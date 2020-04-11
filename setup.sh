#!/usr/bin/env bash

## Install json-server to provide mock user-service API
echo "**************** Installing json-server ****************"
npm install -g json-server
echo "**************** json-server installation done ****************"

## Install newman to run the integration tests
echo "**************** Installing newman ****************"
npm install -g newman
echo "**************** newman installation done ****************"

## Run the json-server in watch mode which will serve as user-service
echo "**************** Running json-server ****************"
json-server --watch ./customer-service-mock/db.json --port 8000 --routes ./customer-service-mock/routes.json
