package hu.unideb.inf.NeptunPro.domain.model.student;

import hu.unideb.inf.NeptunPro.domain.model.Program;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;

    private String lastname;

    private Long programId;

    private Double gpa;

    private Short version;

    private Long createdBy;
    private Long modifiedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp modifiedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "programId", referencedColumnName = "id", insertable = false, updatable = false)
    private Program programInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User createdByInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modifiedBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User modifiedByInfo;

}
