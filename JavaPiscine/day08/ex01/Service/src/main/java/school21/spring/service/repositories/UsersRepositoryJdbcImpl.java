package school21.spring.service.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.zaxxer.hikari.HikariDataSource;

import school21.spring.service.models.User;

public class UsersRepositoryJdbcImpl implements UsersRepository {

	private final HikariDataSource dataSource;

	public UsersRepositoryJdbcImpl(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	@Override
	public User findById(Long id) {
		if (id == null) {
			return null;
		}
		String script = "SELECT * FROM users WHERE id=" + id + ";";
		try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(script)) {
				User user = null;
				if (rs.next()) {
					user = new User(rs.getLong("id"),
									rs.getString("email"));
				}
				return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		String script = "SELECT * FROM users";
		try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(script)) {
				User user;
				while (rs.next()) {
					user = new User(rs.getLong("id"),
									rs.getString("email"));
					list.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		return list;
	}

	@Override
	public void save(User entity) {
		String script = "INSERT INTO users(email) "
						+ "VALUES(?)";
		try (Connection connection = dataSource.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(script,
			Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, entity.getEmail());
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					try (ResultSet rSet = pstmt.getGeneratedKeys()) {
						if (rSet.next()) {
							entity.setId(rSet.getLong(1));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void update(User entity) {
		if (entity.getId() == null || entity.getEmail() == null) {
			return;
		}
		String script = "UPDATE users "
                        + "SET email = ? WHERE id = ? ;";
        try (Connection connection = dataSource.getConnection();
        	PreparedStatement pstmt = connection.prepareStatement(script)) {
				pstmt.setString(1, entity.getEmail());
				pstmt.setLong(2, entity.getId());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			return;
		}
		String script = "DELETE * FROM users WHERE id=" + id + ";";
		try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement()) {
				statement.executeUpdate(script);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		User user = null;
		if (email == null) {
			return Optional.of(user);
		}
		String script = "SELECT * FROM users WHERE email='" + email + "' ;";
		try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(script)) {
				if (rs.next()) {
					user = new User(rs.getLong("id"),
									rs.getString("email"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return Optional.of(user);
	}
	
}
