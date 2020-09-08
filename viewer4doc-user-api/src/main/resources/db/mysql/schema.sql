CREATE TABLE IF NOT EXISTS user (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  email VARCHAR(30),
  password VARCHAR(50),
  level INT(4)
  fileId VARCHAR(30)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS files (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  email VARCHAR(30),
  password VARCHAR(50),
  level INT(4)
  fileId VARCHAR(30)
) engine=InnoDB;
