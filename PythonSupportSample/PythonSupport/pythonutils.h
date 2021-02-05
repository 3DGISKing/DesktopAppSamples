
#include <QString>

class AppInterface;

class PythonUtils
{
public:

	//! returns true if python support is ready to use (must be inited first)
	virtual bool isEnabled() = 0;

	//! initialize python and import bindings
	virtual void initPython(AppInterface* interface) = 0;

	//! close python interpreter
	virtual void exitPython() = 0;

	//! run a statement, show an error message on error
	//! @return true if no error occurred
	virtual bool runString(const QString& command, QString msgOnError = QString(), bool single = true) = 0;

	//! run a statement, error reporting is not done
	//! @return true if no error occurred
	virtual bool runStringUnsafe(const QString& command, bool single = true) = 0;

	virtual bool evalString(const QString& command, QString& result) = 0;


};