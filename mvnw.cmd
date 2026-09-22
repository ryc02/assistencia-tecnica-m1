@echo off
setlocal
if exist "%~dp0tools\maven\bin\mvn.cmd" (
    "%~dp0tools\maven\bin\mvn.cmd" %*
) else (
    mvn %*
)
