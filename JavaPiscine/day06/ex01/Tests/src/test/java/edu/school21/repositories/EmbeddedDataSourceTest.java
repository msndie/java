package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {

    DataSource db;

    @BeforeEach
    public void init() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        db = dbBuilder.addScript("/schema.sql").addScript("/data.sql").build();
    }

    @Test
    public void test() throws SQLException {
        Assertions.assertNotNull(db.getConnection());
    }
}
