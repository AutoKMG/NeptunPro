package hu.unideb.inf.NeptunPro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.grade.EventSourceGrade;
import hu.unideb.inf.NeptunPro.domain.model.grade.Grade;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import hu.unideb.inf.NeptunPro.domain.repo.EventSourceGradeRepository;
import hu.unideb.inf.NeptunPro.domain.repo.GradeRepository;
import hu.unideb.inf.NeptunPro.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;


@RequiredArgsConstructor
@Service
public class GradeService {

    @PersistenceContext
    private final EntityManager em;

    private final ObjectMapper mapper;

    private final GradeRepository gradeRepository;
    private final EventSourceGradeRepository eventSourceGradeRepository;

    public void saveEventSource(
            final String source,
            final Grade persisted,
            final User user
    ) {
        try {
            var json = mapper.writeValueAsString(persisted);

            var eventSource = EventSourceGrade.builder()
                    .gradeId(persisted.getId())
                    .source(source)
                    .payload(json)
                    .createdBy(user.getId())
                    .creator(Utils.getCreator(user))
                    .build();

            eventSourceGradeRepository.save(eventSource);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    @Transactional
    public Grade save(
            final String httpVerb,
            final Grade grade,
            final User user
    ) {
        try {
            switch (httpVerb) {
                case Utils.HTTP_CREATE -> {
                    grade.setVersion(Utils.INITIAL_VERSION);
                    grade.setCreatedBy(user.getId());
                    grade.setModifiedBy(user.getId());
                }
                case Utils.HTTP_UPDATE -> {
                    grade.setVersion((short) (grade.getVersion() + 1));
                    grade.setModifiedBy(user.getId());
                }
            }

            var persistedGrade = gradeRepository.saveAndFlush(grade);

            em.refresh(persistedGrade);

            saveEventSource(httpVerb, persistedGrade, user);

            return persistedGrade;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void delete(final Long id) {
        var events = eventSourceGradeRepository.findAllByGradeId(id);
        eventSourceGradeRepository.deleteAll(events);

        gradeRepository.deleteById(id);
    }
}
