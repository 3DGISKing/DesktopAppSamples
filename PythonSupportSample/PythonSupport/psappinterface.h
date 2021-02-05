#include "./../PythonBinding/appinterface.h"

class PythonSupport;



/**
* brief Interface class to provide access to private methods in Host App for use by plugins.
*
* Only those functions "exposed" by class AppInterface can be called from within a plugin.
*/

class PSAppInterface: public AppInterface
{
public:
	PSAppInterface(PythonSupport *app);

	void setLineEditText(QString text);
	QString getLineEditText();

	void setCheckBoxState(Qt::CheckState state);
	Qt::CheckState getCheckBoxState();
private:
	PythonSupport *app;

};