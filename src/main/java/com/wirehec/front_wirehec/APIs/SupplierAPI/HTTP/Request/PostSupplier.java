package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PostProduct;
import com.wirehec.front_wirehec.DTO.SupplierDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostSupplier {
    private static final Logger LOGGER = Logger.getLogger(PostProduct.class.getName());

    public void sendPostSupplierRequest(SupplierDTO supplierDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SupplierDTO.class, new SupplierDTOTypeAdapter())
                    .create();

            String json = gson.toJson(supplierDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8086/api/supplier/save"))
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

    private static class SupplierDTOTypeAdapter implements JsonSerializer<SupplierDTO> {
        @Override
        public JsonElement serialize(SupplierDTO supplierDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nombreProveedor", supplierDTO.getNombreProveedor());
            jsonObject.addProperty("cifProveedor", supplierDTO.getCifProveedor());
            jsonObject.addProperty("emailProveedor", supplierDTO.getEmailProveedor());
            jsonObject.addProperty("categoriaProveedor", supplierDTO.getCategoriaProveedor());
            jsonObject.addProperty("productoProveedor", supplierDTO.getProductoProveedor());
            return jsonObject;
        }
    }
}
