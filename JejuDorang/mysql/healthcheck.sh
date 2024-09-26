#!/bin/bash

# healthcheck.sh

mysqladmin ping -h localhost -uroot -p${MYSQL_ROOT_PASSWORD} > /dev/null 2>&1

if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi
