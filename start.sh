#!/bin/bash
set -x

docker compose --profile mongo --profile hello-service up -d 
