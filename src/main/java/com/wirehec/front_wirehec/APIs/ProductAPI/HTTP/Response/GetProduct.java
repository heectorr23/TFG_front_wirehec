package com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.ProductDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetProduct {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

    public List<ProductDTO> sendGetProductRequest() {
        List<ProductDTO> productList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8080/api/product/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();;

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(jsonObject.get("id").getAsLong());
                productDTO.setNombreProducto(jsonObject.get("nombreProducto").getAsString());
                productDTO.setCategoriaProducto(jsonObject.get("categoriaProducto").getAsString());
                productDTO.setPrecioVenta(jsonObject.get("precioVenta").getAsBigDecimal());
                productDTO.setStock(jsonObject.get("stock").getAsInt());
                productDTO.setPrecioCoste(jsonObject.get("precioCoste").getAsBigDecimal());
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return productList;
    }
}
