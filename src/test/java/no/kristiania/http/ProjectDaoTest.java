package no.kristiania.http;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


public class ProjectDaoTest {

    private Random random = new Random();
    private JdbcDataSource dataSource;
    private ProjectDao dao;

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

    @BeforeEach
    void setUp() {
        dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:myTestDatabase;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        dao = new ProjectDao(dataSource);
    }

    @Test
    void ShouldListSavedProjects() throws SQLException {
        Project project = sampleProject();
        dao.insert(project);
        assertThat(dao.listAll())
                .extracting(Project::getName)
                .contains(project.getName());
    }

    @Test
    public void shouldRetrieveSavedProduct() throws SQLException {
        Project project = sampleProject();
        dao.insert(project);
        assertThat(project).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(project.getId()));
                //.isEqualToComparingFieldByField(project);
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
