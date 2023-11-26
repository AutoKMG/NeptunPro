package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.model.course.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    public Course findByNameAndType(String name, CourseType type);

    public List<Course> findTop10ByNameContains(final String name);

}
