package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class User {
	private long id;
	private final String login;
	private final String password;
	private ArrayList<Chatroom> createdRooms;
	private ArrayList<Chatroom> socializesRooms;

	public User(long id, String login, String password) {
		this.login = login;
		this.password = password;
		this.id = id;
		createdRooms = null;
		socializesRooms = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User u = (User) obj;
		return Long.compare(u.id, id) == 0
				&& Objects.equals(u.login, login)
				&& Objects.equals(u.password, password)
				&& Objects.equals(u.createdRooms, createdRooms)
				&& Objects.equals(u.socializesRooms, socializesRooms);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, createdRooms, socializesRooms);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", createdRooms=" + createdRooms +
				", socializesRooms=" + socializesRooms +
				'}';
	}
}