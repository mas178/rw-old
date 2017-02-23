# RW-Portal

## Setup Procedure

### Download repositories

```
cd ~
git clone https://github.com/mas178/rw-db.git
git clone https://github.com/mas178/rw-portal.git
git clone https://github.com/mas178/rw-camunda.git
```

### Build container

```
cd ~/rw-db
docker build -t rw-db:10.1 .

cd ~/rw-portal
docker build -t rw-portal:latest .

cd ~/rw-camunda
docker build -t rw-camunda:7.5 .
```

### Run containers

```
docker run -d \
    --name rw-db \
    -e "TZ=Asia/Tokyo" \
    -e MYSQL_RANDOM_ROOT_PASSWORD=yes \
    -p 3306:3306 \
    rw-db:10.1

docker run -d \
    --name rw-portal \
    --link rw-db \
    --link rw-camunda \
    -e "TZ=Asia/Tokyo" \
    -v ~/rw-portal/src:/src \
    -v ~/rw-portal/log:/var/log/rw-portal \
    -p 5000:5000 \
    rw-portal:latest

docker run  -d \
    --name rw-camunda \
    --link rw-db \
    -e "TZ=Asia/Tokyo" \
    -e DB_DRIVER=org.mariadb.jdbc.Driver \
    -e DB_URL=jdbc:mariadb://rw-db/process-engine \
    -e DB_USERNAME=camunda \
    -e DB_PASSWORD=camunda \
    -p 8080:8080 \
    rw-camunda:7.5
```

### (If needed,) Delete containers & images

```
docker stop rw-db
docker rm rw-db
docker rmi rw-db:10.1

docker stop rw-portal
docker rm rw-portal
docker rmi rw-portal:latest

docker stop rw-camunda
docker rm rw-camunda
docker rmi rw-camunda:7.5
```
