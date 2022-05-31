INSERT INTO "User"(login, password)
VALUES ('keker', '123456');

INSERT INTO "User"(login, password)
VALUES ('loler', 'qwerty');

INSERT INTO "User"(login, password)
VALUES ('kekloler', '123qwe');

INSERT INTO "User"(login, password)
VALUES ('lolkeker', 'qwe123');

INSERT INTO "User"(login, password)
VALUES ('koklel', 'ytrewq');

INSERT INTO Chatroom(name, user_id)
VALUES ('raz', 1);

INSERT INTO Chatroom(name, user_id)
VALUES ('dva', 2);

INSERT INTO Chatroom(name, user_id)
VALUES ('tri', 3);

INSERT INTO Chatroom(name, user_id)
VALUES ('four', 4);

INSERT INTO Chatroom(name, user_id)
VALUES ('five', 5);

INSERT INTO Message (author_id, room_id, message_text)
VALUES (1, 1, 'ya keker');

INSERT INTO Message (author_id, room_id, message_text)
VALUES (2, 2, 'ya loler');

INSERT INTO Message (author_id, room_id, message_text)
VALUES (3, 3, 'ya kekloler');

INSERT INTO Message (author_id, room_id, message_text)
VALUES (4, 4, 'ya lolkeker');

INSERT INTO Message (author_id, room_id, message_text)
VALUES (5, 5, 'ya koklel');