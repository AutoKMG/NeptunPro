package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.Program;
import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    private static Program testProgram;

    @BeforeAll
    public static void createTestProgram(@Autowired ProgramRepository programRepository) {
        var program = Program.builder()
                .name("Computer Science")
                .build();
        testProgram = programRepository.save(program);
    }

    @Test
    public void StudentRepository_Save_ReturnSavedStudent() {
        var student = Student.builder()
                .firstname("Hank")
                .lastname("Hill")
                .programId(testProgram.getId())
                .build();
        var savedStudent = studentRepository.save(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void StudentRepository_FindById_ReturnStudent() {
        var student = Student.builder()
                .firstname("Hank")
                .lastname("Hill")
                .programId(testProgram.getId())
                .build();
        var savedStudent = studentRepository.save(student);

        var foundStudent = studentRepository.findById(savedStudent.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get()).isEqualTo(savedStudent);
    }

    @Test
    public void StudentRepository_FindTop10ByFullNameContains_ReturnStudent() {
        var student = Student.builder()
                .firstname("Hank")
                .lastname("Hill")
                .programId(testProgram.getId())
                .build();

        studentRepository.save(student);

        var foundStudent = studentRepository.findTop10ByFullNameContains("Ha");

        assertThat(foundStudent).isNotNull();
    }
}
