package org.arshpsps.learnjee.api.anidb;

import com.fasterxml.jackson.databind.JsonNode;
import org.arshpsps.learnjee.data.anilist;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AniDB", urlPatterns = "/anidb")
public class Anidb extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String searchKeyword = "frieren";
        if (request.getParameter("anime") != null && !request.getParameter("anime").isEmpty()) {
            searchKeyword = request.getParameter("anime");
        } else if (request.getParameter("animeId") != null && !request.getParameter("animeId").isEmpty()) {
            searchKeyword = request.getParameter("animeId");
        }

        PrintWriter out = response.getWriter();

        JsonNode data = null;
        try {
            data = new anilist().SearchAnimeWithId(Integer.parseInt(searchKeyword));
        } catch (Exception e) {
            out.println(e.getMessage());
        }

        assert data != null;
        out.println(data);
    }

}


