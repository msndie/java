package edu.school21.chat.app;

import edu.school21.chat.repositories.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.zaxxer.hikari.HikariDataSource;

public class Main {
	public static void main(String[] args) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5433/day05");
		ds.setUsername("sclam");
		ds.setPassword("");
		MessagesRepositoryJdbcImpl rep = new MessagesRepositoryJdbcImpl(ds);
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter a message ID");
			long id = sc.nextLong();
			System.out.println(rep.findById(id).get());
		} catch (SQLException | NullPointerException | NoSuchElementException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		sc.close();
	}
}
