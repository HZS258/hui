package com.javaweb.servlet;

import com.javaweb.dao.CarDao;
import com.javaweb.pojo.Car;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        String username=request.getParameter("username");
        String password=request.getParameter("pd");

        CarDao dao=new CarDao();
        Car user=dao.Login(username,password);

        JSONObject jo=new JSONObject();
        if(user!=null){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
            jo=(JSONObject) JSONSerializer.toJSON(user,jsonConfig);
        }else {
            jo.put("code",400);
            jo.put("message","错误的用户名或密码");
        }
        out.println(jo.toString());
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
