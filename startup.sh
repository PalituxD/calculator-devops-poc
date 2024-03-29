#!/bin/bash

source env.sh

sh ./shutdown.sh

echo "Starting... Build Application Jar"
mvn clean install -f $PWD/app/pom.xml
sleep 1
echo "End... Build Application Jar"

echo "Starting... Build docker images"
docker-compose build
sleep 1
echo "End... Build docker images"

echo "Starting... Run the database"
docker-compose up -d ${ENV_DATABASE_SERVER}
sleep 10
echo "End... Run the database"

echo "Starting... first node"
docker-compose up -d ${ENV_APPLICATION_SERVER}
sleep 20
echo "End... first node has been deployed"

echo "Starting... second node"
docker-compose up -d ${ENV_APPLICATION_SERVER_2}
sleep 20
echo "End... second node has been deployed"

echo "Starting... Run other tools"
docker-compose up -d
sleep 15
echo "End... Run other tools"

sleep 1
echo "Enjoy the application"

echo "To test the app: exec 'curl -X PUT http://localhost:${ENV_APPLICATION_PORT}/sum/10/20'"
echo "To open prometheus: http://localhost:${ENV_COLLECTOR_PORT}"
echo "To open grafana: http://localhost:${ENV_MONITOR_PORT}"
