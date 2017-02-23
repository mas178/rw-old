#!/bin/bash
# Execute this script with "privileged: false" option
sudo yum -y install epel-release
sudo yum -y install ansible
sudo yum -y install expect

#---------------
# connect from host to web/db via SSH
#---------------
if [ ! -e .ssh/config ]; then

  cat << EOS > .ssh/config
Host web
  HostName 192.168.178.2
Host db
  HostName 192.168.178.3
EOS

  chmod 600 .ssh/config

  expect -c "
  set timeout 10
  spawn ssh-keygen -t rsa
  expect -re \"Enter file in which to save the key\ (.\*\):\"
  send \"\n\"
  expect \"Enter passphrase \(empty for no passphrase\):\"
  send \"\n\"
  expect \"Enter same passphrase again:\"
  send \"\n\"
  expect eof exit 0
  "

  expect -c "
  set timeout 10
  spawn ssh-copy-id web
  expect -re \"Are you sure you want to continue connecting\"
  send \"yes\n\"
  expect -re \"password: $\"
  send \"vagrant\n\"
  expect -re \"password: $\"
  send \"vagrant\n\"
  expect eof exit 0
  "

  expect -c "
  set timeout 10
  spawn ssh-copy-id db
  expect -re \"Are you sure you want to continue connecting\"
  send \"yes\n\"
  expect -re \"password: $\"
  send \"vagrant\n\"
  expect -re \"password: $\"
  send \"vagrant\n\"
  expect eof exit 0
  "
fi
