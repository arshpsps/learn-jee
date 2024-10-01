package org.arshpsps.learnjee.api.anidb;

import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AniDB", urlPatterns = "/anidb")
public class Anidb extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        URL url = new URL("https://graphql.anilist.co");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        String animeId = "154587";

        StringBuilder data = new StringBuilder();
        data.append("{\n" +
                "  \"query\": \"query query($id: Int) {\\n  Media(id: $id, type: ANIME) {\\n    id\\n    title {\\n      romaji\\n      english\\n      native\\n    }\\n  }\\n}\\n\",\n" +
                "  \"variables\": {\n" +
                "    \"id\": ");

        data.append(animeId);
        data.append("\n" +
                "  }\n" +
                "}");

        out.println(data.toString());
        out.println("--------");

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.writeBytes(data.toString());
        outStream.flush();

//        conn.connect();
        int responseCode = conn.getResponseCode();
        out.println(responseCode);
        if (responseCode != 200) {
            out.println("not OK: " + responseCode);
        } else {
            StringBuilder resp = new StringBuilder();

            final var reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            reader.lines().forEach(resp::append);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode animeNode = mapper.readTree(resp.toString());
            out.println(animeNode);


            System.out.println(animeNode);
        }

    }

    static class anime {

    }

}


