#!/bin/bash

echo "*running biosim"
run-nameserver.sh &
sleep 5
run-server.sh &
sleep 5
run-client.sh
echo "*done running biosim"




