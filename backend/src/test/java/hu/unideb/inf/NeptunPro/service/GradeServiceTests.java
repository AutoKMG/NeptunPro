import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.grade.EventSourceGrade;
import hu.unideb.inf.NeptunPro.domain.model.grade.Grade;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import hu.unideb.inf.NeptunPro.domain.repo.EventSourceGradeRepository;
import hu.unideb.inf.NeptunPro.domain.repo.GradeRepository;
import hu.unideb.inf.NeptunPro.service.GradeService;
import hu.unideb.inf.NeptunPro.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GradeServiceTests {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private EventSourceGradeRepository eventSourceGradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEventSource_Success() throws Exception {
        Grade persistedGrade = new Grade();
        persistedGrade.setId(1L);
        User user = new User();
        when(mapper.writeValueAsString(any())).thenReturn("{}");

        gradeService.saveEventSource("HTTP_CREATE", persistedGrade, user);

        verify(eventSourceGradeRepository).save(any(EventSourceGrade.class));
    }

    @Test
    void delete_Success() {
        Long id = 1L;
        EventSourceGrade eventSource = new EventSourceGrade();
        Grade grade = new Grade();
        when(eventSourceGradeRepository.findAllByGradeId(id)).thenReturn(List.of(eventSource));

        gradeService.delete(id);

        verify(eventSourceGradeRepository).deleteAll(List.of(eventSource));
        verify(gradeRepository).deleteById(id);
    }
}
