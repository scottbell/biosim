#!/bin/bash

echo "*running biosim"
run-nameserver &
sleep 5
run-server &
sleep 5
run-client
echo "*done running biosim"




