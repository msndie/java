package edu.school21.sockets.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessagesRepository implements CrudRepository<Message> {

    private JdbcTemplate template = null;

    @Autowired
    @Qualifier("dataSource")
    public void setRepository(HikariDataSource dataSource) {
        template = new JdbcTemplate();
        template.setDataSource(dataSource);
    }

    @Override
    public Message findById(Long id) {
        if (id == null) {
            return null;
        }
        return template.queryForObject("SELECT * FROM messages WHERE id = ? ;", new Object[]{id},
                ((rs, rowNum) -> new Message(rs.getLong(1),
                        rs.getLong(2), rs.getString(3), rs.getTimestamp(4))));
    }

    @Override
    public List<Message> findAll() {
        try {
            return template.query("SELECT * FROM messages ;",
                    ((rs, rowNum) -> new Message(rs.getLong(1),
                            rs.getLong(2), rs.getString(3), rs.getTimestamp(4))));
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean save(Message entity) {
        if (entity.getAuthorId() == null || entity.getText() == null) {
            return false;
        }
//        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(template);
//        entity.setId(simpleJdbcInsert.withTableName("messages")
//                .usingGeneratedKeyColumns("id")
//                .executeAndReturnKey(new BeanPropertySqlParameterSource(entity))
//                .longValue());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(template);
        simpleJdbcInsert.withTableName("messages").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author_id", entity.getAuthorId());
        parameters.put("message", entity.getText());
        parameters.put("time", entity.getTime());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        entity.setId(key.longValue());
        return true;
    }

    @Override
    public void update(Message entity) {
        if (entity.getId() == null || entity.getText() == null || entity.getAuthorId() == null) {
            return;
        }
        template.update("UPDATE messages SET author_id = ? , message = ? , time = ? WHERE id = ? ;",
                entity.getAuthorId(), entity.getText(), entity.getTime(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        template.update("DELETE FROM messages WHERE id = ? ;", id);
    }
}
