package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PostProduct;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostSupplierDetail {
    private static final Logger LOGGER = Logger.getLogger(PostProduct.class.getName());

    public void sendPostSupplierDetailRequest(SupplierDetailDTO supplierDetailDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SupplierDetailDTO.class, new SupplierDetailDTOTypeAdapter())
                    .create();

            String json = gson.toJson(supplierDetailDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/supplierDetail/save"))
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

    private static class SupplierDetailDTOTypeAdapter implements JsonSerializer<SupplierDetailDTO> {
        @Override
        public JsonElement serialize(SupplierDetailDTO supplierDetailDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("idProduct", supplierDetailDTO.getIdProduct());
            jsonObject.add("supplier", context.serialize(supplierDetailDTO.getSupplier()));
            return jsonObject;
        }
    }
}
