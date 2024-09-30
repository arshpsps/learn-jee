package org.arshpsps.learnjee;

import java.io.*;
//import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AllServlet", urlPatterns = "/allServlet")
public class AllServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        String username = request.getParameter("username");

        if (username != null && !username.isEmpty()) {
            session.setAttribute("username", username);
        }

        PrintWriter out = response.getWriter();
        out.println("Request: " + request.getParameter("username"));
        out.println("Session: " + session.getAttribute("username"));
    }

}
