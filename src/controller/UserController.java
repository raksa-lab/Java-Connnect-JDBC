package controller;

import model.dto.CreateUserDto;
import model.dto.UpdateRequestDto;
import model.dto.UserResponseDto;
import model.service.UserService;
import model.service.UserServiceImpl;
import utils.APIResponseTemplate;

import java.time.LocalDate;
import java.util.List;

public class UserController {
    private final UserService userService = new UserServiceImpl();
    public APIResponseTemplate<UserResponseDto> createUser(CreateUserDto createUserDto){
        return new APIResponseTemplate<>(
                200,
                "Successfully",
                LocalDate.now(),
                userService.createUser(createUserDto)
        );
    }
    public APIResponseTemplate<List<UserResponseDto>> getAllUser(){
        return new APIResponseTemplate<>(
                200,
                "Successfully",
                LocalDate.now(),
                userService.getALLUsers()
        );
    }
    public APIResponseTemplate<UserResponseDto> getUserByUuid(String uuid){
        return new APIResponseTemplate<>(
                200,
                "Successfully",
                LocalDate.now(),
                userService.getUserByUuid(uuid)
        );
    }
    public APIResponseTemplate<List<UserResponseDto>> searchUserByName(String name){
        return new APIResponseTemplate<>(
                200,
                "Successfully",
                LocalDate.now(),
                userService.searchUserByName(name)
        );
    }
    public APIResponseTemplate<Integer> deleteUserByUuid(String uuid){
        return new APIResponseTemplate<>(
                200,
                "Successfully",
                LocalDate.now(),
                userService.DeleteUserByUuid(uuid)
        );
    }
    public APIResponseTemplate<UserResponseDto> updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto){
        return new APIResponseTemplate<>(
                200,
                "Successfully",
                LocalDate.now(),
                userService.updateUserByUuid(uuid, updateRequestDto)
        );

    }
}
