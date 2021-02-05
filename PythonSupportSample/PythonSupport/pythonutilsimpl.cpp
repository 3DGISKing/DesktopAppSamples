// python should be first include
// otherwise issues some warnings
/*#ifdef _MSC_VER
#ifdef _DEBUG
#undef _DEBUG
#endif
#endif*/

#include <Python.h>

#include "pythonutilsimpl.h"

#include <QStringList>
#include <QDebug>
#include <QCoreApplication>
#include <QMessageBox>

#if (PY_VERSION_HEX < 0x03000000)
QString PY_UNICODE2QSTRING(PyObject* obj)
{
	PyObject* utf8 = PyUnicode_AsUTF8String(obj);
	QString result = utf8 ? QString::fromUtf8(PyString_AS_STRING(utf8)) : "(error)";
	Py_XDECREF(utf8);
	return result;
}
#elif (PY_VERSION_HEX < 0x03030000)
#define PY_UNICODE2QSTRING(obj) QString::fromUtf8( PyBytes_AsString(PyUnicode_AsUTF8String( obj ) ) )
#else
#define PY_UNICODE2QSTRING(obj) QString::fromUtf8( PyUnicode_AsUTF8( obj ) )
#endif

PyThreadState* _mainState;

PythonUtilsImpl::PythonUtilsImpl()
{
	
}

void PythonUtilsImpl::init()
{
	// initialize python
	Py_Initialize();
	// initialize threading AND acquire GIL
	PyEval_InitThreads();

	mPythonEnabled = true;

	mMainModule = PyImport_AddModule("__main__"); // borrowed reference
	mMainDict = PyModule_GetDict(mMainModule); // borrowed reference
}

bool PythonUtilsImpl::checkSystemImports()
{
	bool ret;

	ret = runString("import sys"); // import sys module (for display / exception hooks)
	ret = runString("import os"); // import os module (for user paths)

	QStringList newpaths;
	newpaths << '"' + pythonPath() + '"';

	runString("sys.path = [" + newpaths.join(",") + "] + sys.path");

	// import SIP
	if (!runString("import sip",
		QObject::tr("Couldn't load SIP module.") + '\n' + QObject::tr("Python support will be disabled.")))
	{
		return false;
	}

	// import QGIS utils
	QString error_msg = QObject::tr("Couldn't load PsApp utils.") + '\n' + QObject::tr("Python support will be disabled.");
	if (!runString("import pybinding.utils", error_msg))
	{
		return false;
	}


	return true;
}

void PythonUtilsImpl::initPython(AppInterface* interface)
{
	init();
	if (!checkSystemImports())
	{
		exitPython();
		return;
	}

	// initialize 'iface' object
	runString("pybinding.utils.initInterface(" + QString::number((unsigned long) interface) + ')');


	finish();
}

bool PythonUtilsImpl::runString(const QString& command, QString msgOnError, bool single)
{
	bool res = runStringUnsafe(command, single);
	if (res)
		return true;

	if (msgOnError.isEmpty())
	{
		// use some default message if custom hasn't been specified
		msgOnError = QObject::tr("An error occurred during execution of following code:") + "\n<tt>" + command + "</tt>";
	}

	// TODO: use python implementation

	QString traceback = getTraceback();
	QString path, version;
	evalString("str(sys.path)", path);
	evalString("sys.version", version);

	QString str = "<font color=\"red\">" + msgOnError + "</font><br><pre>\n" + traceback + "\n</pre>"
		+ QObject::tr("Python version:") + "<br>" + version + "<br><br>"
		+ QObject::tr("version:") + "<br>" + QString("%1 '%2', %3").arg("1.0.0", "Python Support", "") + "<br><br>"
		+ QObject::tr("Python path:") + "<br>" + path;
	str.replace('\n', "<br>").replace("  ", "&nbsp; ");

	qDebug() << str;

	QMessageBox msgBox;

	msgBox.setText("Python error");
	msgBox.setInformativeText(str);
	msgBox.exec();
	
	return res;
}

