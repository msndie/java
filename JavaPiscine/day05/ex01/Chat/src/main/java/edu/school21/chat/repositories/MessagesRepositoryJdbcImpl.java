package edu.school21.chat.repositories;

import edu.school21.chat.models.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        String script = "SELECT * FROM message "
        + "LEFT JOIN (SELECT Chatroom.id AS id_room, name AS room_name, Chatroom.user_id, "
        + "login AS owner_login, password AS owner_password FROM Chatroom "
        + "LEFT JOIN \"User\" AS t1 ON user_id=t1.id) AS t1 ON id_room=room_id "
        + "LEFT JOIN \"User\" AS t2 ON author_id=t2.id;";
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(script)) {
                User user = null;
                User roomOwner = null;
                Chatroom chatroom = null;
                Message message = null;
                while (rs.next()) {
                    if (rs.getInt("id") == id) {
                        user = new User(rs.getInt("author_id"),
                                        rs.getString("login"),
                                        rs.getString("password"));
                        roomOwner = new User(rs.getInt("user_id"),
                                            rs.getString("owner_login"),
                                            rs.getString("owner_password"));
                        chatroom = new Chatroom(rs.getInt("id_room"),
                                                rs.getString("room_name"),
                                                roomOwner);
                        message = new Message(rs.getInt("id"),
                                            user,
                                            chatroom,
                                            rs.getString("message_text"),
                                            rs.getTimestamp("message_date"));
                        break;
                    }
                }
                return Optional.of(message);
            }
    }
}
