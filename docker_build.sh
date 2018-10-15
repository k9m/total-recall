#!/usr/bin/env bash
mvn clean install -T 4 -DskipTests
docker-compose build
docker-compose up $1