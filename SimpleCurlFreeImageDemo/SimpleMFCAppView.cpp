
// SimpleMFCAppView.cpp : implementation of the CSimpleMFCAppView class
//

#include "stdafx.h"
// SHARED_HANDLERS can be defined in an ATL project implementing preview, thumbnail
// and search filter handlers and allows sharing of document code with that project.
#ifndef SHARED_HANDLERS
#include "SimpleMFCApp.h"
#endif

#include "SimpleMFCAppDoc.h"
#include "SimpleMFCAppView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CSimpleMFCAppView

IMPLEMENT_DYNCREATE(CSimpleMFCAppView, CView)

BEGIN_MESSAGE_MAP(CSimpleMFCAppView, CView)
END_MESSAGE_MAP()

// CSimpleMFCAppView construction/destruction

CSimpleMFCAppView::CSimpleMFCAppView()
{
	// TODO: add construction code here

}

CSimpleMFCAppView::~CSimpleMFCAppView()
{
}

BOOL CSimpleMFCAppView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

// CSimpleMFCAppView drawing

void CSimpleMFCAppView::OnDraw(CDC* /*pDC*/)
{
	CSimpleMFCAppDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: add draw code for native data here
}


// CSimpleMFCAppView diagnostics

#ifdef _DEBUG
void CSimpleMFCAppView::AssertValid() const
{
	CView::AssertValid();
}

void CSimpleMFCAppView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CSimpleMFCAppDoc* CSimpleMFCAppView::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CSimpleMFCAppDoc)));
	return (CSimpleMFCAppDoc*)m_pDocument;
}
#endif //_DEBUG


// CSimpleMFCAppView message handlers
