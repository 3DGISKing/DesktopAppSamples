#include "PythonSupport.h"
#include <QtWidgets/QApplication>

#include <QProcess>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    PythonSupport w;
    w.show();

	return a.exec();
}
