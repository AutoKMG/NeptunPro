package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.student.EventSourceStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventSourceStudentRepository extends JpaRepository<EventSourceStudent, Long> {
    List<EventSourceStudent> findAllByStudentId(Long id);
}
