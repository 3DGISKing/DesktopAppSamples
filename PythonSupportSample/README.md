install qt-opensource-windows-x86-msvc2013-5.7.0.  
install visual sudio 2013.   
install python-2.7.14.msi.  
	if want to build debug mode, download python source Python-2.7.14.tgz and build.
     http://www.p-nand-q.com/python/building-python-27-with-visual_studio.html
	 
#### How to build sip

download sip4.18 from https://sourceforge.net/projects/pyqt/files/sip/sip-4.18/sip-4.18.zip/download  
extract sip-4.18.zip to C:\sip-4.18.  
In visual_studio, open visual_studio command prompt.
  
    python configure.py  
    nmake
    nmake install.

do following.
 https://stackoverflow.com/questions/25589103/how-to-install-pyqt5-on-windows-for-python-2

   
#### How to build pyQt5   
1. download PyQt5_gpl-5.7.zip from https://sourceforge.net/projects/pyqt/files/PyQt5/PyQt-5.7/  
2. extract zip file.  
3. In visual_studio, open visual_studio command prompt.
  
          python  configure.py --spec win32-msvc2013 --qmake C:\Qt\Qt5.7.0\5.7\msvc2013\bin\qmake.exe --disable QtNfc 
          
why disable QtNfc: link error 
      
       nmake 
       nmake install

make sip file.  
generate cpp file.  
build pyd file.   