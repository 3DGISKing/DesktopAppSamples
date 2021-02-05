#include "encoder.h"

#include <QTcpSocket>
#include <QCoreApplication>
#include <QTime>

typedef struct tagDataPacket
{
    unsigned int sourceProcessNumber;
    unsigned int destinationProcessNumber;
    unsigned int data;
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

QList<int> getInvertedWalshCodes(QList<int> walshCodes) {
    QList<int> result;

    for (int i = 0; i < walshCodes.size(); i++) {
        result.push_back(-1 * walshCodes[i]);
    }

    return result;
}

QList<int> getEncodedMessage(unsigned int value, unsigned int walshCodesNumber)
{
    // The range of this value will be between zero and seven.

    Q_ASSERT(value <=7);

    QList<int> walshCodes = getWalshCodes(walshCodesNumber);

    QString binaryRepresentationOfValue = QString::number(value, 2);

    QList<int> result;

    for (int i = 0; i < binaryRepresentationOfValue.length(); ++i) {
        QString ithBit = binaryRepresentationOfValue.at(i);

        if(ithBit == "1")
            result.append(walshCodes);
        else
            result.append(getInvertedWalshCodes(walshCodes));
    }

    return result;
}

Encoder::Encoder(quint16 port)
:QObject(nullptr)
{
    _portNumber = port;
    _tcpServer = new QTcpServer(this);

     connect(_tcpServer, &QTcpServer::newConnection, this, &Encoder::onNewConnection);
}

bool Encoder::start()
{
    if (!_tcpServer->listen(QHostAddress::Any, _portNumber)){
        fprintf(stderr, "failed to start encoder\n");
        return false;
    }

    fprintf(stdout, "encoder start listening port: %i\n", _portNumber);

    return true;
}

void delay(int secs)
{
    QTime dieTime= QTime::currentTime().addSecs(secs);

    while (QTime::currentTime() < dieTime)
        QCoreApplication::processEvents(QEventLoop::AllEvents, 100);
}

void Encoder::onNewConnection()
{
    QTcpSocket *socket = _tcpServer->nextPendingConnection();

    connect(socket, &QAbstractSocket::disconnected, this, &Encoder::onDisconnected);

    // Receive the request from each child process from the sender / receiver program

    DataPacket dataPacket;

    socket->waitForReadyRead();

    socket->read(reinterpret_cast<char*> (&dataPacket), sizeof (DataPacket));

    qInfo()<< QString("Here is the message from child %1: Value = %2, Destination = %3").arg(dataPacket.sourceProcessNumber).arg(dataPacket.data).arg(dataPacket.destinationProcessNumber).toLatin1().data();

    EncodedMessage em = getEncodedMessage(dataPacket.data, dataPacket.sourceProcessNumber);

    _encodedMessageList.push_back(em);

    _socketHash.insert(dataPacket.sourceProcessNumber, socket);
    _walshCodesNumberHash.insert(dataPacket.destinationProcessNumber, dataPacket.sourceProcessNumber);

    const int totalProcessCount = 3;

    if(_encodedMessageList.size() == totalProcessCount) {
        // Generate the encoded message using Walsh codes;

        QList<int> EM1 = _encodedMessageList[0];
        QList<int> EM2 = _encodedMessageList[1];
        QList<int> EM3 = _encodedMessageList[2];

        const int codeLength = 12;

        Q_ASSERT(EM1.size() == codeLength && EM1.size() == EM2.size() && EM2.size() == EM3.size());

        QList<int> result;

        // Send the encoded message and corresponding code to all the child processes

        EncodedDataPacket encodedDataPacket;

        for (int i = 0; i < codeLength; ++i) {
            int ithData = EM1[i] + EM2[i] + EM3[i];

            encodedDataPacket.encodedData[i] = ithData;
        }

        // process number start from 1
        for (unsigned int i = 1; i <= totalProcessCount; ++i) {
            QTcpSocket* ithProcessSocket = _socketHash.value(i);
            unsigned int willBeSentWalshCodeNumber = _walshCodesNumberHash.value(i);

            encodedDataPacket.walshCodesNumber = willBeSentWalshCodeNumber;

            qint64 writenBytes = ithProcessSocket->write(reinterpret_cast<char*>(&encodedDataPacket), sizeof(EncodedDataPacket));

            Q_ASSERT(writenBytes == sizeof (EncodedDataPacket));

            delay(1);
        }
    }
}

void Encoder::onDisconnected()
{
    QTcpSocket* socket = dynamic_cast<QTcpSocket*> (QObject::sender());

    unsigned int processNumber = _socketHash.key(socket);

    if(processNumber == 3)
        // Finish its execution.
        QCoreApplication::quit();
}

