package mapper;

import model.User;
import model.dto.CreateUserDto;
import model.dto.UserResponseDto;

import java.util.Random;
import java.util.UUID;

public class UserMapper {
    public User fromCreateUserDatoToUser(CreateUserDto  createUserDto) {
        return new User(new Random().nextInt(99999999),
                UUID.randomUUID().toString(),
                createUserDto.name() , createUserDto.email(),
                createUserDto.password(),
                "https://www.magnific.com/free-vector/user-circles-set_145856997.htm#fromView=keyword&page=1&position=1&uuid=1e8a8dd5-8e1d-4621-821d-c9bbc98e45cf&query=Default+profile"
        );
    }
    public UserResponseDto fromUserToUserResponseDto (User user){
        return new UserResponseDto(user.getUuid(),
                user.getName(),
                user.getEmail(),
                user.getProfile());
    }
}
