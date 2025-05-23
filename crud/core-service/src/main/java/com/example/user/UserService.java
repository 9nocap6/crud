package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userStorage;

    @Autowired
    public UserService(UserRepository userStorage) {
        this.userStorage = userStorage;
    }

    public List<UserDto> getUsers() {
        return userStorage.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }


    public UserDto getUserById(Long id) {
        return UserMapper.toUserDto(userStorage.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID=" + id + " не найден!")));
    }

    public UserDto create(UserDto userDto) {
        try {
            return UserMapper.toUserDto(userStorage.save(UserMapper.toUser(userDto)));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Пользователь с E-mail=" +
                    userDto.getEmail() + " уже существует!");
        }

    }

    public UserDto update(UserDto userDto, Long id) {
        if (userDto.getId() == null) {
            userDto.setId(id);
        }
        User user = userStorage.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID=" + id + " не найден!"));
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(userStorage.save(user));
    }


    public void delete(Long userId) {
        try {
            userStorage.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Пользователь с ID=" + userId + " не найден!");
        }
    }


    public User findUserById(Long id) {
        return userStorage.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID=" + id + " не найден!"));
    }
}
