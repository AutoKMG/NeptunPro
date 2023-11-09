package hu.unideb.inf.NeptunPro.domain.model.grade;


import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide a student")
    private Long studentId;

    @NotBlank(message = "Please provide a course")
    private Long courseId;

    @NotBlank(message = "Please provide a grade")
    private Double grade;

    private Short version;

    private Long createdBy;
    private Long modifiedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp modifiedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId", referencedColumnName = "id", insertable = false, updatable = false)
    private Student studentInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId", referencedColumnName = "id", insertable = false, updatable = false)
    private Course courseInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User createdByInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modifiedBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User modifiedByInfo;
}
