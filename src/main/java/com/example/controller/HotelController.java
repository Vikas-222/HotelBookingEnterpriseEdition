package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Response;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.impl.HotelDao;
import com.example.model.Hotel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "save-hotel", value = "/save-hotel")
public class HotelController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
            Hotel h1 = CustomObjectMapper.toObject(request.getReader(),Hotel.class);
            HotelDao hotel = new HotelDao();
            hotel.saveHotel(h1);
            sendResponse(response, "Hello",null,h1,200);
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
