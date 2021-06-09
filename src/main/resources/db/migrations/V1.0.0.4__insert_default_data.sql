INSERT INTO customer (id, "name") VALUES (1, 'Ezequiel Juliano');
INSERT INTO customer (id, "name") VALUES (2, 'João da Silva');
INSERT INTO customer (id, "name") VALUES (3, 'Paulo Costa');
INSERT INTO customer (id, "name") VALUES (4, 'Alberto Souza');
INSERT INTO customer (id, "name") VALUES (5, 'Anderson Silveira');

INSERT INTO payment (id, "date", customer_id) VALUES (1, CURRENT_TIMESTAMP, 1);
INSERT INTO payment (id, "date", customer_id) VALUES (2, now()::DATE - 1, 2);
INSERT INTO payment (id, "date", customer_id) VALUES (3, CURRENT_TIMESTAMP, 3);
INSERT INTO payment (id, "date", customer_id) VALUES (4, now()::DATE - 30, 4);
INSERT INTO payment (id, "date", customer_id) VALUES (5, now()::DATE - 1, 5);
INSERT INTO payment (id, "date", customer_id) VALUES (6, CURRENT_TIMESTAMP, 1);

INSERT INTO product (id, name, file, price, payment_id) VALUES (1, 'Oasis Album', '/musics/oasis.mp3', 100.0, 1);
INSERT INTO product (id, name, file, price, payment_id) VALUES (2, 'U2 Album', '/musics/u2.mp3', 150.0, 2);
INSERT INTO product (id, name, file, price, payment_id) VALUES (3, 'The Killers Album', '/musics/the-killers.mp3', 125.0, 3);
INSERT INTO product (id, name, file, price, payment_id) VALUES (4, 'Avicii Album', '/musics/avicii.mp3', 90.0, 4);
INSERT INTO product (id, name, file, price, payment_id) VALUES (5, 'Calvin Harris Album', '/musics/calvin-harris.mp3', 85.0, 5);
INSERT INTO product (id, name, file, price, payment_id) VALUES (6, 'Bastille Album', '/musics/bastille.mp3', 90.0, 6);
INSERT INTO product (id, name, file, price, payment_id) VALUES (7, 'ACDC Album', '/musics/acdc.mp3', 90.0, 6);
INSERT INTO product (id, name, file, price, payment_id) VALUES (8, 'Oasis Album', '/musics/oasis.mp3', 100.0, 2);
INSERT INTO product (id, name, file, price, payment_id) VALUES (9, 'The Killers Album', '/musics/the-killers.mp3', 125.0, 2);
INSERT INTO product (id, name, file, price, payment_id) VALUES (10, 'Calvin Harris Album', '/musics/calvin-harris.mp3', 85.0, 3);
INSERT INTO product (id, name, file, price, payment_id) VALUES (11, 'Calvin Harris Album', '/musics/calvin-harris.mp3', 85.0, 5);
INSERT INTO product (id, name, file, price, payment_id) VALUES (12, 'Oasis Album', '/musics/oasis.mp3', 100.0, 3);
INSERT INTO product (id, name, file, price, payment_id) VALUES (13, 'Bastille Album', '/musics/bastille.mp3', 90.0, 4);