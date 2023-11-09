package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.grade.EventSourceGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventSourceGradeRepository extends JpaRepository<EventSourceGrade, Long> {

    public List<EventSourceGrade> findAllByGradeId(Long gradeId);
}
