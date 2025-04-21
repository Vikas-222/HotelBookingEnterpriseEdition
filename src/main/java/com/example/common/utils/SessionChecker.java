package com.example.common.utils;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.UserDTO;
import com.example.dto.UsersDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionChecker {

    public static UsersDTO checkSession(HttpServletRequest request) throws ApplicationException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ApplicationException(Messages.Error.LOGIN_FIRST);
        }
        UsersDTO userDTO = (UsersDTO) session.getAttribute("user");
        if (userDTO == null) {
            throw new ApplicationException(Messages.Error.LOGIN_FIRST);
        }
        return userDTO;
    }
}
