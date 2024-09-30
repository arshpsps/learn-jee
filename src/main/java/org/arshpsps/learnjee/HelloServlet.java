package org.arshpsps.learnjee;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "HelloServlet", urlPatterns = "/helloServlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public HelloServlet() {
        System.out.println("HELLO MY NIGGER");
    }

//    public void init() {
//        this.message = "Hello World!";
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();


        out.println("{ \"servlets\": [");
        StringBuilder str = new StringBuilder();
        ServletConfig conf =  getServletConfig();
        conf.getServletContext().getServletRegistrations().forEach((s, servletRegistration) -> {

            str.append(String.format("\"%s\", ", s));
        });
        str.deleteCharAt(str.length()-2);
        out.println(str.toString());
        // Hello
        out.println("]}");
    }

    public void destroy() {
    }
}