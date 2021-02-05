
// SimpleMFCAppView.h : interface of the CSimpleMFCAppView class
//

#pragma once


class CSimpleMFCAppView : public CView
{
protected: // create from serialization only
	CSimpleMFCAppView();
	DECLARE_DYNCREATE(CSimpleMFCAppView)

// Attributes
public:
	CSimpleMFCAppDoc* GetDocument() const;

// Operations
public:

// Overrides
public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:

// Implementation
public:
	virtual ~CSimpleMFCAppView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in SimpleMFCAppView.cpp
inline CSimpleMFCAppDoc* CSimpleMFCAppView::GetDocument() const
   { return reinterpret_cast<CSimpleMFCAppDoc*>(m_pDocument); }
#endif

