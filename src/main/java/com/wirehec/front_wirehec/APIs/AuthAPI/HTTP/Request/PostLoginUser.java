package com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostLoginUser {
    private static final Logger LOGGER = Logger.getLogger(PostLoginUser.class.getName());

    public String sendPostLoginUserRequest(String username, String passwordEmpleado) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("username", username);
            requestBody.addProperty("passwordEmpleado", passwordEmpleado);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            if (response.statusCode() == 200) {
                JsonObject responseBody = JsonParser.parseString(response.body()).getAsJsonObject();
                return responseBody.get("token").getAsString();
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud POST de login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}