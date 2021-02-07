#ifndef ENCODER_H
#define ENCODER_H

#include <QTcpServer>
#include <QList>

typedef QList<int> EncodedMessage;

class Encoder: public QObject
{
    Q_OBJECT

public:
    Encoder(quint16 port);
    bool start();

private slots:
    void onNewConnection();
    void onDisconnected();

private:
    quint16               _portNumber;
    QTcpServer *          _tcpServer;
    QList<EncodedMessage> _encodedMessageList;

    // key: process number value: process 's socket
    QHash<unsigned int, QTcpSocket*> _socketHash;

    // key: process number value: walshCodesNumber which will be sent
    QHash<unsigned int, unsigned int> _walshCodesNumberHash;
};

#endif // ENCODER_H
