package com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.DTO.Adapters.EmployeeDTOTypeAdapter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PutEmployee {
    private static final Logger LOGGER = Logger.getLogger(PutEmployee.class.getName());

    public void sendPutEmployeeRequest(EmployeeDTO employeeDTO, Long id) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(EmployeeDTO.class, new EmployeeDTOTypeAdapter())
                    .create();

            String json = gson.toJson(employeeDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/employee/update/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud PUT: " + e.getMessage());
            e.printStackTrace();
        }
    }
}