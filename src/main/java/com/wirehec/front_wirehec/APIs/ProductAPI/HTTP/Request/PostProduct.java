package com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.DTO.ProductDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostProduct {
    private static final Logger LOGGER = Logger.getLogger(PostProduct.class.getName());

    public void sendPostProductRequest(ProductDTO productDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ProductDTO.class, new ProductDTOTypeAdapter())
                    .create();

            String json = gson.toJson(productDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/product/save"))
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

    private static class ProductDTOTypeAdapter implements JsonSerializer<ProductDTO> {
        @Override
        public JsonElement serialize(ProductDTO productDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nombreProducto", productDTO.getNombreProducto());
            jsonObject.addProperty("categoriaProducto", productDTO.getCategoriaProducto());
            jsonObject.addProperty("precioVenta", productDTO.getPrecioVenta().toString());
            jsonObject.addProperty("stock", productDTO.getStock());
            jsonObject.addProperty("precioCoste", productDTO.getPrecioCoste().toString());
            return jsonObject;
        }
    }
}
