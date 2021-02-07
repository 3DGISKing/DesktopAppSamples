#include "process.h"

#include <QCoreApplication>

typedef struct tagDataPacket
{
    int sourceProcessNumber;
    int destinationProcessNumber;
    int data;
}DataPacket;

typedef struct tagEncodedDataPacket
{
    int encodedData[12];
    unsigned int walshCodesNumber;
}EncodedDataPacket;

static QList<int> WalshCodes1 = {-1,  1, -1,  1};
static QList<int> WalshCodes2 = {-1, -1,  1,  1};
static QList<int> WalshCodes3 = {-1,  1,  1, -1};

QList<int> getWalshCodes(unsigned int codeNumber){
    if(codeNumber == 1)
        return WalshCodes1;
    else if (codeNumber == 2) {
        return WalshCodes2;
    }
    else if (codeNumber == 3) {
        return WalshCodes3;
    }
    else {
        return WalshCodes1;
    }
}

int decode(QList<int> EM, unsigned int walshCodesNumber) {
    QList<int> walshCodes = getWalshCodes(walshCodesNumber);
    int walshCodesLength = walshCodes.size();

    QList<int> decodedMessage;

    for (int i = 0; i < EM.size(); ++i) {
       int ithData = EM[i];

       decodedMessage.push_back(ithData * walshCodes[i % walshCodesLength]);
    }

    //spread
    int bitCount = EM.size() / walshCodesLength;
    QString bitRepresentation;

    for (int i = 0; i < bitCount; ++i) {
        int ithBit = 0;

        for (int j = 0; j < walshCodesLength; ++j) {
            ithBit += decodedMessage[i * walshCodesLength + j];
        }

        ithBit /= walshCodesLength;

        if(ithBit == -1)
            ithBit = 0;

        bitRepresentation += QString::number(ithBit);
    }

    int decodedValue = bitRepresentation.toInt(nullptr, 2);

    return decodedValue;

}

Process::Process(int processNumber, int destinationProcessNumber, int value) : QObject(nullptr)
{
    this->_tcpSocket = new QTcpSocket(this);

    this->_number = processNumber;
    this->_destinationProcessNumber = destinationProcessNumber;
    this->_value = value;

    connect(_tcpSocket, SIGNAL(connected()), this, SLOT(onConnected()));
    connect(_tcpSocket, &QIODevice::readyRead, this, &Process::readEncodedData);
    connect(_tcpSocket, QOverload<QAbstractSocket::SocketError>::of(&QAbstractSocket::error), this, &Process::displayError);
}

bool Process::connectToEncoder(QString address, quint16 port)
{
    _tcpSocket->connectToHost(address, port);
    return _tcpSocket->waitForConnected();
}

bool Process::sendData(int destinationProcessNumber, int data)
{
    if(_tcpSocket->state() != QAbstractSocket::ConnectedState)
        return false;

    DataPacket packet;

    packet.sourceProcessNumber = this->_number;
    packet.destinationProcessNumber = destinationProcessNumber;
    packet.data = data;

    _tcpSocket->write(reinterpret_cast<char*>(&packet), sizeof(DataPacket));

    return true;
}

void Process::displayError(QAbstractSocket::SocketError socketError)
{
    switch (socketError) {
    case QAbstractSocket::RemoteHostClosedError:
        break;
    case QAbstractSocket::HostNotFoundError:
        qInfo()<< "The host was not found. Please check the host name and port settings.";
        break;
    case QAbstractSocket::ConnectionRefusedError:
        qInfo()<< "The connection was refused by the peer. Make sure the encoder program is running, and check that the host name and port settings are correct.";
        break;
    default:
        qInfo()<<tr("The following error occurred: %1.").arg(_tcpSocket->errorString());
    }
}

void Process::readEncodedData()
{
    // receive the encoded signal  (twelve integer values), and the code (four integer values);

    EncodedDataPacket encodedDataPacket;

    _tcpSocket->read(reinterpret_cast<char*> (&encodedDataPacket), sizeof (EncodedDataPacket));

    const int codeLength = 12;

    QList<int> EM;

    for (int i = 0; i < codeLength; ++i)
        EM.push_back(encodedDataPacket.encodedData[i]);

    // Print the encoded message, the code, and the decoded message.

    fprintf(stdout, "Child %i \n", _number);
    fprintf(stdout, "Signal: ");

    for (int i = 0; i < codeLength; ++i)
        fprintf(stdout, "%i ", EM[i]);

    fprintf(stdout, "\n");

    fprintf(stdout, "Code: ");

    QList<int> walshCodes = getWalshCodes(encodedDataPacket.walshCodesNumber);

    for (int i = 0; i < walshCodes.size(); ++i)
        fprintf(stdout, "%i ", walshCodes[i]);

    fprintf(stdout, "\n");

    int decodedValue = decode(EM, encodedDataPacket.walshCodesNumber);

    fprintf(stdout, "Received value: %i \n", decodedValue);
    fprintf(stdout, "\n");

    _tcpSocket->disconnectFromHost();

    const int totalProcessCount = 3;

    if(_number == totalProcessCount)
        QCoreApplication::quit();
}

void Process::onConnected()
{
    if (sendData(_destinationProcessNumber, _value)) {
        qInfo()<< QString("Child %1 sending value: %2 to child process %3").arg(_number).arg(_value).arg(_destinationProcessNumber).toLatin1().data();
    }
}
