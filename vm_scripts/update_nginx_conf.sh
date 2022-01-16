#!/bin/bash

echo "copy config"
sudo cp ./nginx.conf /etc/nginx/

echo "test"
sudo nginx -t

echo "reload"
sudo service nginx reload
