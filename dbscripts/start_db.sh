#!/usr/bin/env bash
docker start f1bingodb || \
docker run --name f1bingodb -e POSTGRES_PASSWORD=mysecret -d -p 5435:5432 postgres:16
