package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PostProduct;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostSupplierOrder {
    private static final Logger LOGGER = Logger.getLogger(PostProduct.class.getName());

    public void sendPostSupplierOrderRequest(SupplierOrderDTO supplierOrderDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SupplierOrderDTO.class, new SupplierOrderDTOTypeAdapter())
                    .create();

            String json = gson.toJson(supplierOrderDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/orderSupplier/save"))
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

    private static class SupplierOrderDTOTypeAdapter implements JsonSerializer<SupplierOrderDTO> {
        @Override
        public JsonElement serialize(SupplierOrderDTO supplierOrderDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("FechaPedido", supplierOrderDTO.getFechaPedido().toString());
            jsonObject.addProperty("FechaEntrega", supplierOrderDTO.getFechaEntrega().toString());
            return jsonObject;
        }
    }
}
