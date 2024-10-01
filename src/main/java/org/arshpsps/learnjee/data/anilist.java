package org.arshpsps.learnjee.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class anilist {
    private String request(String query) throws RuntimeException {
        URL url = null;
        try {
            url = new URL("https://graphql.anilist.co");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }

        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        DataOutputStream outStream = null;
        try {
            outStream = new DataOutputStream(conn.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outStream.writeBytes(query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int responseCode;
        try {
            responseCode = conn.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (responseCode != 200) {
            throw new RuntimeException("HTTP Response Code: " + responseCode);
        } else {
            StringBuilder resp = new StringBuilder();

            final BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            reader.lines().forEach(resp::append);

            return resp.toString();
        }
    }

    public JsonNode SearchAnimeWithTitle(String animeTitle) {
        final int pageSize = 10;

        String query = "{\n" +
                "\"query\": \"query($id: Int, $page: Int, $perPage: Int, $search: String) {Page(page: $page, perPage: $perPage) {pageInfo {hasNextPage} media(id: $id, search: $search, type: ANIME) {id title{romaji english native}}}}\",\n" +
                "  \"variables\": {\n" +
                "    \"search\": \"" +
                animeTitle +
                "\",\n" +
                "    \"page\": 1" +
                ",\n" +
                "    \"perPage\": " +
                pageSize +
                "\n" +
                "  }\n" +
                "}";


        System.out.println(query);
        final String response = request(query);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode animeNode = null;
        try {
            animeNode = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON");
        }
        return animeNode;
    }

}
