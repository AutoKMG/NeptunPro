package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentId(Long studentId);
}
