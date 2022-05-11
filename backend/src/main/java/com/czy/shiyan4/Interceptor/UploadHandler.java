package com.czy.shiyan4.Interceptor;

import com.czy.shiyan4.entity.resp.Result;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class UploadHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            System.out.println("Method:OPTIONS");
            return true;
        }
        String requestURI = request.getRequestURI();
        if (!(requestURI.indexOf("/file/upload") >= 0)) {
            return true;
        }
        String token = request.getHeader("token");
        System.out.println(token);
        if(!token.equals("null") && token != null){
            return true;
        }
        //Object json = JSONObject.toJSON(Result.error().message("请登录后上传"));
        String json = JSONObject.toJSONString(Result.error().message("用户未登录，请先登录"));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        out.append(json);
        return false;
    }
}
