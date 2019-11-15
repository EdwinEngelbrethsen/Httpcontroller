package no.kristiania.http;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {

    // private ArrayList<Project> projects = new ArrayList<>();name
    private DataSource dataSource;

    public ProjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Project project) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
          try (PreparedStatement stmt = conn.prepareStatement("insert into PROJECTS (name) values (?)")) {
              stmt.setString(1, project.getName());
              stmt.executeUpdate();
          }
        }

    }

    public List<Project> listAll() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("select * from PROJECTS")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Project> result = new ArrayList<>();
                    while (rs.next()) {
                        Project project = new Project();
                        project.setName(rs.getString("name"));
                        result.add(new Project());
                    }
                    return result;
                    // return results
                }
            }
        }
    }
}
