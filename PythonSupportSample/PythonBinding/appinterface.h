#ifndef APPINTERFACE_H
#define APPINTERFACE_H

#include <QString>

#include "pythonbinding_global.h"
/**
* Abstract base class defining interfaces exposed by PythonSupport and
* made available to plugins.
*
* Only functionality exposed by PSInterface can be used in plugins.
* This interface has to be implemented with application specific details.
*
* We implements it in PSAppInterface class, 3rd party applications
* could provide their own implementation to be able to use plugins.
*/

class PYTHONBINDING_EXPORT AppInterface
{
public:
	AppInterface();

	/** Virtual destructor */
	virtual ~AppInterface();

	virtual void setLineEditText(QString text) = 0;
	virtual QString getLineEditText() = 0;
	virtual void setCheckBoxState(Qt::CheckState state) = 0;
	virtual Qt::CheckState getCheckBoxState() = 0;
};

#endif