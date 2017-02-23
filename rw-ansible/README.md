# rw-ansible

Vagrant & Ansible script to configure development environment for RW project.

## Structure

- Ansible Host
  - Inventory (hosts)
  - ansible.cfg
  - Playbook (site.yml)
- Web Server
  - Nginx
  - Java
  - Tomcat
  - Camunda
- DB Server
  - MariaDB

## Environment

- Vagrant 1.8.1
- CentOS 7.2
- Ansible 2.0.2.0

## Setup Procedure

```
$ git clone https://github.com/mas178/rw-ansible.git
$ cd rw-ansible/
$ vagrant up
```

After that, you can see Tomcat admin page at [http://192.168.178.1:8080/](http://192.168.178.1:8080/).
