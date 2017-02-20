set pl=F:\.metadata\repo\stable2
cd %pl%
set classpath=%pl%\bin;%pl%\lib\*
java org.testng.TestNG %pl%\testng.xml
pause