package org.arshpsps.learnjee.api.dict;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "DictAPI", urlPatterns = "/api/dict")
public class DictApi extends HttpServlet {
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String word = req.getParameter("word");



    }
}
