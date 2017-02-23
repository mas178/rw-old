CREATE TABLE applicants (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  status_id   VARCHAR(255),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  age         INTEGER,
  birth_day   DATETIME,
  name        VARCHAR(127) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE applications (
  id           VARCHAR(30)  NOT NULL,
  created_at   DATETIME     NOT NULL,
  created_by   VARCHAR(255) NOT NULL,
  description  VARCHAR(800),
  status_id    VARCHAR(255),
  updated_at   DATETIME     NOT NULL,
  updated_by   VARCHAR(255) NOT NULL,
  applicant_id VARCHAR(30)  NOT NULL,
  company_id   VARCHAR(30)  NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE categories (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  status_id   VARCHAR(255),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  domain      VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE companies (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  status_id   VARCHAR(255),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE companies_categories (
  company_id  VARCHAR(30) NOT NULL,
  category_id VARCHAR(30) NOT NULL
);
CREATE TABLE groups (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  status_id   VARCHAR(255),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  tenant_id   VARCHAR(30)  NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE groups_users (
  group_id VARCHAR(30)  NOT NULL,
  username VARCHAR(255) NOT NULL
);
CREATE TABLE jobs (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  status_id   VARCHAR(255),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  company_id  VARCHAR(30)  NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE jobs_applications (
  application_id VARCHAR(30) NOT NULL,
  job_id         VARCHAR(30) NOT NULL
);
CREATE TABLE statuses (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  domain      VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE tasks (
  id                      VARCHAR(30)  NOT NULL,
  created_at              DATETIME     NOT NULL,
  created_by              VARCHAR(255) NOT NULL,
  description             VARCHAR(800),
  status_id               VARCHAR(255) NOT NULL,
  updated_at              DATETIME     NOT NULL,
  updated_by              VARCHAR(255) NOT NULL,
  planned_timestamp       DATETIME,
  actual_timestamp        DATETIME,
  planned_action          VARCHAR(255) NOT NULL,
  actual_action           VARCHAR(255) NOT NULL,
  name                    VARCHAR(127) NOT NULL,
  in_charge_user_username VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE tasks_applicants (
  task_id      VARCHAR(30) NOT NULL,
  applicant_id VARCHAR(30) NOT NULL
);
CREATE TABLE tasks_applications (
  task_id        VARCHAR(30) NOT NULL,
  application_id VARCHAR(30) NOT NULL
);
CREATE TABLE tasks_companies (
  task_id    VARCHAR(30) NOT NULL,
  company_id VARCHAR(30) NOT NULL
);
CREATE TABLE tasks_jobs (
  task_id VARCHAR(30) NOT NULL,
  job_id  VARCHAR(30) NOT NULL
);
CREATE TABLE tenants (
  id          VARCHAR(30)  NOT NULL,
  created_at  DATETIME     NOT NULL,
  created_by  VARCHAR(255) NOT NULL,
  description VARCHAR(800),
  status_id   VARCHAR(255),
  updated_at  DATETIME     NOT NULL,
  updated_by  VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE users (
  username         VARCHAR(255) NOT NULL,
  created_at       DATETIME     NOT NULL,
  created_by       VARCHAR(255) NOT NULL,
  description      VARCHAR(1200),
  encoded_password VARCHAR(255),
  updated_at       DATETIME     NOT NULL,
  updated_by       VARCHAR(255) NOT NULL,
  tenant_id        VARCHAR(30)  NOT NULL,
  PRIMARY KEY (username)
);
ALTER TABLE applications
  ADD CONSTRAINT FKdh2halxnbiwpkvpsgfjqrl0c4 FOREIGN KEY (applicant_id) REFERENCES applicants (id);
ALTER TABLE applications
  ADD CONSTRAINT FKhbqfa036nc7qtknfeakxuoqy8 FOREIGN KEY (company_id) REFERENCES companies (id);
ALTER TABLE companies_categories
  ADD CONSTRAINT FK72mu1vm00gdfyiobbdpifukve FOREIGN KEY (category_id) REFERENCES categories (id);
ALTER TABLE companies_categories
  ADD CONSTRAINT FK21lj0mfryppbcc80i21l9ogtf FOREIGN KEY (company_id) REFERENCES companies (id);
ALTER TABLE groups
  ADD CONSTRAINT FK81g0vs0l81kiifj5hn1dkv01n FOREIGN KEY (tenant_id) REFERENCES tenants (id);
ALTER TABLE groups_users
  ADD CONSTRAINT FKjo7q7ynjxnakhj4g97sd88v8f FOREIGN KEY (username) REFERENCES users (username);
ALTER TABLE groups_users
  ADD CONSTRAINT FKakkv3ihrlmgfjf50vj62a0jr6 FOREIGN KEY (group_id) REFERENCES groups (id);
ALTER TABLE jobs
  ADD CONSTRAINT FKrtmqcrktb6s7xq8djbs2a2war FOREIGN KEY (company_id) REFERENCES companies (id);
ALTER TABLE jobs_applications
  ADD CONSTRAINT FKljx90pt2ciuj8fxhcsikxjwy1 FOREIGN KEY (job_id) REFERENCES jobs (id);
ALTER TABLE jobs_applications
  ADD CONSTRAINT FKkap6c0ixt35h8sk7m0nhur15j FOREIGN KEY (application_id) REFERENCES applications (id);
ALTER TABLE tasks
  ADD CONSTRAINT FKalfswop4xg3a7sqs7k0rhr5si FOREIGN KEY (in_charge_user_username) REFERENCES users (username);
ALTER TABLE tasks_applicants
  ADD CONSTRAINT FKdpm2bd4u4ed8cpx9vhv19w89d FOREIGN KEY (applicant_id) REFERENCES applicants (id);
ALTER TABLE tasks_applicants
  ADD CONSTRAINT FKgyv7dkv70e5n6dyw5l5m37fxa FOREIGN KEY (task_id) REFERENCES tasks (id);
ALTER TABLE tasks_applications
  ADD CONSTRAINT FKf3yg1gcpnbonihrpk94mvh6st FOREIGN KEY (application_id) REFERENCES applications (id);
ALTER TABLE tasks_applications
  ADD CONSTRAINT FKiwxtotr5kxhkllq5hwihaeg2w FOREIGN KEY (task_id) REFERENCES tasks (id);
ALTER TABLE tasks_companies
  ADD CONSTRAINT FK7up4v0w39ns1dyj1estdyo8wb FOREIGN KEY (company_id) REFERENCES companies (id);
ALTER TABLE tasks_companies
  ADD CONSTRAINT FKdt0msjr9o7mmpwntal3uhdpk9 FOREIGN KEY (task_id) REFERENCES tasks (id);
ALTER TABLE tasks_jobs
  ADD CONSTRAINT FKrmtg0kx264di2ltvvmvstdbbw FOREIGN KEY (job_id) REFERENCES jobs (id);
ALTER TABLE tasks_jobs
  ADD CONSTRAINT FKoyy2bg7f2hycpnlini82xsujw FOREIGN KEY (task_id) REFERENCES tasks (id);
ALTER TABLE users
  ADD CONSTRAINT FK21hn1a5ja1tve7ae02fnn4cld FOREIGN KEY (tenant_id) REFERENCES tenants (id);
