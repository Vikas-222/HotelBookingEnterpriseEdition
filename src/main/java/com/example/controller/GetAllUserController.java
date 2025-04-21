package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.enums.Role;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.utils.SessionChecker;
import com.example.common.utils.SessionValidator;
import com.example.dto.UserDTO;
import com.example.dto.UsersDTO;
import com.example.service.UserService;
import com.example.service.UserServices;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllUserController", value = "/get-all-user")
public class GetAllUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        UserServices userService = new UserServices();
        try {
            UsersDTO user = SessionChecker.checkSession(request);
            if (!user.getRole().equals(Role.ADMIN)) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            List<UsersDTO> userList = userService.getAllUser();
            sendResponse(response, null, null, userList, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.UNAUTHORIZED_ACCESS, null, null, 500);
        }
    }


    private void sendResponse(HttpServletResponse response, String message, String technicalMessage, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response();
        apiResponse.setMessage(message);
        apiResponse.setTechnicalMessage(technicalMessage);
        apiResponse.setData(data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
