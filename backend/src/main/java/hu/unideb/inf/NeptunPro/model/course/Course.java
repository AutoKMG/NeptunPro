package hu.unideb.inf.NeptunPro.model.course;

import hu.unideb.inf.NeptunPro.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Long teacherId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherId", referencedColumnName = "id", insertable = false, updatable = false)
    private User teacherInfo;

}
