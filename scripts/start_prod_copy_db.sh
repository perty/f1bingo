#!/usr/bin/env bash
docker start f1bingodb-prodcopy || \
docker run --name f1bingodb-prodcopy -e POSTGRES_PASSWORD=mysecret -d -p 5535:5432 postgres:16
