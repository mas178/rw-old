#!/bin/bash
# Execute this script with "privileged: false" option
HOME_DIR="/home/vagrant/"

echo "================================================================================"
echo "Install g++"
echo "================================================================================"
sudo yum install -y gcc-c++

echo "================================================================================"
echo "Install git"
echo "================================================================================"
sudo yum install -y git

echo "================================================================================"
echo "Install maven"
echo "================================================================================"
sudo yum install -y maven

echo "================================================================================"
echo "Install nvm (node, npm)"
echo "================================================================================"
if [ ! -e ${HOME_DIR}.nvm/nvm.sh ]; then
  git clone git://github.com/creationix/nvm.git ${HOME_DIR}.nvm
  source ${HOME_DIR}.nvm/nvm.sh
  nvm install 4.4.1
  echo 'source ~/.nvm/nvm.sh' >> ~/.bash_profile
fi

echo "================================================================================"
echo "Install grunt-cli"
echo "================================================================================"
npm install -g grunt-cli@0.1.13
npm install utf-8-validate@1.2.1
npm install bufferutil@1.2.1

echo "================================================================================"
echo "Start npm install / Start grunt 1"
echo "================================================================================"
cd ${HOME_DIR}rw-front
npm install
grunt build:dev

echo "================================================================================"
echo "Start npm install / Start grunt 2"
echo "================================================================================"
npm install
grunt build:dev

echo "================================================================================"
echo "Start Jetty"
echo "================================================================================"
nohup mvn jetty:run -Pdevelop > jetty.log &
