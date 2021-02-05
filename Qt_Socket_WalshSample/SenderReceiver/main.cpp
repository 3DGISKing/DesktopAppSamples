#include <QCoreApplication>
#include <QFile>
#include <QList>

#include "process.h"

struct InputData {
    int destinationProcessNumber;
    int value;
};

static inline QStringList qCmdLineArgs(int argc, char *argv[])
{
    QStringList args;

    for (int i = 0; i != argc; ++i)
        args += QString::fromLocal8Bit(argv[i]);

    return args;
}

void showHelp() {
    fprintf(stderr, "sender / receiver usage\n");

    fprintf(stderr,  "./exec_filename hostname port_no < input_filename\n");
}

int main(int argc, char *argv[])
{
    QCoreApplication app(argc, argv);

    QStringList args = qCmdLineArgs(argc, argv);

    if(args.size() != 4){
        showHelp();
        return 1;
    }

    QString hostName = args[1];
    quint16 portNumber = static_cast<quint16> (args[2].toUInt());
    QString inputFile = args[3];

    QFile file(inputFile);

    if(!file.open(QIODevice::ReadOnly)) {
        fprintf(stderr,  "failed to open input file\n");
        return 1;
    }

    QTextStream in(&file);

    QList<InputData> inputDataList;

    while(!in.atEnd()) {
        QString line = in.readLine();
        QStringList fields = line.split(" ");

        if(inputDataList.size() == 3)
            break;

        if(fields.size() != 2) {
            fprintf(stderr, "failed to parse input file\n");
            return 1;
        }

        InputData inputData;

        // The first value of each line represents the id of the process which the current process wants to contact.
        inputData.destinationProcessNumber = fields[0].toInt();

        /*
        The second value of each line represents the data
        (integer value) that the current process wants to
        send. The range of this value will be between zero
        and seven. Based on this restriction the
        encoding/decoding mechanism will always represent
        the data field with three bits.
        */

        inputData.value = fields[1].toInt();

        if(inputData.destinationProcessNumber < 0 || inputData.destinationProcessNumber > 3) {
            fprintf(stderr, "failed to parse input file\n");
            return 1;
        }

        if(inputData.value < 0 || inputData.value > 7) {
            fprintf(stderr, "failed to parse input file\n");
            return 1;
        }

        inputDataList.push_back(inputData);
    }

    file.close();

    if(inputDataList.size() != 3) {
        fprintf(stderr, "failed to parse input file\n");
        return 1;
    }


    Process process1(1, 3, 4);
    Process process2(2, 1, 5);
    Process process3(3, 2, 7);


    /*
    After reading the input file, the sender/receiver program
    will create three child processes (representing processes
    1, 2, and 3).
    */

    /*
    Process process1(1, inputDataList[0].destinationProcessNumber, inputDataList[0].value);
    Process process2(2, inputDataList[1].destinationProcessNumber, inputDataList[1].value);
    Process process3(3, inputDataList[2].destinationProcessNumber, inputDataList[2].value);
    */

    process1.connectToEncoder(hostName, portNumber);
    process2.connectToEncoder(hostName, portNumber);
    process3.connectToEncoder(hostName, portNumber);

    fprintf(stdout,  "\n");

    return app.exec();
}
