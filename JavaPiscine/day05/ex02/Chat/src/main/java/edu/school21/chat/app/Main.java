package edu.school21.chat.app;

import edu.school21.chat.repositories.*;
import edu.school21.chat.models.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.zaxxer.hikari.HikariDataSource;

public class Main {
	public static void main(String[] args) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5433/day05");
		ds.setUsername("sclam");
		ds.setPassword("");
		try {
			MessagesRepositoryJdbcImpl rep = new MessagesRepositoryJdbcImpl(ds);
			User creator = new User(4L, "user", "user");
			User author = creator;
			Chatroom room = new Chatroom(4L, "room", creator);
			Message message = new Message(null, author, room, "Hello!", Timestamp.valueOf(LocalDateTime.now()));
			rep.save(message);
			System.out.println(message.getId());
		} catch (SQLException | NullPointerException | NoSuchElementException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}
