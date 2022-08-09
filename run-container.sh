#!/bin/sh
PG_SERVER_IP=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' pgserver)
docker run -d -p 8080:8080 --name homeincapi -e PG_SERVER_ADDRESS=$PG_SERVER_IP -e HOMEINCAPI_ALLOWED_SERVERS=http://localhost,http://0.0.0.0,http://0.0.0.0:3000,http://localhost:3000,http://44.198.99.95:3000,http://satan.com:6666 geojabs/homeincapi
docker logs -f homeincapi