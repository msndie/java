package edu.school21.chat.repositories;

import edu.school21.chat.models.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public int update(Message message) throws SQLException {
        String script = "UPDATE message "
                        + "SET author_id = ? , "
                        + "room_id = ? , "
                        + "message_text = ? , "
                        + "message_date = ? "
                        + "WHERE id = ? ;";
        int affectedRows = 0;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(script)) {
            pstmt.setInt(1, message.getAuthor().getId().intValue());
            pstmt.setInt(2, message.getRoom().getId().intValue());
            pstmt.setString(3, message.getMessage());
            if (message.getDate() == null) {
                pstmt.setNull(4, java.sql.Types.TIMESTAMP);
            } else {
                pstmt.setTimestamp(4, message.getDate());
            }
            pstmt.setInt(5, message.getId().intValue());
            affectedRows = pstmt.executeUpdate();
        }
        return affectedRows;
    }

    @Override
    public void save(Message message) throws SQLException {
        String script = "INSERT INTO Message(author_id, room_id, message_text) "
                        + "VALUES(?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            int author_id = message.getAuthor().getId() == null ? -1 : message.getAuthor().getId().intValue();
            int room_id = message.getRoom().getId() == null ? -1 : message.getRoom().getId().intValue();
            if (author_id == -1 || room_id == -1) {
                throw new NotSavedSubEntityException("No such ID in DB");
            }
            String sql = "SELECT * FROM \"User\" WHERE id=" + author_id + ";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (!rs.next()) {
                throw new NotSavedSubEntityException("No such ID in DB");
            }
            sql = "SELECT * FROM Chatroom WHERE id=" + room_id + ";";
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if (!rs.next()) {
                throw new NotSavedSubEntityException("No such ID in DB");
            }
            try (PreparedStatement pstmt = connection.prepareStatement(script,
                Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, author_id);;
                pstmt.setInt(2, room_id);
                pstmt.setString(3, message.getMessage());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rSet = pstmt.getGeneratedKeys()) {
                        if (rSet.next()) {
                            message.setId(rSet.getInt(1));
                        }
                    }
                }
            }
        }
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
                        user = new User((long)rs.getInt("author_id"),
                                        rs.getString("login"),
                                        rs.getString("password"));
                        roomOwner = new User((long)rs.getInt("user_id"),
                                            rs.getString("owner_login"),
                                            rs.getString("owner_password"));
                        chatroom = new Chatroom((long)rs.getInt("id_room"),
                                                rs.getString("room_name"),
                                                roomOwner);
                        message = new Message((long)rs.getInt("id"),
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
