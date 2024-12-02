
CREATE TABLE player (
    id_player INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id_player)
) ENGINE=INNODB;

CREATE TABLE idle_game (
    id_game INT AUTO_INCREMENT,
    created_at DATETIME,
    last_join DATETIME,
    region_id VARCHAR(100) NOT NULL,
    is_enabled boolean default TRUE,
    world_name VARCHAR(100) NOT NULL,
    player_name VARCHAR(100) NOT NULL,
    last_position TEXT DEFAULT NULL,
    PRIMARY KEY (id_game)
) ENGINE=INNODB;
