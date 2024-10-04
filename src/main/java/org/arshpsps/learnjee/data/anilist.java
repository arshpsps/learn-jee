package org.arshpsps.learnjee.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private JsonNode queryAndMap(String query) {
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

    public JsonNode SearchAnimeWithTitle(String animeTitle) {
        final int pageSize = 10;

        // TODO: query to .gql or .graphql
        // TODO: "variables" to jackson, if possible
        String query = "{\n" + "\"query\": \"query($id: Int, $page: Int, $perPage: Int, $search: String) {Page(page: $page, perPage: $perPage) {pageInfo {hasNextPage} media(id: $id, search: $search, type: ANIME) {id title{romaji english native} averageScore studios {nodes {name id}} coverImage {extraLarge} }}}\",\n" + "  \"variables\": {\n" + "    \"search\": \"" + animeTitle + "\",\n" + "    \"page\": 1" + ",\n" + "    \"perPage\": " + pageSize + "\n" + "  }\n" + "}";

        return queryAndMap(query);
    }

    public JsonNode SearchAnimeWithId(int animeId) {
        String rawQuery = "query Query($mediaId: Int, $perPage: Int) {\\n  Media(id: $mediaId) {\\n    title {\\n      english\\n      native\\n      romaji\\n    }\\n    averageScore\\n    characters(perPage: $perPage) {\\n      pageInfo {\\n        perPage\\n        hasNextPage\\n        currentPage\\n      }\\n      nodes {\\n        image {\\n          large\\n        }\\n        name {\\n          native\\n          full\\n        }\\n      }\\n      edges {\\n        voiceActors {\\n          name {\\n            full\\n            native\\n          }\\n        }\\n      }\\n    }\\n    coverImage {\\n      extraLarge\\n    }\\n    description\\n    episodes\\n    genres\\n    id\\n    relations {\\n      pageInfo {\\n        currentPage\\n        perPage\\n        hasNextPage\\n      }\\n      nodes {\\n        title {\\n          english\\n          native\\n          romaji\\n        }\\n        id\\n      }\\n    }\\n    trailer {\\n      site\\n    }\\n    studios {\\n      pageInfo {\\n        currentPage\\n        hasNextPage\\n        perPage\\n      }\\n      nodes {\\n        name\\n        isAnimationStudio\\n      }\\n    }\\n    status\\n  }\\n}";
        String rawVars = "{\n" +
                "  \"mediaId\": " + animeId + ",\n" +
                "  \"perPage\": 10\n" +
                "}";
        String query = "{ \"query\": \"" + rawQuery + "\",\n" + "  \"variables\": " + rawVars + "\n}";

        return queryAndMap(query);
    }

}
