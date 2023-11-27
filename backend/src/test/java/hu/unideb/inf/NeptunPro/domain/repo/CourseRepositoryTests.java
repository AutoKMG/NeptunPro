package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.model.course.CourseType;
import hu.unideb.inf.NeptunPro.domain.model.user.Role;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    private static User testUser;

    @BeforeAll
    public static void createTestUser(@Autowired UserRepository userRepository) {
        var user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@email.com")
                .password("ABC12345678")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        testUser = user;
    }

    @Test
    public void CourseRepository_Save_ReturnSavedCourse() {
        Course course = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(testUser.getId())
                .build();

        Course savedCourse = courseRepository.save(course);

        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void CourseRepository_GetAll_ReturnMoreThanOneCourse() {
        Course course1 = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(testUser.getId())
                .build();

        Course course2 = Course.builder()
                .name("Software Methodology")
                .type(CourseType.LECTURE)
                .teacherId(1L)
                .build();

        courseRepository.save(course1);
        courseRepository.save(course2);

        List<Course> courses = courseRepository.findAll();

        assertThat(courses).isNotNull();
        assertThat(courses.size()).isGreaterThan(1);
    }

    @Test
    public void CourseRepository_FindById_ReturnCourse() {
        Course course = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(testUser.getId())
                .build();

        courseRepository.save(course);

        Course savedCourse = courseRepository.findById(course.getId()).get();

        assertThat(savedCourse).isNotNull();
    }

    @Test
    public void CourseRepository_FindByNameAndType_ReturnCourse() {
        Course course = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(testUser.getId())
                .build();

        courseRepository.save(course);

        Course savedCourse = courseRepository.findByNameAndType(course.getName(), course.getType());

        assertThat(savedCourse).isNotNull();
    }

    @Test
    public void CourseRepository_FindTop10ByNameContains_ReturnCourse() {
        Course course = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(testUser.getId())
                .build();

        courseRepository.save(course);

        List<Course> savedCourse = courseRepository.findTop10ByNameContains(course.getName());

        assertThat(savedCourse).isNotNull();
    }

    @Test
    public void CourseRepository_Delete_ReturnNull() {
        Course course = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(testUser.getId())
                .build();

        courseRepository.save(course);

        courseRepository.delete(course);

        Course savedCourse = courseRepository.findById(course.getId()).orElse(null);

        assertThat(savedCourse).isNull();
    }
}
