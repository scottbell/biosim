@echo off
echo "*making biosim"
bash %BIOSIM_HOME%/bin/make-server.sh all
bash %BIOSIM_HOME%/bin/make-client.sh all
bash %BIOSIM_HOME%/bin/make-docs.sh all
echo "*done making biosim"