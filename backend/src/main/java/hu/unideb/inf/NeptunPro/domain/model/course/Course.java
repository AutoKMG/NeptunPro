package hu.unideb.inf.NeptunPro.domain.model.course;

import hu.unideb.inf.NeptunPro.domain.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide a name")
    private String name;

    @NotNull(message = "Please provide a type")
    @Enumerated(EnumType.STRING)
    private CourseType type;

    @NotNull(message = "Please provide a teacher")
    private Long teacherId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacherId", referencedColumnName = "id", insertable = false, updatable = false)
    private User teacherInfo;

}
