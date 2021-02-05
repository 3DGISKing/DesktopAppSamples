#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_PythonSupport.h"

class PSAppInterface;
class PythonUtils;


class PythonSupport : public QMainWindow
{
    Q_OBJECT

public:
    PythonSupport(QWidget *parent = Q_NULLPTR);

private:
    Ui::PythonSupportClass ui;

	void initPythonSupport();

	PSAppInterface *mInterface;
	friend class PSAppInterface; // brief Interface class to provide access to private methods in Host App for use by plugins.

	PythonUtils *mPythonUtils;

	QLineEdit* mLineEdit;
	QCheckBox* mCheckBox;

private slots:
	void on_pushButtonRun_clicked();
};
