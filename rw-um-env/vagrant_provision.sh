echo "================================================================================"
echo "Install delta rpms"
echo "================================================================================"
sudo yum install -y deltarpm

echo "================================================================================"
echo "Install epel-release"
echo "================================================================================"
sudo yum install -y epel-release

echo "================================================================================"
echo "Install OpenSSL"
echo "================================================================================"
sudo yum install -y openssl, openssl-devel

echo "================================================================================"
echo "Install g++ (for install python)"
echo "================================================================================"
sudo yum install -y gcc-c++

echo "================================================================================"
echo "Install git (for pyenv-installer)"
echo "================================================================================"
sudo yum install -y git

echo "================================================================================"
echo "Install MongoDB"
echo "================================================================================"
cat << EOF > mongodb-org-3.2.repo
[mongodb-org-3.2]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/7Server/mongodb-org/3.2/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-3.2.asc
EOF
sudo mv mongodb-org-3.2.repo /etc/yum.repos.d/
sudo yum install -y mongodb-org
sudo service mongod start
sudo chkconfig mongod on

echo "================================================================================"
echo "Install pip"
echo "================================================================================"
sudo yum install -y python-pip

echo "================================================================================"
echo "Install pyenv & virtualenv"
echo "================================================================================"
sudo curl -L https://raw.githubusercontent.com/yyuu/pyenv-installer/master/bin/pyenv-installer | bash
echo 'export PATH="~/.pyenv/bin:$PATH"'  >> ~/.bash_profile
echo 'eval "$(pyenv init -)"'            >> ~/.bash_profile
echo 'eval "$(pyenv virtualenv-init -)"' >> ~/.bash_profile
source ~/.bash_profile

echo "================================================================================"
echo "Install Python 3.5.1"
echo "================================================================================"
pyenv install 3.5.1
pyenv local 3.5.1

echo "================================================================================"
echo "Setup virtualenv"
echo "================================================================================"
pyenv virtualenv rw-um
pyenv rehash
pyenv local rw-um

echo "================================================================================"
echo "Install packages"
echo "================================================================================"
cd ~/rw-um
pip install --upgrade pip
pip install -r requirements.txt

echo "================================================================================"
echo "Execute rw-um (http://192.168.43.178:8080/)"
echo "================================================================================"
nohup python src/runserver.py &
