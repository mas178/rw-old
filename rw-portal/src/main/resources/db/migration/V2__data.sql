INSERT INTO tenants (id, name) VALUES ('hoge', '株式会社トヨテック');
INSERT INTO groups (id, name, tenant_id) VALUES ('FS', '財務部', 'hoge');

INSERT INTO users (username, encoded_password)
VALUES ('user1', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb');
INSERT INTO users (username, encoded_password)
VALUES ('user2', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb');

INSERT INTO groups_users (group_id, username) VALUES ('FS', 'user1');
INSERT INTO groups_users (group_id, username) VALUES ('FS', 'user2');
