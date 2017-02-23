CREATE TABLE tenants (
  id   VARCHAR(100) NOT NULL PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE groups (
  id        VARCHAR(100) NOT NULL PRIMARY KEY,
  name      VARCHAR(255),
  tenant_id VARCHAR(100) NOT NULL
);

CREATE TABLE users (
  username         VARCHAR(100) NOT NULL PRIMARY KEY,
  encoded_password VARCHAR(255)
);

CREATE TABLE groups_users (
  id       INT PRIMARY KEY AUTO_INCREMENT,
  group_id VARCHAR(100) NOT NULL,
  username  VARCHAR(100) NOT NULL
);

ALTER TABLE groups
  ADD FOREIGN KEY (tenant_id) REFERENCES tenants (id);
ALTER TABLE groups_users
  ADD FOREIGN KEY (group_id) REFERENCES groups (id);
ALTER TABLE groups_users
  ADD FOREIGN KEY (username) REFERENCES users (username);
ALTER TABLE groups_users
  ADD UNIQUE (group_id, username);