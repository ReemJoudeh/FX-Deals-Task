CREATE DATABASE IF NOT EXISTS fx_deals;

CREATE
    USER 'fx_user'@'%' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON fx_deals.* TO
    'fx_user'@'%';
FLUSH PRIVILEGES;

USE fx_deals;

CREATE TABLE `fx_deal`
(
    `id`                     bigint       NOT NULL AUTO_INCREMENT,
    `deal_unique_id`         varchar(255) NOT NULL,
    `from_currency_iso_code` varchar(3)   NOT NULL,
    `to_currency_iso_code`   varchar(3)   NOT NULL,
    `deal_timestamp`         varchar(255) NOT NULL,
    `deal_amount`            double       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `deal_unique_id` (`deal_unique_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `rejected_deals_log`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `file_name` varchar(45)  NOT NULL,
    `Reason`    varchar(1000) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;