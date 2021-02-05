#pragma once

#include <QtCore/qglobal.h>

#ifndef QT_STATIC
# if defined(PYTHONBINDING_LIB)
#  define PYTHONBINDING_EXPORT Q_DECL_EXPORT
# else
#  define PYTHONBINDING_EXPORT Q_DECL_IMPORT
# endif
#else
# define PYTHONBINDING_EXPORT
#endif
