package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dto.CategoryDTO;
import com.example.dto.ReviewDTO;
import com.example.dto.UserDTO;
import com.example.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "update-category", value = "/update-category")
public class UpdateCategoryController extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        CategoryService categoryService = new CategoryService();
        try{
            UserDTO user = SessionValidator.checkSession(request);
            if(!user.getRole().equalsIgnoreCase("Admin")){
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            String id = request.getParameter("id");
            if (id == null || id.isBlank()) {
                throw new ApplicationException(Messages.CategoryError.INVALID_CATEGORY);
            }
            int Id = Integer.parseInt(id);
            if (Id <= 0) {
                throw new ApplicationException(Messages.CategoryError.INVALID_CATEGORY);
            }
            CategoryDTO categoryDTO = CustomObjectMapper.toObject(request.getReader(),CategoryDTO.class);
            categoryService.updateCategory(Id,categoryDTO.getCategoryName());
            sendResponse(response, null,null,null,200);
        }catch(DBException e){
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED,e.getMessage(),null,400);
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(),null,null,500);

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
