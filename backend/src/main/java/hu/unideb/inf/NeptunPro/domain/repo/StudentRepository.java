package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
