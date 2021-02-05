
// SimpleMFCApp.h : main header file for the SimpleMFCApp application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols


// CSimpleMFCAppApp:
// See SimpleMFCApp.cpp for the implementation of this class
//

class CSimpleMFCAppApp : public CWinApp
{
public:
	CSimpleMFCAppApp();


// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
  afx_msg void OnFileSend();
  afx_msg void OnFileSendimage();
};

extern CSimpleMFCAppApp theApp;
