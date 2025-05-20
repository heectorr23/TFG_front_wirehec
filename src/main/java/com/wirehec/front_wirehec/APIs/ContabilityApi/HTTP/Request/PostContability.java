package com.wirehec.front_wirehec.APIs.ContabilityApi.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostContability {
    private static final Logger LOGGER = Logger.getLogger(PostContability.class.getName());

    public void sendPostContabilityRequest(ContabilityDTO contabilityDTO) {
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

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud POST: " + e.getMessage());
            e.printStackTrace();
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
