#!/bin bash
docker compose --env-file .env.local -f docker-compose-local.yml up --build