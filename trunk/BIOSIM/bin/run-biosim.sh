#!/bin/bash

echo "*running biosim"
run-nameserver.sh &
run-server.sh &
run-client.sh
echo "*done running biosim"




