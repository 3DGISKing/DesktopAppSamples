#ifndef PROCESS_H
#define PROCESS_H

#include <QTcpSocket>

class Process : public QObject
{
    Q_OBJECT

public:
    explicit Process(int processNumber, int destinationProcessNumber, int value);
    int number() {return _number;}

    bool connectToEncoder(QString address, quint16 port);
    bool sendData(int destinationProcessNumber, int data);

signals:

private slots:
    void onConnected();
    void displayError(QAbstractSocket::SocketError socketError);
    void readEncodedData();

private:
    int        _number;
    int        _destinationProcessNumber;
    int        _value;
    QTcpSocket *_tcpSocket;
};

#endif // PROCESS_H
