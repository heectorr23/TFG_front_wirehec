package com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import com.wirehec.front_wirehec.DTO.RegisterUserDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostRegisterUser {
    private static final Logger LOGGER = Logger.getLogger(PostRegisterUser.class.getName());
    private final TokenConstants tokenConstants = new TokenConstants();
    private  String token = tokenConstants.TOKEN;

    public void sendPostRegisterUserRequest(RegisterUserDTO registerUserDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(RegisterUserDTO.class, new RegisterUserDTOTypeAdapter())
                    .create();

            String json = gson.toJson(registerUserDTO);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/createUser"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Registrar detalles de la solicitud
            LOGGER.info("Enviando solicitud HTTP:");
            LOGGER.info("URL: " + request.uri());
            LOGGER.info("Encabezados: " + request.headers().map());
            LOGGER.info("Cuerpo de la solicitud: " + json);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Registrar detalles de la respuesta
            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud POST de registro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setToken(String jwtToken) {
        token = jwtToken;
    }

    private static class RegisterUserDTOTypeAdapter implements JsonSerializer<RegisterUserDTO> {
        @Override
        public JsonElement serialize(RegisterUserDTO registerUserDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", registerUserDTO.getUsername());
            jsonObject.addProperty("passwordEmpleado", registerUserDTO.getPasswordEmpleado());
            jsonObject.addProperty("email", registerUserDTO.getEmail());
            JsonArray rolesArray = new JsonArray();
            for (RoleDTO role : registerUserDTO.getRoles()) {
                JsonObject roleObject = new JsonObject();
                roleObject.addProperty("name", role.getName());
                rolesArray.add(roleObject);
            }
            jsonObject.add("roles", rolesArray);
            jsonObject.addProperty("beneficioEmpleado", registerUserDTO.getBeneficioEmpleado());
            jsonObject.addProperty("salario", registerUserDTO.getSalario().toString());
            jsonObject.addProperty("telefonoEmpleado", registerUserDTO.getTelefonoEmpleado());
            jsonObject.addProperty("horaEntrada", registerUserDTO.getHoraEntrada().toString());
            jsonObject.addProperty("horaSalida", registerUserDTO.getHoraSalida().toString());
            jsonObject.addProperty("nifEmpleado", registerUserDTO.getNifEmpleado());
            jsonObject.addProperty("nombreEmpleado", registerUserDTO.getNombreEmpleado());
            return jsonObject;
        }
    }
}