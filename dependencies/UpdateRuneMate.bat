@echo off
cd %~dp0
echo Downloading HTML...
::https://stackoverflow.com/questions/32425973/how-can-i-get-html-from-page-with-cloudflare-ddos-portection
powershell -Command "(New-Object System.Net.WebClient).DownloadFile('https://www.runemate.com/download/', 'downloads.html')"
FOR /F tokens^=2^ delims^=^" %%W IN ('find "downloadLink =" downloads.html') DO set updateURL=%%W/standalone/RuneMate.jar
::del /Q downloads.html
cls
echo Downloading HTML... DONE!
echo Downloading Update...
powershell -Command "(New-Object System.Net.WebClient).DownloadFile('%updateURL%', 'RuneMate.jar')"
cls
echo Downloading HTML... DONE!
echo Downloading Update... DONE!
echo.
echo Press any key to exit. . .
pause>nul
exit
