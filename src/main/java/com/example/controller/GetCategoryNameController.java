package com.example.controller;

import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dto.UserDTO;
import com.example.service.AmenityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "get-category-name", value = "/get-category-name")
public class GetCategoryNameController extends HttpServlet {
    AmenityService amenityService = new AmenityService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDTO user = SessionValidator.checkSession(request);
            if (!user.getRole().equalsIgnoreCase("Admin")) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            String id = request.getParameter("Id");
            if (id.isBlank()) {
                throw new ApplicationException(Messages.AmenityError.INVALID_CATEGORY);
            }
            int Id = Integer.parseInt(id);
            if (Id <= 0) {
                throw new ApplicationException(Messages.AmenityError.INVALID_CATEGORY);
            }
            String name = amenityService.getCategoryName(Id);
            sendResponse(response,null,null,name,200);
        }catch (DBException e) {
            e.printStackTrace();
            sendResponse(response,Messages.Error.FAILED,e.getMessage(),null,500);
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response,e.getMessage(),null,null,400);
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
