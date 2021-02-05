#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_QtSqlUtility.h"

class QtSqlUtility : public QMainWindow
{
    Q_OBJECT

public:
    QtSqlUtility(QWidget *parent = Q_NULLPTR);

private:
    Ui::QtSqlUtilityClass ui;
};
