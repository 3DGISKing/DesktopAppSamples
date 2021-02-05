#include "PythonSupport.h"
#include "psappinterface.h"
#include "pythonutilsimpl.h"

PythonSupport::PythonSupport(QWidget *parent)
	: QMainWindow(parent)
{
	ui.setupUi(this);

	
	mLineEdit = ui.lineEdit;
	mCheckBox = ui.checkBox;


	mInterface = new PSAppInterface(this);

	initPythonSupport();
}


void PythonSupport::initPythonSupport() {
	mPythonUtils = new PythonUtilsImpl();

	mPythonUtils->initPython(mInterface);
}

void PythonSupport::on_pushButtonRun_clicked()
{
	//mPythonUtils->runString("pybinding.utils.setLineEditText()");

	QString python = ui.textEditPython->toPlainText();
	
	mPythonUtils->runString(python);
}
