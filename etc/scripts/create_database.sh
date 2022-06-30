#!/usr/bin/env bash

. ./env.sh
set -e

if docker create --name ${DOCKER_DB_NAME} -p 5500:5432 -e POSTGRES_DB=${DB_NAME} -e POSTGRES_USER=${DB_NAME} -e POSTGRES_PASSWORD=${DB_NAME} postgres ; then
	echo "Container created! To start it run start_database.sh"
else
	echo "Container ${DOCKER_DB_NAME} already exists!"
fi
