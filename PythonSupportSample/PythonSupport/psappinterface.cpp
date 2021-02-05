#include "psappinterface.h"
#include "PythonSupport.h"

PSAppInterface::PSAppInterface(PythonSupport * app)
{
	this->app = app;
}

void PSAppInterface::setLineEditText(QString text)
{
	this->app->mLineEdit->setText(text);
}

QString PSAppInterface::getLineEditText(){
	return this->app->mLineEdit->text();
}

void PSAppInterface::setCheckBoxState(Qt::CheckState state) {
	this->app->mCheckBox->setCheckState(state);
}


Qt::CheckState PSAppInterface::getCheckBoxState()
{
	return this->app->mCheckBox->checkState();
}


