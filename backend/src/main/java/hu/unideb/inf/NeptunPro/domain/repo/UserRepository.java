package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(
            value = """
            SELECT * FROM users
            WHERE CONCAT(firstname, ' ', lastname) LIKE :name
            """,
            nativeQuery = true
    )
    public List<User> findTop10ByFullNameContains(final String name);

}
