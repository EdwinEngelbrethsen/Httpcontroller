package no.kristiania.http;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(Project project) throws SQLException {
        long id = insert(project, "insert into PROJECTS (name) values (?)");
        project.setId(id);
    }

    @Override
    protected void mapToStatement(Project project, PreparedStatement statement) throws SQLException {
        statement.setString(1, project.getName());
    }

    public List<Project> listAll() throws SQLException {
        return listAll("select * from PROJECTS");
    }

    @Override
    protected Project mapFromResultSet(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setName(rs.getString("name"));
        return project;
    }

    public Project retrieve(long id) throws SQLException {
        return retrieve(id, "select * from PROJECTS where id = ?");
    }

}
