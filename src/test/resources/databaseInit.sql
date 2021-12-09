INSERT INTO gift_certificates (name, description, price, create_date, last_update_date, duration)
VALUES ('certificate 1', 'description 1', 1.1, '2021-12-09 01:11:11', '2021-12-09 01:22:11', 1),
       ('certificate 2', 'description 2', 2.2, '2021-12-09 02:22:22', '2021-12-09 02:22:22', 2),
       ('certificate 3', 'description 3', 3.3, '2021-12-09 03:33:33', '2021-12-09 03:44:33', 3);

INSERT INTO tags (name) VALUES ('tag 1'), ('tag 2'), ('tag 3'), ('tag 4');

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 1), (1, 3), (2, 2);