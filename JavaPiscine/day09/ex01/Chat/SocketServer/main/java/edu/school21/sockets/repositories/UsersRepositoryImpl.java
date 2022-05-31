package edu.school21.sockets.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

	private JdbcTemplate template = null;

	@Autowired
	@Qualifier("dataSource")
	public void setRepository(HikariDataSource dataSource) {
		template = new JdbcTemplate();
		template.setDataSource(dataSource);
	}

	@Override
	public User findById(Long id) {
		if (id == null) {
			return null;
		}
		return template.queryForObject("SELECT * FROM users WHERE id = ? ;", new Object[]{id} ,
				((rs, rowNum) -> new User(rs.getLong(1), rs.getString(2), rs.getString(3))));
	}

	@Override
	public List<User> findAll() {
		try {
			return template.query("SELECT * FROM users ;",
					((rs, rowNum) -> new User(rs.getLong(1),
							rs.getString(2),
							rs.getString(3))));
		} catch (DataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean save(User entity) {
		if (entity.getName() == null || entity.getPassword() == null) {
			return false;
		}
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(template);
		simpleJdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", entity.getName());
		parameters.put("password", entity.getPassword());
		Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		entity.setId(key.longValue());
		return true;
	}

	@Override
	public void update(User entity) {
		if (entity.getId() == null || entity.getName() == null || entity.getPassword() == null) {
			return;
		}
		template.update("UPDATE users SET email = ? , password = ? WHERE id = ? ;",
				entity.getName(), entity.getPassword(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			return;
		}
		template.update("DELETE FROM users WHERE id = ? ;", id);
	}

	@Override
	public Optional<User> findByName(String name) {
		User user;
		try {
			user = template.queryForObject("SELECT * FROM users WHERE name = ? ;", new Object[]{name},
					((rs, rowNum) -> new User(rs.getLong(1),
							rs.getString(2),
							rs.getString(3))));
		} catch (DataAccessException e) {
			user = null;
		}
		if (user == null) {
			return Optional.empty();
		}
		return Optional.of(user);
	}
	
}
