#include "stdafx.h"
#include "Test.h"
#include "FreeImage.h"
#include "config-win32.h" // for curl
#include <curl/curl.h>
#include <string>

#define API_URL "https://api.remove.bg/v1.0/removebg"

bool TestClass::mCurlIsInit = false;
std::string responseBuffer;

TestClass::TestClass()
{
}

TestClass::~TestClass()
{
}

void TestClass::InitCurl() {
	mCurlIsInit = true;
	curl_global_init(CURL_GLOBAL_ALL);
}

size_t CurlWriteCallback(char* contents, size_t size, size_t nmemb, void* userp)
{
	((std::string*)userp)->append((char*)contents, size * nmemb);

	return size * nmemb;
}

BOOL TestClass::SaveBufferToFile(const CString filename, const char* buffer,const int size)
{
	FILE* file = fopen(CT2A(filename), "wb");

	if (file == NULL)
	{
		return FALSE;
	}

	fwrite(buffer, sizeof(char), size, file);
	fclose(file);

	return TRUE;
}

BOOL TestClass::SubmitDiskVersion(CString inputImageFileName, CString outputImageFileName)
{
	// init curl
	if (!mCurlIsInit)
		InitCurl();

	// init buffer
	responseBuffer.clear();

	struct curl_httppost *formpost = NULL;
	struct curl_httppost *lastptr = NULL;
	
	char buf_file_name[256];

	std::wcstombs(buf_file_name, (LPCWSTR)inputImageFileName, 256);

	curl_formadd(&formpost,
		&lastptr,
		CURLFORM_COPYNAME, "image_file",
		CURLFORM_FILE, buf_file_name,
		CURLFORM_END);
	
	curl_formadd(&formpost,
		&lastptr,
		CURLFORM_COPYNAME, "image_file",
		CURLFORM_COPYCONTENTS, buf_file_name,
		CURLFORM_END);
		
	CURL *curl = curl_easy_init();

	if (curl == NULL)
		return FALSE;
	
	struct curl_slist * headerlist = NULL;

	headerlist = curl_slist_append(headerlist, "X-API-Key: egt7rFuFekxB46CLD1c46VN9");

	/* First set the URL that is about to receive our POST. This URL can
	just as well be a https:// URL if that is what should receive the
	data. */
	curl_easy_setopt(curl, CURLOPT_URL, API_URL);

	/* Now specify the POST data */
	curl_easy_setopt(curl, CURLOPT_HTTPPOST, formpost);

	curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headerlist);

	curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &CurlWriteCallback);
	curl_easy_setopt(curl, CURLOPT_WRITEDATA, &responseBuffer);

	// because ssl connection
	curl_easy_setopt(curl, CURLOPT_CAINFO, "cacert.pem");
	
	/* Perform the request, res will get the return code */
	CURLcode res = curl_easy_perform(curl);

	/* Check for errors */
	if (res != CURLE_OK) {
		fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));

		/* always cleanup */
		curl_easy_cleanup(curl);

		return FALSE;
	}

	/* always cleanup */
	curl_easy_cleanup(curl);

	curl_global_cleanup();

	OutputDebugString((LPCWSTR)responseBuffer.c_str());

	// parse response

	// prepare momory stream
	FIMEMORY* fMemeory = FreeImage_OpenMemory((BYTE*)responseBuffer.c_str(), responseBuffer.size());
	
	// get image type
	FREE_IMAGE_FORMAT lFIF = FreeImage_GetFileTypeFromMemory(fMemeory, 0);

	if (lFIF == FIF_UNKNOWN)
		return FALSE;

	// always close the memory stream
	FreeImage_CloseMemory(fMemeory);

	// response is resulgar image

	SaveBufferToFile(outputImageFileName, responseBuffer.c_str(), responseBuffer.size());

	return TRUE;
}

BOOL TestClass::SubmitMemoryVersion(CString inputImageFileName, CString outputImageFileName)
{
	// first check file is image
	FREE_IMAGE_FORMAT lFIF = FreeImage_GetFileType(CT2A(inputImageFileName), 0);

	if (lFIF == FIF_UNKNOWN) {
		OutputDebugString(_T("This is not valid image!"));
		return FALSE;
	}

	// next open raw image file and prepare buffer

	FILE* pFile = fopen(CT2A(inputImageFileName), "rb");

	fseek(pFile, 0, SEEK_END);

	int file_size = ftell(pFile);

	rewind(pFile);

	char* buffer = (char*)malloc(sizeof(char) * file_size);

	int result = fread(buffer, 1, file_size, pFile);

	if (result != file_size){
		return FALSE;
	}

	// curl init

	if (!mCurlIsInit)
		InitCurl();

	// buffer init
	responseBuffer.clear();

	// prepare request
	struct curl_httppost *formpost = NULL;
	struct curl_httppost *lastptr = NULL;
	
	curl_formadd(&formpost,
		&lastptr,
		CURLFORM_COPYNAME, "image_file",
		CURLFORM_BUFFER, "image_file",
		CURLFORM_BUFFERPTR, buffer,
		CURLFORM_BUFFERLENGTH, file_size,
		CURLFORM_END);

	CURL *curl = curl_easy_init();

	if (curl == NULL)
		return FALSE;

	struct curl_slist * headerlist = NULL;

	headerlist = curl_slist_append(headerlist, "X-API-Key: egt7rFuFekxB46CLD1c46VN9");

	/* First set the URL that is about to receive our POST. This URL can
	just as well be a https:// URL if that is what should receive the
	data. */
	curl_easy_setopt(curl, CURLOPT_URL, API_URL);

	/* Now specify the POST data */
	curl_easy_setopt(curl, CURLOPT_HTTPPOST, formpost);

	curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headerlist);

	curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &CurlWriteCallback);
	curl_easy_setopt(curl, CURLOPT_WRITEDATA, &responseBuffer);

	// because ssl connection
	curl_easy_setopt(curl, CURLOPT_CAINFO, "cacert.pem");

	/* Perform the request, res will get the return code */
	CURLcode res = curl_easy_perform(curl);

	/* Check for errors */
	if (res != CURLE_OK) {
		fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));

		/* always cleanup */
		curl_easy_cleanup(curl);
		
		free(buffer);

		return FALSE;
	}

	/* always cleanup */
	curl_easy_cleanup(curl);

	curl_global_cleanup();

	OutputDebugString((LPCWSTR)responseBuffer.c_str());

	// parse response

	FIMEMORY* fMemeory = FreeImage_OpenMemory((BYTE*)responseBuffer.c_str(), responseBuffer.size());

	lFIF = FreeImage_GetFileTypeFromMemory(fMemeory, 0);

	if (lFIF == FIF_UNKNOWN)
		return FALSE;

	// load an image from the memory stream
	FIBITMAP *resultImage = FreeImage_LoadFromMemory(lFIF, fMemeory, 0);

	// save as a regular file
	FreeImage_Save(lFIF, resultImage, CT2A(outputImageFileName), PNG_DEFAULT);

	FreeImage_Unload(resultImage);

	// always close the memory stream
	FreeImage_CloseMemory(fMemeory);

	free(buffer);
	return TRUE;
}