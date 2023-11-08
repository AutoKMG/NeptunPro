package hu.unideb.inf.NeptunPro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.repo.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CourseService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final ObjectMapper mapper;

    private final CourseRepository courseRepository;

    @Transactional
    public Course save(final Course course) {
        var persisted = courseRepository.saveAndFlush(course);
        entityManager.refresh(persisted);
        return persisted;
    }
}
