package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetSupplierOrder {
    private static final Logger LOGGER = Logger.getLogger(GetSupplierOrder.class.getName());

    public List<SupplierOrderDTO> sendGetSupplierOrderRequest() {
        List<SupplierOrderDTO> supplierOrders = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/orderSupplier/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            if (response.statusCode() == 200) {
                JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    SupplierOrderDTO orderDTO = new SupplierOrderDTO();

                    // Mapear los campos del JSON al DTO
                    orderDTO.setIdPedidoProveedor(jsonObject.get("idPedidoProveedor").getAsLong());
                    orderDTO.setFechaPedido(LocalDate.parse(jsonObject.get("fechaPedido").getAsString()));
                    orderDTO.setFechaEntrega(LocalDate.parse(jsonObject.get("fechaEntrega").getAsString()));

                    supplierOrders.add(orderDTO);
                }
            } else {
                LOGGER.severe("Error en la respuesta de la API: " + response.body());
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierOrders;
    }
}