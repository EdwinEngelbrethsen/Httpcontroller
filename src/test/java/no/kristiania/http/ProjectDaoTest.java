package no.kristiania.http;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;


public class ProjectDaoTest {

    private Random random = new Random();

    /*@BeforeEach
    void setUp() throws SQLException {
        dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:myTestDatabase");
        dataSource.getConnection().createStatement().executeUpdate(
                "create table if not exists PROJECTS (" +
                        "id serial primary key, name varchar(100) not null" + ")"
        );
        dao = new ProjectDao(dataSource);
    } */

    @Test
    void ShouldListSavedProjects() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:myTestDatabase;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();

        ProjectDao dao = new ProjectDao(dataSource);
        Project project = sampleProject();

        dao.insert(project);
        Assertions.assertThat(dao.listAll())
                .extracting(Project::getName)
                .contains(project.getName());
    }

    private Project sampleProject() {
        Project project = new Project();
        project.setName(pickOne(new String[] {"Building Project", "Light Project", "Website Project", "App Project", "Game Project"}));
        return project;
    }

    private String pickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
