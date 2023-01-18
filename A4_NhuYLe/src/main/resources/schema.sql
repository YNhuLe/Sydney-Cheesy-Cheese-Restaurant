CREATE TABLE units (
  id INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL
);
            
CREATE TABLE cheeses (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  quantity INT,
  weight INT NOT NULL,
  unitsId INT NOT NULL,
  price DECIMAL(5, 2),
  specSheet VARCHAR(255)
);

CREATE TABLE users (
userId             BIGINT PRIMARY KEY AUTO_INCREMENT,
userName           VARCHAR(64) NOT NULL UNIQUE,
email              VARCHAR(75) NOT NULL UNIQUE,
password           VARCHAR(128) NOT NULL,
enabled            BOOLEAN NOT NULL 
);

CREATE TABLE roles (
roleId   BIGINT PRIMARY KEY AUTO_INCREMENT,
roleName    VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_role (
id       BIGINT PRIMARY KEY AUTO_INCREMENT,
userId    BIGINT NOT NULL,
roleId    INTEGER NOT NULL,
UNIQUE   (userId, roleId),
FOREIGN    KEY (userId) REFERENCES users(userId),
FOREIGN    KEY (roleId) REFERENCES roles(roleId)
);