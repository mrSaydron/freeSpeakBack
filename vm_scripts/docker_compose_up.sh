#!/bin/bash

sudo docker login --username oauth --password AQAAAAAPJZSDAATuwanqBSRBy0stjeFyyENCOAA cr.yandex
sudo docker-compose pull
sudo docker-compose up --force-recreate --build -d
sudo docker image prune -f
