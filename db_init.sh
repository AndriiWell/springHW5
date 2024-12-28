#!/bin/bash
envsubst < /docker-entrypoint-initdb.d/db_init.sql > /docker-entrypoint-initdb.d/db_init.sql.tmp
mv /docker-entrypoint-initdb.d/db_init.sql.tmp /docker-entrypoint-initdb.d/db_init.sql