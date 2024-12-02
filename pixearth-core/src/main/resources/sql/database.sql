CREATE TABLE idle_option (
	option_id INT AUTO_INCREMENT,
	option_name VARCHAR(100) UNIQUE NOT NULL,
	option_value LONGTEXT,
	CONSTRAINT fk_id_idle_option PRIMARY KEY (option_id)
);