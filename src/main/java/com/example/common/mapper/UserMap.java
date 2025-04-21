package com.example.common.mapper;

import com.example.dto.UserDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMap {

    public static User convertUserDTOToUserForSignup(UsersDTO userDTO) {
        return new User.Builder().setFirstName(userDTO.getFirstName()).setLastName(userDTO.getLastName())
                .setEmail(userDTO.getEmail()).setPassword(userDTO.getPassword()).setContactNumber(userDTO.getContactNumber()).build();
    }

    public static UsersDTO toUserDTO(User user) {
        return new UsersDTO.Builder()
                .setUserId(user.getUserId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setContactNumber(user.getContactNumber())
                .setGender(user.getGender())
                .setRole(user.getRole())
                .setActive(user.getIsActive()).build();
    }

    public static List<UsersDTO> convertUserToUserDTOList(List<User> list) {
        return list.stream()
                .map(user -> new UsersDTO.Builder()
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


//    public static UsersDTO toUserDTO(User user) {
//        return new UsersDTO.Builder()
//                .setUserId(user.getUserId())
//                .setFirstName(user.getFirstName())
//                .setLastName(user.getLastName())
//                .setEmail(user.getEmail())
//                .setContactNumber(user.getContactNumber())
//                .setGender(user.getGender())
//                .setProfilePic(user.getProfilePic())
//                .setRole(user.getRole())
//                .setActive(user.getIsActive()).build();
//    }

}
