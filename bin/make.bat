@echo off

 setlocal
 if "%JAVA_HOME%" == "" (
	echo variable JAVA_HOME must be set up
	pause
	goto eof
 ) 
 set PATH=%JAVA_HOME%\bin;%PATH%

 call setenv.bat



 if "%ANT_HOME%" == "" (
   set ANT_HOME=%BASEDIR%\lib\apache-ant-1.7.0
 ) 

 set PATH=%ANT_HOME%\bin;%PATH%


 cd %BASEDIR%
	ant.bat -f %BASEDIR%/build.xml	all
	
 cd %STARTDIR%


:eof
endlocal