@echo off

 setlocal
 if "%JAVA_HOME%" == "" (
	echo variable JAVA_HOME must be set up
	pause
	goto eof
 ) 


 echo "JAVA version must be 1.6"
 pause

 set PATH=%JAVA_HOME%\bin;%PATH%

 call setenv.bat



 if "%ANT_HOME%" == "" (
   set ANT_HOME=%BASEDIR%\lib\apache-ant-1.7.0
 ) 

 set PATH=%ANT_HOME%\bin;%PATH%

 


 cd %BASEDIR%
	java -jar lib/dsigcollector-1.0.jar
	
 cd %STARTDIR%


:eof
endlocal