package com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PutChangePassword {
    private static final Logger LOGGER = Logger.getLogger(PutChangePassword.class.getName());

    public String sendChangePasswordRequest(String token, String newPassword) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Crear el cuerpo de la solicitud
            String requestBody = "{\"newPassword\":\"" + newPassword + "\"}";

            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/changePassword"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token) // Token de autenticación
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            if (response.statusCode() == 200) {
                return "Contraseña actualizada correctamente.";
            } else {
                return "Error al actualizar la contraseña: " + response.body();
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud PUT de cambio de contraseña: " + e.getMessage());
            e.printStackTrace();
            return "Error al realizar la solicitud.";
        }
    }
}