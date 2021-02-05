class AppInterface
{
%TypeHeaderCode
#include <appinterface.h>
%End

public:

    /** Constructor */
    AppInterface();

    /** Virtual destructor */
    virtual ~AppInterface();

	virtual void setLineEditText(QString text) = 0;
	virtual QString getLineEditText() = 0;

	virtual void setCheckBoxState(Qt::CheckState state) = 0;
	virtual Qt::CheckState getCheckBoxState() = 0;
};