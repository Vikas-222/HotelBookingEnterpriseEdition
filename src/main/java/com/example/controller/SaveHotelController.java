package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dto.HotelDTO;
import com.example.service.HotelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "save-hotel", value = "/save-hotel")
public class SaveHotelController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        try {
            HotelDTO h1 = CustomObjectMapper.toObject(request.getReader(), HotelDTO.class);
            HotelService service = new HotelService();
            service.saveHotel(h1);
            sendResponse(response, null, null, h1, 200);
        }catch(DBException e){
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED,e.getMessage(),null,500);
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(),null,null,400);
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
