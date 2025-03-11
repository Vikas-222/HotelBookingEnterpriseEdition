package com.example.common;

import com.example.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Response {

    public static void responseMethod(HttpServletResponse response,int code, APIResponse apiResponse){
        ObjectMapper mapper = new ObjectMapper();
        try {
            PrintWriter out = response.getWriter();
            response.setStatus(code);
            out.write(mapper.writeValueAsString(apiResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
