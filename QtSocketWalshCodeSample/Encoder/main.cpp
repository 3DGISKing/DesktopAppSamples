#include <QCoreApplication>
#include "encoder.h"

static inline QStringList qCmdLineArgs(int argc, char *argv[])
{
    QStringList args;
    for (int i = 0; i != argc; ++i)
        args += QString::fromLocal8Bit(argv[i]);
    return args;
}

void showHelp() {
    fprintf(stderr, "encoder usage\n");

    fprintf(stderr, "./exec_filename port_no\n");
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    QStringList args = qCmdLineArgs(argc, argv);

    if(args.size() != 2){
        showHelp();
        return 1;
    }

    quint16 portNumber = static_cast<quint16>(args[1].toUInt());

    Encoder encoder(portNumber);

    if(!encoder.start())
    {
        return 1;
    }

    return a.exec();
}

