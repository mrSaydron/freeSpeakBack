#!/bin/bash

echo "----- start init system -----"
echo ""

echo "----- config -----"
sudo apt update
sudo apt -y upgrade
uname -r
uname -a
sudo sed -i "s/#   PasswordAuthentication yes/PasswordAuthentication no/" /etc/ssh/ssh_config
sudo sh -c "echo 'AllowUsers aleksey' >> /etc/ssh/ssh_config"
sudo sh -c "echo 'PermitRootLogin no' >> /etc/ssh/ssh_config"
sudo systemctl reload sshd.service
sudo apt -y install htop
echo "----- config complete -----"
echo ""

echo "----- docker install -----"
sudo apt -y install ca-certificates curl gnupg lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt -y install docker-ce docker-ce-cli containerd.io
echo "----- docker complete -----"
echo ""

echo "----- docker compose install -----"
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
echo "----- docker compose install complete -----"
echo ""

echo "----- postgreSql install -----"
sudo apt -y install postgresql postgresql-contrib
sudo -u postgres psql -c "ALTER USER postgres PASSWORD '16729438';"
sudo sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" /etc/postgresql/12/main/postgresql.conf
sudo sh -c "echo 'host all all all md5' >> /etc/postgresql/12/main/pg_hba.conf"
echo "----- postgresSql complete -----"
echo ""

echo "----- unzip install -----"
sudo apt -y install unzip
echo "----- unzip install complete -----"
echo ""

echo "----- nginx install -----"
sudo apt-add-repository -y ppa:nginx/stable
sudo apt update
sudo apt -y install nginx
sudo systemctl enable nginx
echo "----- nginx install complete -----"

echo "----- snap install -----"
sudo apt -y install snapd
sudo snap install core
sudo snap refresh core
echo "----- snap install complete -----"

echo "----- certbot install -----"
sudo snap install --classic certbot
sudo certbot certonly --nginx -m mrsaydron@gmail.com
echo "----- tune cron -----"
crontab -l > foocron
echo "0 1 * * * sudo certbot renew --dry-run" >> foocron
crontab foocron
rm foocron
echo "----- certbot install complete -----"
