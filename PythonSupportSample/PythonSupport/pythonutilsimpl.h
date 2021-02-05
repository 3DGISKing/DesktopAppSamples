

#include "pythonutils.h"

// forward declaration for PyObject
#ifndef PyObject_HEAD
struct _object;
typedef _object PyObject;
#endif

class PythonUtilsImpl : public PythonUtils
{
public:

	PythonUtilsImpl();

	//! initialize python and import bindings
	void initPython(AppInterface* interface) override;

	//! close python interpreter
	void exitPython() override;

	//! returns true if python support is ready to use (must be inited first)
	bool isEnabled() override;

	//! returns path where python stuff is located
	QString pythonPath();

	//! run a statement (wrapper for PyRun_String)
	//! this command is more advanced as enables error checking etc.
	//! when an exception is raised, it shows dialog with exception details
	//! @return true if no error occurred
	bool runString(const QString& command, QString msgOnError = QString(), bool single = true) override;

	//! run a statement, error reporting is not done
	//! @return true if no error occurred
	bool runStringUnsafe(const QString& command, bool single = true) override;

	bool evalString(const QString& command, QString& result) override;
protected:

	//! initialize Python context
	void init();

	//! check imports and plugins
	//@return true if all imports worked
	bool checkSystemImports();

	QString getTraceback();

	//! cleanup Python context
	void finish();

	//! convert python object to QString. If the object isn't unicode/str, it will be converted
	QString PyObjectToQString(PyObject* obj);

	//! reference to module __main__
	PyObject* mMainModule;

	//! dictionary of module __main__
	PyObject* mMainDict;

	//! flag determining that python support is enabled
	bool mPythonEnabled;

};
