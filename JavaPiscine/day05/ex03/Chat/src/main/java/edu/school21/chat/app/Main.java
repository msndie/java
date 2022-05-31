package edu.school21.chat.app;

import edu.school21.chat.repositories.*;
import edu.school21.chat.models.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.zaxxer.hikari.HikariDataSource;

public class Main {
	public static void main(String[] args) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5433/day05");
		ds.setUsername("sclam");
		ds.setPassword("");
		try {
			MessagesRepositoryJdbcImpl rep = new MessagesRepositoryJdbcImpl(ds);
			Optional<Message> messageOptional = rep.findById(6L);
			if (messageOptional.isPresent()) {
				Message message = messageOptional.get();
				message.setMessage("bye!");
				message.setDate(null);
				rep.update(message);
			}
		} catch (SQLException | NullPointerException | NoSuchElementException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}
