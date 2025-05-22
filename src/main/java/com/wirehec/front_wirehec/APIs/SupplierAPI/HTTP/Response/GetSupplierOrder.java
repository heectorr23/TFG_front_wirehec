package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetSupplierOrder {
    private static final Logger LOGGER = Logger.getLogger(GetSupplierOrder.class.getName());

    public List<SupplierOrderDTO> sendGetSupplierOrderRequest() {
        List<SupplierOrderDTO> supplierOrderList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8086/api/orderSupplier/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO();
                supplierOrderDTO.setIdPedidoProveedor(jsonObject.get("idPedidoProveedor").getAsLong());
                supplierOrderDTO.setFechaPedido(Date.valueOf(jsonObject.get("fechaPedido").getAsString()));
                supplierOrderDTO.setFechaEntrega(Date.valueOf(jsonObject.get("fechaEntrega").getAsString()));
                supplierOrderList.add(supplierOrderDTO);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierOrderList;
    }
}