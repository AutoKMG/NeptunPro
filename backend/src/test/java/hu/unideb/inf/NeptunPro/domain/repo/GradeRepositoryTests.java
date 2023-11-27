package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.Program;
import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.model.course.CourseType;
import hu.unideb.inf.NeptunPro.domain.model.grade.Grade;
import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import hu.unideb.inf.NeptunPro.domain.model.user.Role;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GradeRepositoryTests {

    @Autowired
    private GradeRepository gradeRepository;

    private static Course testCourse;
    private static Student testStudent;

    @BeforeAll
    public static void createTestData(
            @Autowired UserRepository userRepository,
            @Autowired StudentRepository studentRepository,
            @Autowired CourseRepository courseRepository,
            @Autowired ProgramRepository programRepository
    ) {
        var user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@email.com")
                .password("ABC12345678")
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);

        var course = Course.builder()
                .name("Software Engineering")
                .type(CourseType.LAB)
                .teacherId(savedUser.getId())
                .build();
        testCourse = courseRepository.save(course);

        var program = Program.builder()
                .name("Computer Science")
                .build();
        var savedProgram = programRepository.save(program);

        var student = Student.builder()
                .firstname("Hank")
                .lastname("Green")
                .programId(savedProgram.getId())
                .build();
        testStudent = studentRepository.save(student);
    }

    @Test
    public void GradeRepository_Save_ReturnSavedGrade() {
        Grade grade = Grade.builder()
                .studentId(testStudent.getId())
                .courseId(testCourse.getId())
                .grade(5.0)
                .build();

        Grade savedGrade = gradeRepository.save(grade);

        assertThat(savedGrade).isNotNull();
        assertThat(savedGrade.getId()).isGreaterThan(0);
    }

    @Test
    public void GradeRepository_GetAll_ReturnMoreThanOneGrade() {
        Grade grade1 = Grade.builder()
                .studentId(testStudent.getId())
                .courseId(testCourse.getId())
                .grade(5.0)
                .build();

        Grade grade2 = Grade.builder()
                .studentId(testStudent.getId())
                .courseId(testCourse.getId())
                .grade(4.0)
                .build();

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);

        List<Grade> grades = gradeRepository.findAll();

        assertThat(grades).isNotNull();
        assertThat(grades.size()).isGreaterThan(1);
    }

    @Test
    public void GradeRepository_FindById_ReturnGrade() {
        Grade grade = Grade.builder()
                .studentId(testStudent.getId())
                .courseId(testCourse.getId())
                .grade(5.0)
                .build();
        gradeRepository.save(grade);

        var savedGrade = gradeRepository.findById(grade.getId()).get();

        assertThat(savedGrade).isNotNull();
    }

    @Test
    public void GradeRepository_FindByStudentId_ReturnGrade() {
        Grade grade = Grade.builder()
                .studentId(testStudent.getId())
                .courseId(testCourse.getId())
                .grade(5.0)
                .build();
        gradeRepository.save(grade);

        var savedGrade = gradeRepository.findAllByStudentId(testStudent.getId());

        assertThat(savedGrade).isNotNull();
    }

    @Test
    public void GradeRepository_Delete_ReturnNull() {
        Grade grade = Grade.builder()
                .studentId(testStudent.getId())
                .courseId(testCourse.getId())
                .grade(5.0)
                .build();
        gradeRepository.save(grade);

        gradeRepository.delete(grade);

        var savedGrade = gradeRepository.findById(grade.getId()).orElse(null);

        assertThat(savedGrade).isNull();
    }

}
