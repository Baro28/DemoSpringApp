#!/usr/bin/env bash

. ./env.sh
set -e

if docker start ${DOCKER_DB_NAME} ; then
	echo "Container started successfully!"
else
	echo "Starting failed! To create a container run create_database.sh"
fi
