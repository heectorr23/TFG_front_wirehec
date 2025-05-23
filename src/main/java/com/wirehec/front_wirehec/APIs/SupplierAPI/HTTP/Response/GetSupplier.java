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
                    .uri(URI.create("http://localhost:8086/api/supplier/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setIdProveedor(jsonObject.get("idProveedor").getAsLong());
                supplierDTO.setNombreProveedor(jsonObject.get("nombreProveedor").getAsString());
                supplierDTO.setCifProveedor(jsonObject.get("cifProveedor").getAsInt());
                supplierDTO.setEmailProveedor(jsonObject.get("emailProveedor").getAsString());
                supplierDTO.setCategoriaProveedor(jsonObject.get("categoriaProveedor").getAsString());
                supplierDTO.setProductoProveedor(jsonObject.get("productoProveedor").getAsString());
                supplierList.add(supplierDTO);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierList;
    }
}
