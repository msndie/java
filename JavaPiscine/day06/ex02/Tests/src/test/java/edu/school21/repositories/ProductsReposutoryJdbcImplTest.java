package edu.school21.repositories;

import edu.school21.models.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsReposutoryJdbcImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS;
    final Product EXPECTED_FIND_BY_ID_PRODUCT;
    final Product EXPECTED_UPDATED_PRODUCT;
    final Product EXPECTED_SAVED_PRODUCT;
    final Product EXPECTED_DELETE_PRODUCT;
    private ProductRepositoryJdbcImpl productRepository;
    private DataSource db;

    public ProductsReposutoryJdbcImplTest() {
        EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>();
        Product one = new Product(1L, "pivo", 50L);
        Product two = new Product(2L, "chips", 70L);
        Product three = new Product(3L, "suhari", 80L);
        Product four = new Product(4L, "riba", 90L);
        Product five = new Product(5L, "sir kosichka", 100L);
        EXPECTED_FIND_ALL_PRODUCTS.add(one);
        EXPECTED_FIND_ALL_PRODUCTS.add(two);
        EXPECTED_FIND_ALL_PRODUCTS.add(three);
        EXPECTED_FIND_ALL_PRODUCTS.add(four);
        EXPECTED_FIND_ALL_PRODUCTS.add(five);
        EXPECTED_FIND_BY_ID_PRODUCT = one;
        EXPECTED_UPDATED_PRODUCT = new Product(2L, "chipsi", 100L);
        EXPECTED_DELETE_PRODUCT = EXPECTED_UPDATED_PRODUCT;
        EXPECTED_SAVED_PRODUCT = new Product(6L, "vodka", 444L);
    }

    @BeforeEach
    public void init() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        db = dbBuilder.addScript("/schema.sql").addScript("/data.sql").build();
        productRepository = new ProductRepositoryJdbcImpl(db);
    }

    @Test
    public void testFindById() throws SQLException {
        Assertions.assertEquals(productRepository.findById(1L).get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    public void testFindAll() throws SQLException {
        Assertions.assertIterableEquals(productRepository.findAll(), EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    public void testUpdate() throws SQLException {
        productRepository.update(EXPECTED_UPDATED_PRODUCT);
        Assertions.assertEquals(productRepository.findById(EXPECTED_UPDATED_PRODUCT.getId()).get(), EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    public void testSave() throws SQLException {
        productRepository.save(EXPECTED_SAVED_PRODUCT);
        Assertions.assertTrue(productRepository.findAll().contains(EXPECTED_SAVED_PRODUCT));
    }

    @Test
    public void testDelete() throws SQLException {
        productRepository.delete(EXPECTED_UPDATED_PRODUCT.getId());
        Assertions.assertFalse(productRepository.findAll().contains(EXPECTED_DELETE_PRODUCT));
    }
}
