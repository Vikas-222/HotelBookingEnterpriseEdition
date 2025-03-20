package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.UserDTO;
import com.example.service.UserService;
import com.example.validation.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginController extends HttpServlet {

    private IUserDAO iUserDAO = new UserDAOImpl();
    private UserService userService = new UserService(iUserDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        try {
            UserDTO user = mapper.readValue(request.getReader(), UserDTO.class);
            if (!UserValidator.isNullCheckLoginValues(user.getEmail(), user.getPassword())) {
                throw new ApplicationException(Messages.Error.INVALID_CREDENTIALS);
            }
            UserDTO userDTO = userService.userLogin(user);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5 * 60);
            session.setAttribute("user", userDTO);
            sendResponse(response, Messages.LOGIN_SUCCESSFUL, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, "Something went wrong", e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 400);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, "Something went wrong", e.getMessage(), null, 500);
        }
    }

    private void sendResponse(HttpServletResponse response, String message, String technicalMessage, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, technicalMessage, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
