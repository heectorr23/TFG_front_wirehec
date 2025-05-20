package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.SupplierDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetSupplier {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

    public List<SupplierDTO> sendGetSupplierRequest() {
        List<SupplierDTO> supplierList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8080/api/supplier/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();;

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                SupplierDTO productDTO = new SupplierDTO();
                productDTO.setIdProveedor(jsonObject.get("idProveedor").getAsLong());
                productDTO.setNombreProveedor(jsonObject.get("nombreProveedor").getAsString());
                productDTO.setCifProveedor(jsonObject.get("cifProveedor").getAsInt());
                productDTO.setEmailProveedor(jsonObject.get("emailProveedor").getAsString());
                productDTO.setCategoriaProveedor(jsonObject.get("categoriaProveedor").getAsString());
                productDTO.setProductoProveedor(jsonObject.get("productoProveedor").getAsString());
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierList;
    }
}
