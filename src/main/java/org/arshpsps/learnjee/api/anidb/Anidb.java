package org.arshpsps.learnjee.api.anidb;

import com.fasterxml.jackson.databind.*;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.arshpsps.learnjee.data.anilist;

@WebServlet(name = "AniDB", urlPatterns = "/anidb")
public class Anidb extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        JsonNode data = null;
        try {
            data = new anilist().SearchAnimeWithTitle("Fate");
        } catch (Exception e) {
            out.println(e.getMessage());
        }

        assert data != null;
        out.println(data.toString());
    }

}


