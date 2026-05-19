package model.service;

import mapper.UserMapper;
import model.User;
import model.UserDao;
import model.dto.CreateUserDto;
import model.dto.UpdateRequestDto;
import model.dto.UserResponseDto;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserDao userDao = new UserDao();
    private final UserMapper userMapper = new UserMapper();
    @Override
    public UserResponseDto createUser() {
        return null;
    }

    @Override
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        //map from CreateUserDto to User
        User user = userMapper.fromCreateUserDatoToUser(createUserDto);
        User savedUser = userDao.save(user);
        if (savedUser == null) {
            throw new RuntimeException("Failed to create user");
        }
        return userMapper.fromUserToUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getALLUsers() {

        return userDao.findAll().stream()
                .map(userMapper::fromUserToUserResponseDto).toList();
    }

    @Override
    public UserResponseDto getUserByUuid(String uuid) {
        User user = userDao.findAll()
                .stream().filter(u->u.getUuid().equals(uuid))
                .findFirst().get();
        return userMapper.fromUserToUserResponseDto(user);
//        return null;
    }

    @Override
    public UserResponseDto updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto) {
        User user = userDao.findAll()
                .stream().filter(u->u.getUuid().equals(uuid))
                .findFirst().get();
        user.setName(updateRequestDto.name());
        user.setEmail(updateRequestDto.email());
        user.setPassword(updateRequestDto.password());
        user.setProfile(updateRequestDto.profile());
        User updatedUser = userDao.update(user);
        return userMapper.fromUserToUserResponseDto(updatedUser);
    }


    @Override
    public int DeleteUserByUuid(String uuid) {
        User user = userDao.findAll()
                .stream().filter(u->u.getUuid().equals(uuid))
                .findFirst().get();
        return userDao.remove(user);
    }

    @Override
    public List<UserResponseDto> searchUserByName(String name) {
        return userDao.findByName(name).stream()
                .map(userMapper::fromUserToUserResponseDto)
                .toList();
    }
}
