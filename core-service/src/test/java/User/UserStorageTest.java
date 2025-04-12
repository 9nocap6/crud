package User;

import com.example.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



@DataJpaTest
class UserStorageTest {
    @Autowired
    UserRepository storage;

    @Test
    void findAllWithEmptyRepository_shouldReturnEmpty() {
        List<User> users = storage.findAll();

        Assertions.assertEquals(0, users.size());
    }
}