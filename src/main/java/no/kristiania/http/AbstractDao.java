package no.kristiania.http;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<ENTITY> {
    // private ArrayList<Project> projects = new ArrayList<>();
    protected DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long insert(ENTITY o, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
          try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
              mapToStatement(o, statement);
              statement.executeUpdate();

              ResultSet generatedKeys = statement.getGeneratedKeys();
              generatedKeys.next();
              return generatedKeys.getLong("id");
          }
        }
        // projects.add(o);
    }

    protected abstract void mapToStatement(ENTITY o, PreparedStatement statement) throws SQLException;

    public List<ENTITY> listAll(String sql) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    List<ENTITY> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(mapFromResultSet(rs));
                    }
                    return result;
                }
            }
        }
        // return projects;
    }

    protected abstract ENTITY mapFromResultSet(ResultSet rs) throws SQLException;

    public ENTITY retrieve(long id, String sql) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1,id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapFromResultSet(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
