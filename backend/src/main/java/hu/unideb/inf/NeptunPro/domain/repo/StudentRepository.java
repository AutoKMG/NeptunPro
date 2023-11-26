package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(
            value = """
            SELECT * FROM student
            WHERE CONCAT(firstname, ' ', lastname) LIKE :name
            """,
            nativeQuery = true
    )
    public List<Student> findTop10ByFullNameContains(final String name);
}
