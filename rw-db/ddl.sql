CREATE DATABASE IF NOT EXISTS `portal`;
CREATE USER 'portal'@'%' IDENTIFIED BY 'portal';
GRANT CREATE, DELETE, INSERT, SELECT, UPDATE ON `portal`.* TO 'portal'@'%';
GRANT DELETE, INSERT, SELECT, UPDATE ON `camunda`.* TO 'portal'@'%';

CREATE DATABASE IF NOT EXISTS `process-engine`;
CREATE USER 'camunda'@'%' IDENTIFIED BY 'camunda';
GRANT ALTER, CREATE, DELETE, INDEX, INSERT, SELECT, UPDATE ON `process-engine`.* TO 'camunda'@'%';

CREATE USER 'test'@'%' IDENTIFIED BY 'test';
GRANT ALL ON *.* TO 'test'@'%';

FLUSH PRIVILEGES;
