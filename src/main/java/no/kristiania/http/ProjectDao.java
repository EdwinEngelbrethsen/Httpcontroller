package no.kristiania.http;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {

    private ArrayList<Project> projects = new ArrayList<>();
    private DataSource dataSource;

    public ProjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Project project) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
          try (PreparedStatement statement = connection.prepareStatement("insert into PROJECTS (name) values (?)")) {
              statement.setString(1, project.getName());
              statement.executeUpdate();
          }
        }
        projects.add(project);
    }

    public List<Project> listAll() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("select * from PROJECTS")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Project> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(new Project());
                    }
                }
            }
        }
        return projects;
    }
}
