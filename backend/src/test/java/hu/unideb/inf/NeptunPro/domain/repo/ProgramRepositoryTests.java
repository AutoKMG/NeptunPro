package hu.unideb.inf.NeptunPro.domain.repo;


import hu.unideb.inf.NeptunPro.domain.model.Program;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProgramRepositoryTests {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    public void ProgramRepository_Save_ReturnSavedProgram() {
        Program program = Program.builder()
                .name("Computer Science")
                .build();

        Program savedProgram = programRepository.save(program);

        assertThat(savedProgram).isNotNull();
        assertThat(savedProgram.getId()).isGreaterThan(0);
    }

    @Test
    public void ProgramRepository_GetAll_ReturnMoreThanOneProgram() {
        Program program1 = Program.builder()
                .name("Computer Science")
                .build();
        Program program2 = Program.builder()
                .name("Software Engineering")
                .build();

        programRepository.save(program1);
        programRepository.save(program2);

        List<Program> programs = programRepository.findAll();

        assertThat(programs.size()).isGreaterThan(1);
    }

    @Test
    public void ProgramRepository_FindTop10ByNameContains_ReturnPrograms() {
        Program program1 = Program.builder()
                .name("Computer Science")
                .build();
        Program program2 = Program.builder()
                .name("Software Engineering")
                .build();

        programRepository.save(program1);
        programRepository.save(program2);

        List<Program> programs = programRepository.findTop10ByNameContains("Science");

        assertThat(programs.size()).isGreaterThan(0);
    }

    @Test
    public void ProgramRepository_ExistsByName_ReturnTrue() {
        Program program = Program.builder()
                .name("Computer Science")
                .build();

        programRepository.save(program);

        boolean exists = programRepository.existsByName("Computer Science");

        assertThat(exists).isTrue();
    }

    @Test
    public void ProgramRepository_ExistsByName_ReturnFalse() {
        Program program = Program.builder()
                .name("Computer Science")
                .build();

        programRepository.save(program);

        boolean exists = programRepository.existsByName("Software Engineering");

        assertThat(exists).isFalse();
    }

    @Test
    public void ProgramRepository_FindById_ReturnProgram() {
        Program program = Program.builder()
                .name("Computer Science")
                .build();

        Program savedProgram = programRepository.save(program);

        Program foundProgram = programRepository.findById(savedProgram.getId()).get();

        assertThat(foundProgram).isNotNull();
    }

    @Test
    public void ProgramRepository_Delete_ReturnNull() {
        Program program = Program.builder()
                .name("Computer Science")
                .build();

        Program savedProgram = programRepository.save(program);

        programRepository.delete(savedProgram);

        Program foundProgram = programRepository.findById(savedProgram.getId()).orElse(null);

        assertThat(foundProgram).isNull();
    }

    @Test
    public void ProgramRepository_Update_ReturnUpdatedProgram() {
        Program program = Program.builder()
                .name("Computer Science")
                .build();

        Program savedProgram = programRepository.save(program);

        savedProgram.setName("Software Engineering");

        Program updatedProgram = programRepository.saveAndFlush(savedProgram);

        assertThat(updatedProgram.getName()).isEqualTo("Software Engineering");
    }
}
