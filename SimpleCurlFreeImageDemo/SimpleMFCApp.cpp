
// SimpleMFCApp.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "afxwinappex.h"
#include "afxdialogex.h"
#include "SimpleMFCApp.h"
#include "MainFrm.h"

#include "SimpleMFCAppDoc.h"
#include "SimpleMFCAppView.h"

#include "Test.h"
#include "FreeImage.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CSimpleMFCAppApp

BEGIN_MESSAGE_MAP(CSimpleMFCAppApp, CWinApp)
	ON_COMMAND(ID_APP_ABOUT, &CSimpleMFCAppApp::OnAppAbout)
	// Standard file based document commands
	ON_COMMAND(ID_FILE_NEW, &CWinApp::OnFileNew)
	ON_COMMAND(ID_FILE_OPEN, &CWinApp::OnFileOpen)
  ON_COMMAND(ID_FILE_SEND, &CSimpleMFCAppApp::OnFileSend)
  ON_COMMAND(ID_FILE_SENDIMAGE, &CSimpleMFCAppApp::OnFileSendimage)
END_MESSAGE_MAP()


// CSimpleMFCAppApp construction

CSimpleMFCAppApp::CSimpleMFCAppApp()
{
	// TODO: replace application ID string below with unique ID string; recommended
	// format for string is CompanyName.ProductName.SubProduct.VersionInformation
	SetAppID(_T("SimpleMFCApp.AppID.NoVersion"));

	// TODO: add construction code here,
	// Place all significant initialization in InitInstance
}

// The one and only CSimpleMFCAppApp object

CSimpleMFCAppApp theApp;


void FreeImageErrorHandler(FREE_IMAGE_FORMAT fif, const char *message)
{
  OutputDebugString(_T("\n*** "));
  if (fif != FIF_UNKNOWN)
  {
    CString lFIformat(FreeImage_GetFormatFromFIF(fif));
    CString lOut;
    lOut.Format(_T("Format: %s\n"), lFIformat);
    OutputDebugString(lOut);
  }
  CString lMessage(message);
  OutputDebugString(lMessage);
  OutputDebugString(_T(" ***\n"));
}


// CSimpleMFCAppApp initialization


BOOL CSimpleMFCAppApp::InitInstance()
{
  FreeImage_SetOutputMessage(FreeImageErrorHandler);

	// InitCommonControlsEx() is required on Windows XP if an application
	// manifest specifies use of ComCtl32.dll version 6 or later to enable
	// visual styles.  Otherwise, any window creation will fail.
	INITCOMMONCONTROLSEX InitCtrls;
	InitCtrls.dwSize = sizeof(InitCtrls);
	// Set this to include all the common control classes you want to use
	// in your application.
	InitCtrls.dwICC = ICC_WIN95_CLASSES;
	InitCommonControlsEx(&InitCtrls);

	CWinApp::InitInstance();


	EnableTaskbarInteraction(FALSE);

	// AfxInitRichEdit2() is required to use RichEdit control	
	// AfxInitRichEdit2();

	// Standard initialization
	// If you are not using these features and wish to reduce the size
	// of your final executable, you should remove from the following
	// the specific initialization routines you do not need
	// Change the registry key under which our settings are stored
	// TODO: You should modify this string to be something appropriate
	// such as the name of your company or organization
	SetRegistryKey(_T("Local AppWizard-Generated Applications"));
	LoadStdProfileSettings(4);  // Load standard INI file options (including MRU)


	// Register the application's document templates.  Document templates
	//  serve as the connection between documents, frame windows and views
	CSingleDocTemplate* pDocTemplate;
	pDocTemplate = new CSingleDocTemplate(
		IDR_MAINFRAME,
		RUNTIME_CLASS(CSimpleMFCAppDoc),
		RUNTIME_CLASS(CMainFrame),       // main SDI frame window
		RUNTIME_CLASS(CSimpleMFCAppView));
	if (!pDocTemplate)
		return FALSE;
	AddDocTemplate(pDocTemplate);


	// Parse command line for standard shell commands, DDE, file open
	CCommandLineInfo cmdInfo;
	ParseCommandLine(cmdInfo);



	// Dispatch commands specified on the command line.  Will return FALSE if
	// app was launched with /RegServer, /Register, /Unregserver or /Unregister.
	if (!ProcessShellCommand(cmdInfo))
		return FALSE;

	// The one and only window has been initialized, so show and update it
	m_pMainWnd->ShowWindow(SW_SHOW);
	m_pMainWnd->UpdateWindow();
	return TRUE;
}

// CSimpleMFCAppApp message handlers


// CAboutDlg dialog used for App About

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// Dialog Data
	enum { IDD = IDD_ABOUTBOX };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

// Implementation
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()

// App command to run the dialog
void CSimpleMFCAppApp::OnAppAbout()
{
	CAboutDlg aboutDlg;
	aboutDlg.DoModal();
}

// CSimpleMFCAppApp message handlers


void CSimpleMFCAppApp::OnFileSend()
{
	BOOL ret = TestClass::SubmitDiskVersion(_T("InputImage.jpg"), _T("resultFromDiskVersion.png"));

	if (ret == TRUE){
		AfxMessageBox(_T("Success!"));
	}
	else {
		AfxMessageBox(_T("Failed!"));
	}
}


void CSimpleMFCAppApp::OnFileSendimage()
{
	BOOL ret = TestClass::SubmitMemoryVersion(_T("InputImage.jpg"), _T("resultFromMemoryVersion.png"));

	if (ret == TRUE){
		AfxMessageBox(_T("Success!"));
	}
	else {
		AfxMessageBox(_T("Failed!"));
	}
}
