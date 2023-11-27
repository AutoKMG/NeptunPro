package hu.unideb.inf.NeptunPro.domain.model.student;

import hu.unideb.inf.NeptunPro.domain.model.Program;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide the first name")
    private String firstname;

    @NotBlank(message = "Please provide the last name")
    private String lastname;

    @NotNull(message = "Please provide a program")
    private Long programId;

    @Null
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
