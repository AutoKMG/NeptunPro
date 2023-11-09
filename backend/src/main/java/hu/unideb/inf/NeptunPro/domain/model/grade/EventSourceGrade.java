package hu.unideb.inf.NeptunPro.domain.model.grade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventSourceGrade {

    @Id
    @GeneratedValue
    private Long id;

    private Long gradeId;

    private String source;

    private String payload;

    @CreationTimestamp
    private Timestamp createdAt;

    private Long createdBy;

    private String creator;

}
