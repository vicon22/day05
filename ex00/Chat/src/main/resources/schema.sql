DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA chat;

CREATE TABLE chat.users(
	user_id SERIAL,
	PRIMARY KEY(user_id),
	user_login varchar(50) UNIQUE NOT NULL,
	user_password varchar(50) NOT NULL,
	created_rooms int[],
	chatrooms int[]
);

CREATE TABLE chat.chatrooms(
	chatroom_id SERIAL,
	PRIMARY KEY(chatroom_id),
	chatroom_name varchar(100)  UNIQUE NOT NULL,
	chatroom_owner INTEGER,
	FOREIGN KEY (chatroom_owner) REFERENCES chat.users(user_id),
	messages int[]
);

CREATE TABLE chat.messages(
	message_id SERIAL,
	PRIMARY KEY(message_id),
	message_author INTEGER,
	FOREIGN KEY (message_author) REFERENCES chat.users(user_id),
	message_room INTEGER,
	FOREIGN KEY (message_room) REFERENCES chat.chatrooms(chatroom_id),
	message_text text,
	message_date timestamp
);