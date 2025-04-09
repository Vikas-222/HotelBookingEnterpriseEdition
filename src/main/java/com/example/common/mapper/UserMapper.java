package com.example.common.mapper;

import com.example.dto.UserDTO;
import com.example.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User convertUserDTOToUserForSignup(UserDTO userDTO) {
        return new User.UserBuilder().setFirstName(userDTO.getFirstName()).setLastName(userDTO.getLastName())
                .setEmail(userDTO.getEmail()).setPassword(userDTO.getPassword()).setContactNumber(userDTO.getContactNumber()).build();
    }

    public static User convertUserDTOToUserForLogin(UserDTO userDTO) {
        return new User.UserBuilder().setEmail(userDTO.getEmail()).setPassword(userDTO.getPassword()).build();
    }

    public static UserDTO ToUserDTO(User user) {
        return new UserDTO.Builder()
                .setUserId(user.getUserId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setContactNumber(user.getContactNumber())
                .setGender(user.getGender())
                .setProfilePic(user.getProfilePic())
                .setRole(user.getRole())
                .setActive(user.getIsActive()).build();
    }

    public static User ForUpdateDTOToEntity(UserDTO user) {
        return new User.UserBuilder()
                .setUserId(user.getUserId())
                .setLastName(user.getLastName())
                .setContactNumber(user.getContactNumber())
                .setGender(user.getGender())
                .setProfilePic(user.getProfilePic()).build();
    }

    public static List<UserDTO> convertUserToUserDTOList(List<User> list) {
        return list.stream()
                .map(user -> new UserDTO.Builder()
                        .setUserId(user.getUserId())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setEmail(user.getEmail())
                        .setContactNumber(user.getContactNumber())
                        .setGender(user.getGender())
                        .setRole(user.getRole())
                        .setActive(user.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }
}
