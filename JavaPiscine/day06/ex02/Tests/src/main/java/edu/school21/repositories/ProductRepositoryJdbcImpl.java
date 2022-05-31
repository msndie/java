package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements ProductRepository {

    private DataSource dataSource;

    public ProductRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String script = "SELECT * FROM product;";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(script)) {
            while (rs.next()) {
                list.add(new Product(rs.getLong("id"),
                                    rs.getString("name"),
                                    rs.getLong("price")));
            }
            return list;
        }
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        String script = "SELECT * FROM product WHERE id=" + id + ";";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(script)) {
            Product product = null;
            if (rs.next()) {
                product = new Product(rs.getLong("id"),
                                        rs.getString("name"),
                                        rs.getLong("price"));
            }
            return Optional.of(product);
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        String script = "UPDATE product "
                + "SET name = ? , "
                + "price = ? "
                + "WHERE id = ? ;";
        int affectedRows = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(script)) {
            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getPrice());
            pstmt.setLong(3, product.getId());
            affectedRows = pstmt.executeUpdate();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        String script = "INSERT INTO product(name, price) "
                + "VALUES(?,?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(script,
                    Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, product.getName());;
                pstmt.setLong(2, product.getPrice());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rSet = pstmt.getGeneratedKeys()) {
                        if (rSet.next()) {
                            product.setId(rSet.getLong(1));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String script = "DELETE FROM product WHERE id=" + id + ";";
        try (Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(script);
        }
    }
}
