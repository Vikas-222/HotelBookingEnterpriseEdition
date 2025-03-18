package com.example.common.mapper;

import com.example.dto.LoginRequestUserDTO;
import com.example.dto.SignupRequestUserDTO;
import com.example.dto.UserDTO;
import com.example.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User userDTOToEntity(SignupRequestUserDTO signupUser){
        return new User.UserBuilder(signupUser.getFirstName(), signupUser.getLastName(), signupUser.getEmail(),
                signupUser.getPassword(), signupUser.getContactNumber()).build();
    }

    public static User loginUserDTOtoEntity(LoginRequestUserDTO loginUser){
        return new User.UserBuilder(loginUser.getEmail(),loginUser.getPassword()).build();
    }

    public static UserDTO ToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setContactNumber(user.getContactNumber());
        userDTO.setGender(user.getGender());
        userDTO.setProfilePic(user.getProfilePic());
        return userDTO;
    }

    public static List<UserDTO> entityToDTOList(List<User> list){
        List<UserDTO> userList = list.stream().map(UserDTO::new).collect(Collectors.toList());
        return userList;
    }
}
