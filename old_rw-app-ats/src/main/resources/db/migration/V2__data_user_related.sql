INSERT INTO tenants (id, name, created_by, updated_by, created_at, updated_at) VALUES ('dct', '株式会社DCT', 'sa', 'sa', SYSDATE(), SYSDATE());

INSERT INTO groups (id, name, tenant_id, created_by, updated_by, created_at, updated_at)
VALUES ('BA', 'ビジネス管理', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()), ('SA', 'システム管理', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('CC', 'コンサルタント', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE());

INSERT INTO users (username, encoded_password, tenant_id, created_by, updated_by, created_at, updated_at)
VALUES ('sa', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('hashimoto', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('tanigawa', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('paul', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('maeda', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('kadota', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('user1', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE()),
  ('inaba', 'b9b1a1fb64960e03ebb6d66727cd635c23981ff7a65b1ace8fe393063888a76120977818290bb7bb', 'dct', 'sa', 'sa', SYSDATE(), SYSDATE());

INSERT INTO groups_users (group_id, username)
VALUES ('BA', 'hashimoto'), ('SA', 'hashimoto'), ('CC', 'hashimoto'), ('BA', 'tanigawa'), ('SA', 'tanigawa'), ('CC', 'tanigawa'), ('CC', 'paul'),
  ('CC', 'maeda'), ('SA', 'inaba'), ('SA', 'sa'), ('SA', 'user1');
