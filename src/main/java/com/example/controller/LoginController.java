package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dto.UserLoginResponseDTO;
import com.example.dto.UsersDTO;
import com.example.controller.validation.UserValidator;
import com.example.entityservice.UserServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        UserServices userService = new UserServices();
        try {
            UsersDTO user = CustomObjectMapper.toObject(request.getReader(), UsersDTO.class);
            if (!UserValidator.isNullCheckLoginValues(user.getEmail(), user.getPassword())) {
                throw new ApplicationException(Messages.Error.INVALID_CREDENTIALS);
            }
            UsersDTO userDTO = userService.userLogin(user);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5 * 60);
            session.setAttribute("user", userDTO);
            /**
             *When I use UsersDTO object that time isActive = true is also present in response.
             *So I had to use another DTO for Login response which does not have isActive property.
             */
//            UsersDTO responseUser = new UsersDTO.Builder().setUserId(userDTO.getUserId()).setEmail(userDTO.getEmail()).setRole(userDTO.getRole()).build();
            UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();
                    loginResponse.setUserId(userDTO.getUserId());
                    loginResponse.setEmail(userDTO.getEmail());
                    loginResponse.setRole(userDTO.getRole());
            sendResponse(response, Messages.LOGIN_SUCCESSFUL, null, loginResponse, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 400);
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
