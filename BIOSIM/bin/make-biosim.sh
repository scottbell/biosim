#!/bin/bash

echo "*making biosim"
make-server.sh all
make-client.sh all
make-docs.sh
echo "*done making biosim"




