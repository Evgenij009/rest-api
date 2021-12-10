create table tags
(
    id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE
);

create table gift_certificates
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name             varchar(128)  NOT NULL UNIQUE,
    description      VARCHAR(256) NOT NULL,
    price            DECIMAL(10, 2),
    create_date      TIMESTAMP DEFAULT NOW(),
    last_update_date TIMESTAMP DEFAULT NOW(),
    duration         INT          NOT NULL
);

create table gift_certificate_has_tag
(
    gift_certificate_id BIGINT UNSIGNED NOT NULL,
    tag_id         BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);