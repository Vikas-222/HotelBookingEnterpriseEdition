package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dto.AdditionalChargesDTO;
import com.example.dto.UserDTO;
import com.example.service.AdditionalChargesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "insert-additional-charges", value = "/insert-additional-charges")
public class InsertAdditionalChargesController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        AdditionalChargesService service = new AdditionalChargesService();
        try{
            UserDTO user = SessionValidator.checkSession(request);
            if(!user.getRole().equalsIgnoreCase("admin")){
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            AdditionalChargesDTO charges = CustomObjectMapper.toObject(request.getReader(),AdditionalChargesDTO.class);
            service.addCharges(charges);
            sendResponse(response,null,null,null,200);
        }catch (DBException e) {
            e.printStackTrace();
            sendResponse(response,Messages.Error.FAILED,e.getMessage(),null,500);
        } catch (ApplicationException e) {
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
