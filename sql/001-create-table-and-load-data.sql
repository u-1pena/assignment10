DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int unsigned AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  birthday DATE NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO users (name, birthday) VALUES ("yuichi", '1984-07-03');
INSERT INTO users (name, birthday) VALUES ("kana", '1993-12-31');
