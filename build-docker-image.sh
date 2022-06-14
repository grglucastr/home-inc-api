#!/bin/sh


PG_SERVER_IP=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' pgserver)

docker stop homeincapi
docker rm homeincapi
docker rmi geojabs/homeincapi
docker build -t geojabs/homeincapi .
docker run -d -p 8080:8080 --name homeincapi -e PG_SERVER_ADDRESS=$PG_SERVER_IP geojabs/homeincapi
docker logs -f homeincapi
