package com.example.common.utils;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionValidator {

    public static UserDTO checkSession(HttpServletRequest request) throws ApplicationException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            throw new ApplicationException(Messages.Error.LOGIN_FIRST);
        }
        return userDTO;
    }
}
