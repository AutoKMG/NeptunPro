package hu.unideb.inf.NeptunPro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.grade.Grade;
import hu.unideb.inf.NeptunPro.domain.model.student.EventSourceStudent;
import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import hu.unideb.inf.NeptunPro.domain.repo.EventSourceStudentRepository;
import hu.unideb.inf.NeptunPro.domain.repo.StudentRepository;
import hu.unideb.inf.NeptunPro.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

@RequiredArgsConstructor
@Service
public class StudentService {

    @PersistenceContext
    private final EntityManager em;

    private final ObjectMapper mapper;

    private final StudentRepository studentRepository;
    private final EventSourceStudentRepository eventSourceStudentRepository;

    public void saveEventSource(
            final String source,
            final Student persisted,
            final User user
    ) {
        try {
            var json = mapper.writeValueAsString(persisted);

            var eventSource = EventSourceStudent.builder()
                    .studentId(persisted.getId())
                    .source(source)
                    .payload(json)
                    .createdBy(user.getId())
                    .creator(Utils.getCreator(user))
                    .build();

            eventSourceStudentRepository.save(eventSource);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    @Transactional
    public Student save(
            final String httpVerb,
            final Student student,
            final User user
    ) {
        try {
            switch (httpVerb) {
                case Utils.HTTP_CREATE -> {
                    student.setVersion(Utils.INITIAL_VERSION);
                    student.setCreatedBy(user.getId());
                    student.setModifiedBy(user.getId());
                }
                case Utils.HTTP_UPDATE -> {
                    student.setVersion((short) (student.getVersion() + 1));
                    student.setModifiedBy(user.getId());
                }
            }

            var persistedStudent = studentRepository.saveAndFlush(student);

            em.refresh(persistedStudent);

            saveEventSource(httpVerb, persistedStudent, user);

            return persistedStudent;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void delete(final Long id) {
        var events = eventSourceStudentRepository.findAllByStudentId(id);
        eventSourceStudentRepository.deleteAll(events);

        studentRepository.deleteById(id);
    }

}