QString PythonUtilsImpl::getTraceback()
{
#define TRACEBACK_FETCH_ERROR(what) {errMsg = what; goto done;}

	// acquire global interpreter lock to ensure we are in a consistent state
	PyGILState_STATE gstate;
	gstate = PyGILState_Ensure();

	QString errMsg;
	QString result;

	PyObject *modStringIO = nullptr;
	PyObject *modTB = nullptr;
	PyObject *obStringIO = nullptr;
	PyObject *obResult = nullptr;

	PyObject *type, *value, *traceback;

	PyErr_Fetch(&type, &value, &traceback);
	PyErr_NormalizeException(&type, &value, &traceback);

#if (PY_VERSION_HEX < 0x03000000)
	const char* iomod = "cStringIO";
#else
	const char* iomod = "io";
#endif

	modStringIO = PyImport_ImportModule(iomod);
	if (!modStringIO)
		TRACEBACK_FETCH_ERROR(QString("can't import %1").arg(iomod));

	obStringIO = PyObject_CallMethod(modStringIO, (char*) "StringIO", nullptr);

	/* Construct a cStringIO object */
	if (!obStringIO)
		TRACEBACK_FETCH_ERROR("cStringIO.StringIO() failed");

	modTB = PyImport_ImportModule("traceback");
	if (!modTB)
		TRACEBACK_FETCH_ERROR("can't import traceback");

	obResult = PyObject_CallMethod(modTB, (char*) "print_exception",
		(char*) "OOOOO",
		type, value ? value : Py_None,
		traceback ? traceback : Py_None,
		Py_None,
		obStringIO);

	if (!obResult)
		TRACEBACK_FETCH_ERROR("traceback.print_exception() failed");

	Py_DECREF(obResult);

	obResult = PyObject_CallMethod(obStringIO, (char*) "getvalue", nullptr);
	if (!obResult)
		TRACEBACK_FETCH_ERROR("getvalue() failed.");

	/* And it should be a string all ready to go - duplicate it. */
	if (!
#if (PY_VERSION_HEX < 0x03000000)
		PyString_Check(obResult)
#else
		PyUnicode_Check(obResult)
#endif
		)
		TRACEBACK_FETCH_ERROR("getvalue() did not return a string");

	result = PyObjectToQString(obResult);

done:

	// All finished - first see if we encountered an error
	if (result.isEmpty() && !errMsg.isEmpty())
	{
		result = errMsg;
	}

	Py_XDECREF(modStringIO);
	Py_XDECREF(modTB);
	Py_XDECREF(obStringIO);
	Py_XDECREF(obResult);
	Py_XDECREF(value);
	Py_XDECREF(traceback);
	Py_XDECREF(type);

	// we are done calling python API, release global interpreter lock
	PyGILState_Release(gstate);

	return result;
}

QString PythonUtilsImpl::PyObjectToQString(PyObject* obj)
{
	QString result;

	// is it None?
	if (obj == Py_None)
	{
		return QString();
	}

	// check whether the object is already a unicode string
	if (PyUnicode_Check(obj))
	{
		result = PY_UNICODE2QSTRING(obj);
		return result;
	}

#if (PY_VERSION_HEX < 0x03000000)
	// check whether the object is a classical (8-bit) string
	if (PyString_Check(obj))
	{
		return QString::fromUtf8(PyString_AS_STRING(obj));
	}

	// it's some other type of object:
	// convert object to unicode string (equivalent to calling unicode(obj) )
	PyObject* obj_uni = PyObject_Unicode(obj); // obj_uni is new reference
	if (obj_uni)
	{
		result = PY_UNICODE2QSTRING(obj_uni);
		Py_XDECREF(obj_uni);
		return result;
	}
#endif

	// if conversion to unicode failed, try to convert it to classic string, i.e. str(obj)
	PyObject* obj_str = PyObject_Str(obj); // new reference
	if (obj_str)
	{
#if (PY_VERSION_HEX < 0x03000000)
		result = QString::fromUtf8(PyString_AS_STRING(obj));
#else
		result = PY_UNICODE2QSTRING(obj_str);
#endif
		Py_XDECREF(obj_str);
		return result;
	}

	// some problem with conversion to unicode string
	qDebug("unable to convert PyObject to a QString!");
	return "(error)";
}

void PythonUtilsImpl::exitPython()
{
	Py_Finalize();
	mMainModule = nullptr;
	mMainDict = nullptr;
	mPythonEnabled = false;
}

bool PythonUtilsImpl::isEnabled()
{
	return mPythonEnabled;
}

bool PythonUtilsImpl::runStringUnsafe(const QString& command, bool single)
{
	// acquire global interpreter lock to ensure we are in a consistent state
	PyGILState_STATE gstate;
	gstate = PyGILState_Ensure();

	// TODO: convert special characters from unicode strings u"..." to \uXXXX
	// so that they're not mangled to utf-8
	// (non-unicode strings can be mangled)
	PyObject* obj = PyRun_String(command.toUtf8().data(), single ? Py_single_input : Py_file_input, mMainDict, mMainDict);
	bool res = nullptr == PyErr_Occurred();
	Py_XDECREF(obj);

	// we are done calling python API, release global interpreter lock
	PyGILState_Release(gstate);

	return res;
}

bool PythonUtilsImpl::evalString(const QString& command, QString& result)
{
	// acquire global interpreter lock to ensure we are in a consistent state
	PyGILState_STATE gstate;
	gstate = PyGILState_Ensure();

	PyObject* res = PyRun_String(command.toUtf8().data(), Py_eval_input, mMainDict, mMainDict);
	bool success = nullptr != res;

	if (PyErr_Occurred())
	{
		QString traceback = getTraceback();
		QString msg = QString("evalString()) error!\nCommand:\n%1\nError:\n%2").arg(command).arg(traceback);

		//qDebug(msg.toStdString());
	}

	if (success)
		result = PyObjectToQString(res);

	Py_XDECREF(res);

	// we are done calling python API, release global interpreter lock
	PyGILState_Release(gstate);

	return success;
}

void PythonUtilsImpl::finish()
{
	// release GIL!
	// Later on, we acquire GIL just before doing some Python calls and
	// release GIL again when the work with Python API is done.
	// (i.e. there must be PyGILState_Ensure + PyGILState_Release pair
	// around any calls to Python API, otherwise we may segfault!)
	_mainState = PyEval_SaveThread();
}

QString PythonUtilsImpl::pythonPath()
{
	return QCoreApplication::applicationDirPath() + "/python/pybinding";
	
}
