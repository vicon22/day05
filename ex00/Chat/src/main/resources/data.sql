INSERT INTO chat.users (user_login, user_password, created_rooms) VALUES ('EVGENII', '123', ARRAY [1]);
INSERT INTO chat.users (user_login, user_password, created_rooms) VALUES ('NAIL', '456', ARRAY [2]);
INSERT INTO chat.users (user_login, user_password, created_rooms) VALUES ('FIDAN', '789', ARRAY [3]);
INSERT INTO chat.users (user_login, user_password, created_rooms) VALUES ('IRINA', '131', ARRAY [4]);
INSERT INTO chat.users (user_login, user_password, created_rooms) VALUES ('REGINA', '234', ARRAY [5]);

INSERT INTO chat.chatrooms (chatroom_name, chatroom_owner) VALUES ('11A', 1);
INSERT INTO chat.chatrooms (chatroom_name, chatroom_owner) VALUES ('BEXET', 2);
INSERT INTO chat.chatrooms (chatroom_name, chatroom_owner) VALUES ('IKIGAI', 3);
INSERT INTO chat.chatrooms (chatroom_name, chatroom_owner) VALUES ('JUST A CHAT', 4);
INSERT INTO chat.chatrooms (chatroom_name, chatroom_owner) VALUES ('REGINA''S DREAMS', 5);

INSERT INTO chat.messages (message_author, message_room, message_text) VALUES (1,3,'Hello, Fidan');
INSERT INTO chat.messages (message_author, message_room, message_text) VALUES (2,5,'Regina, what is your dream?');
INSERT INTO chat.messages (message_author, message_room, message_text) VALUES (5,5,'Its a complicated question. I need time for think');
INSERT INTO chat.messages (message_author, message_room, message_text) VALUES (3,3,'We have to come over to me tonight...');
INSERT INTO chat.messages (message_author, message_room, message_text) VALUES (4,4,'Son, how are you? Why did you lost my calls???');