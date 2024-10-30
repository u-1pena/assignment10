DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int unsigned AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  birthday DATE NOT NULL,
  gender ENUM('male','female') NOT NULL,
  mobile_phone VARCHAR(100) NOT NULL,UNIQUE,
  email VARCHAR(100) NOT NULL,UNIQUE,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO users (first_name, last_name, birthday, gender, mobile_phone, email, password) VALUES ("yuichi", "shima", '1984-07-03','male', "090-1234-5678","shimaichi5973@gmail.com", "RaiseTech@2023");
INSERT INTO users (first_name, last_name, birthday, gender, mobile_phone, email, password) VALUES ("kana", "nishiyama", '1993-12-31','female', "080-1234-5678", "kana1234@yahoo.com", "MarinTokyo@2023");
