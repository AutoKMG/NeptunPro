package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.model.course.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    public Course findByNameAndType(String name, CourseType type);

    public List<Course> findTop10ByNameContains(final String name);

    @Query(
            value = """
                    SELECT COUNT(*)
                      FROM course c
                      JOIN grade g
                      ON c.id = g.course_id
                      JOIN student s
                      ON s.id = g.student_id
                      WHERE c.id =:id
                      GROUP BY c.id
                    """,nativeQuery = true
    )
    public Optional<Integer> findCoursesRegisteredStudentsById(final Long id);

}
