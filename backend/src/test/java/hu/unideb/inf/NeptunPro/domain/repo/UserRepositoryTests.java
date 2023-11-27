package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model.user.Role;
import hu.unideb.inf.NeptunPro.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindByEmail_ReturnUser() {
        var user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@email.com")
                .password("ABC12345678")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var savedUser = userRepository.findByEmail(user.getEmail());
        assertThat(savedUser).isPresent();
    }

    @Test
    public void UserRepository_FindTop10ByFullNameContains_ReturnUser() {
        var user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@gmail.com")
                .password("ABC12345678")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var foundUser = userRepository.findTop10ByFullNameContains("John Doe");
        assertThat(foundUser).isNotNull();
    }
}
