struct FIBITMAP;

class TestClass
{
    static bool mCurlIsInit;
private:
	static void InitCurl();
	static FIBITMAP* LoadImage(CString pPathFileName);
	static BOOL SaveBufferToFile(const CString filename, const char* buffer, const int size);
public:
  TestClass();
  virtual ~TestClass();

  static BOOL SubmitDiskVersion(CString inputImageFileName, CString outputImageFileName);
  static BOOL SubmitMemoryVersion(CString inputImageFileName, CString outputImageFileName);
};
