#!/bin/bash

echo "*running biosim"
ant run-nameserver &
run-server.sh &
run-client.sh
echo "*done running biosim"




