package hu.unideb.inf.NeptunPro.repository;

import hu.unideb.inf.NeptunPro.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
