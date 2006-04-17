@echo off
start /B run-nameserver.bat
start /B run-server.bat -demo
start /B run-client.bat