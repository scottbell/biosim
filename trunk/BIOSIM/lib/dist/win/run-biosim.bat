@echo off
echo "Starting BioSim..."
start /B run-distro-nameserver.bat
start /B run-distro-server.bat
start /B run-distro-client.bat