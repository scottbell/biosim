@echo off
echo "Starting BioSim..."
start /B run-distro-nameserver.bat
sleep 10
start /B run-distro-server.bat
sleep 10
start /B run-distro-client.bat
echo CLOSE THIS WINDOW TO QUIT