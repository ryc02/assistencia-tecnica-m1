@echo off
title Console do Banco de Dados H2
color 0A

echo ===================================================
echo     Assistência Técnica M1 - Acesso ao Banco
echo ===================================================
echo.
echo O navegador abrira automaticamente.
echo.
echo Copie esta URL e cole no campo "JDBC URL" do site:
echo jdbc:h2:file:%CD%\data\assistenciadb;MODE=MySQL;AUTO_SERVER=TRUE
echo.
echo Usuario: sa
echo Senha: (deixe em branco)
echo.
echo ===================================================

:: Tenta executar o jar do H2 que o Maven já baixou na sua máquina
java -cp "%USERPROFILE%\.m2\repository\com\h2database\h2\2.2.224\h2-2.2.224.jar" org.h2.tools.Server -web -browser -tcp -tcpAllowOthers -webAllowOthers

pause
