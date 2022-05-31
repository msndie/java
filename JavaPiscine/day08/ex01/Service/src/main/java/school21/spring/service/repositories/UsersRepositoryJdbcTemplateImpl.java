package school21.spring.service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import school21.spring.service.models.User;

import org.springframework.jdbc.core.JdbcTemplate;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

	private final JdbcTemplate template;

	public UsersRepositoryJdbcTemplateImpl(DriverManagerDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public User findById(Long id) {
		if (id == null) {
			return null;
		}
		return template.queryForObject("SELECT * FROM users WHERE id = ? ;", new Object[]{id} ,
				((rs, rowNum) -> new User(rs.getLong(1), rs.getString(2))));
	}

	@Override
	public List<User> findAll() {
		return template.query("SELECT * FROM users ;",
				((rs, rowNum) -> new User(rs.getLong(1), rs.getString(2))));
	}

	@Override
	public void save(User entity) {
		if (entity.getId() == null || entity.getEmail() == null) {
			return;
		}
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(template);
		entity.setId(simpleJdbcInsert.withTableName("user")
				.usingGeneratedKeyColumns("id")
				.executeAndReturnKey(new BeanPropertySqlParameterSource(entity))
				.longValue());
	}

	@Override
	public void update(User entity) {
		if (entity.getId() == null || entity.getEmail() == null) {
			return;
		}
		template.update("UPDATE users SET email = ? WHERE id = ? ;",
				entity.getEmail(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			return;
		}
		template.update("DELETE FROM users WHERE id = ? ;", id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		User user;
		user = template.queryForObject("SELECT * FROM users WHERE email = ? ;", new Object[]{email},
				((rs, rowNum) -> new User(rs.getLong(1), rs.getString(2))));
		return Optional.of(user);
	}
	
}
