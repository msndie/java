package edu.school21.chat.repositories;

import java.sql.SQLException;
import java.util.Optional;
import edu.school21.chat.models.Message;

public interface MessagesRepository {
    Optional<Message> findById(Long id) throws SQLException;
    void save(Message message) throws SQLException;
    int update(Message message) throws SQLException;
}
