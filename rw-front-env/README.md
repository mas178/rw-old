# RW-Front-Env

This repo is development environment of [RW-Front](https://github.com/mas178/rw-front).

## Setup

```sh
$ git clone https://github.com/mas178/rw-front-env.git
$ cd rw-front-env
$ git clone https://github.com/mas178/rw-front.git
$ vagrant up
```

## Screens

|Screen Name|URL|User|Password|
|---|---|---|---|
|admin|http://192.168.11.178:8080/camunda/app/admin/|jonny1|jonny1|
|cockpit|http://192.168.11.178:8080/camunda/app/cockpit/|jonny1|jonny1|
|tasklist|http://192.168.11.178:8080/camunda/app/tasklist/|jonny1|jonny1|

## Issues

I don't know why, but `grunt auto-build` doesn't work.

Use `grunt build:dev:cockpit` each time when you change the codes.
