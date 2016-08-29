cd %~dp0
set cesta="C:\BoxScore"
mkdir %cesta%
xcopy /s BoxScore.jar %cesta%
xcopy /s README.txt %cesta%
set cesta="C:\BoxScore\lib"
mkdir %cesta%
xcopy /s lib %cesta%
set cesta="C:\BoxScore\data"
mkdir %cesta%
cd %cesta%
mkdir rosters
mkdir boxscores

set SCRIPT="%TEMP%\%RANDOM%-%RANDOM%-%RANDOM%-%RANDOM%.vbs"

echo Set oWS = WScript.CreateObject("WScript.Shell") >> %SCRIPT%
echo sLinkFile = "%USERPROFILE%\Desktop\BoxScore.lnk" >> %SCRIPT%
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> %SCRIPT%
echo oLink.TargetPath = "C:\BoxScore\BoxScore.jar" >> %SCRIPT%
echo oLink.Save >> %SCRIPT%

cscript /nologo %SCRIPT%
del %SCRIPT%

pause