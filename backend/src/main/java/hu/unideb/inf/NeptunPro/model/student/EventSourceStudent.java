package hu.unideb.inf.NeptunPro.model.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventSourceStudent {

    @Id
    @GeneratedValue
    private Long id;

    private Long studentId;

    private String source;

    private String payload;

    @CreationTimestamp
    private Timestamp createdAt;

    private Long createdBy;

}
