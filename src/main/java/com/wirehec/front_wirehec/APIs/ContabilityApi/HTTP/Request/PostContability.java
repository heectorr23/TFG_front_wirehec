package com.wirehec.front_wirehec.APIs.ContabilityApi.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.APIs.ContabilityApi.HTTP.Response.GetContability;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

public class PostContability {
    private static final Logger LOGGER = Logger.getLogger(PostContability.class.getName());
    private final Object lock = new Object(); // Bloqueo para concurrencia
    private boolean isRequestInProgress = false;

    public void sendPostContabilityRequest(ContabilityDTO contabilityDTO) {
        synchronized (lock) {
            if (isRequestInProgress) {
                LOGGER.warning("Ya hay una solicitud en progreso. Ignorando esta solicitud.");
                return;
            }
            isRequestInProgress = true;
        }

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ContabilityDTO.class, new ContabilityDTOTypeAdapter())
                    .create();

            String json = gson.toJson(contabilityDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/contability/save"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                LOGGER.info("Registro creado exitosamente: " + response.body());
            } else {
                LOGGER.severe("Error al crear el registro. Código de estado: " + response.statusCode());
                LOGGER.severe("Respuesta de la API: " + response.body());
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud POST: " + e.getMessage());
            e.printStackTrace();
        } finally {
            synchronized (lock) {
                isRequestInProgress = false;
            }
        }
    }

    private static class ContabilityDTOTypeAdapter implements JsonSerializer<ContabilityDTO> {
        @Override
        public JsonElement serialize(ContabilityDTO contabilityDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("presupuestomensual", contabilityDTO.getPresupuestomensual());
            jsonObject.addProperty("beneficio", contabilityDTO.getBeneficio());
            jsonObject.addProperty("gasto", contabilityDTO.getGasto());
            return jsonObject;
        }
    }
}