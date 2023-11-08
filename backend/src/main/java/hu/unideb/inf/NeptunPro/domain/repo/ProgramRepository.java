package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    public boolean existsByName(String name);
}
