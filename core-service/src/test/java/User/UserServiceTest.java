package User;

import com.example.user.UserDto;
import com.example.user.UserMapper;
import com.example.user.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTest {
    final UserService userService;
    User user = new User(1L, "User", "first@first.ru");

    @Test
    void shouldReturnUserWhenGetUserById() {
        UserDto returnUserDto = userService.create(UserMapper.toUserDto(user));
        assertThat(returnUserDto.getName(), equalTo(user.getName()));
        assertThat(returnUserDto.getEmail(), equalTo(user.getEmail()));
    }



    @Test
    void shouldExceptionWhenDeleteUserWithWrongId() {
        IllegalArgumentException exp = assertThrows(IllegalArgumentException.class, () -> userService.delete(10L));
        assertEquals("Пользователь с ID=10 не найден!", exp.getMessage());
    }

    @Test
    void shouldDeleteUser() {
        User user = new User(10L, "Ten", "ten@ten.ru");
        UserDto returnUserDto = userService.create(UserMapper.toUserDto(user));
        List<UserDto> listUser = userService.getUsers();
        int size = listUser.size();
        userService.delete(returnUserDto.getId());
        listUser = userService.getUsers();
        assertThat(listUser.size(), equalTo(size - 1));
    }



    @Test
    void shouldExceptionWhenCreateUserWithExistEmail() {
        user = new User(2L, "User2", "second@second.ru");
        userService.create(UserMapper.toUserDto(user));
        User newUser = new User(3L, "User3", "second@second.ru");
        final IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(UserMapper.toUserDto(newUser)));
        Assertions.assertEquals("Пользователь с E-mail=" + newUser.getEmail() + " уже существует!",
                exception.getMessage());
    }

}