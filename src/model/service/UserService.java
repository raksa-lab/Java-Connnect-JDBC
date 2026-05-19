package model.service;

import model.dto.CreateUserDto;
import model.dto.UpdateRequestDto;
import model.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser();

    UserResponseDto createUser(CreateUserDto createUserDto);
    List<UserResponseDto> getALLUsers();
    UserResponseDto getUserByUuid(String uuid);
    UserResponseDto updateUserByUuid (String uuid
            , UpdateRequestDto updateRequestDto);

    int DeleteUserByUuid(String uuid);
    List<UserResponseDto> searchUserByName (String name);
}
