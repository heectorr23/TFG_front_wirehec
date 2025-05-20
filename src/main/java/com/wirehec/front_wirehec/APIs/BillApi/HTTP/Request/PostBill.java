package com.wirehec.front_wirehec.APIs.BillApi.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.DTO.FacturaDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostBill {
    private static final Logger LOGGER = Logger.getLogger(PostBill.class.getName());

    public void sendPostBillRequest(FacturaDTO facturaDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(FacturaDTO.class, new FacturaDTOTypeAdapter())
                    .create();

            String json = gson.toJson(facturaDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/bill/save"))
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

    private static class FacturaDTOTypeAdapter implements JsonSerializer<FacturaDTO> {
        @Override
        public JsonElement serialize(FacturaDTO facturaDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Precio", facturaDTO.getPrecio());
            jsonObject.addProperty("Zona", facturaDTO.getZona());
            jsonObject.addProperty("Direccion", facturaDTO.getDireccion());
            return jsonObject;
        }
    }
}
