# RW-DB

## Setup Procedure

### Download repositories

```
cd ~
git clone https://github.com/mas178/rw-db.git
```

### Build & Run container

```
cd ~/rw-db
docker build -t rw-db:10.1 .
docker run -d \
    --name rw-db \
    -e "TZ=Asia/Tokyo" \
    -e MYSQL_RANDOM_ROOT_PASSWORD=yes \
    -p 3306:3306 \
    rw-db:10.1
```

### (If needed,) Delete containers & images

```
docker stop rw-db
docker rm rw-db
docker rmi rw-db:10.1
```
