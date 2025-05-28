package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetSupplierDetail {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

    public List<SupplierDetailDTO> sendGetSupplierDetailRequest() {
        List<SupplierDetailDTO> supplierDetailList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8086/api/supplierDetail/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();;

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                SupplierDetailDTO supplierDetailDTO = new SupplierDetailDTO();
                supplierDetailDTO.setIdDetalleProveedor(jsonObject.get("idDetalleProveedor").getAsLong());

                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setIdProveedor(jsonObject.get("supplier").getAsJsonObject().get("idProveedor").getAsLong());
                supplierDTO.setNombreProveedor(jsonObject.get("supplier").getAsJsonObject().get("nombreProveedor").getAsString());
                supplierDTO.setCifProveedor(jsonObject.get("supplier").getAsJsonObject().get("cifProveedor").getAsInt());
                supplierDTO.setEmailProveedor(jsonObject.get("supplier").getAsJsonObject().get("emailProveedor").getAsString());
                supplierDTO.setCategoriaProveedor(jsonObject.get("supplier").getAsJsonObject().get("categoriaProveedor").getAsString());
                supplierDTO.setProductoProveedor(jsonObject.get("supplier").getAsJsonObject().get("productoProveedor").getAsString());
                supplierDetailDTO.setSupplier(supplierDTO);

                JsonArray supplierOrdersArray = jsonObject.get("supplierOrders").getAsJsonArray();
                List<SupplierOrderDTO> supplierOrders = new ArrayList<>();
                for (int j = 0; j < supplierOrdersArray.size(); j++) {
                    JsonObject orderObject = supplierOrdersArray.get(j).getAsJsonObject();
                    SupplierOrderDTO orderDTO = new SupplierOrderDTO();
                    orderDTO.setIdPedidoProveedor(orderObject.get("idPedidoProveedor").getAsLong());
                    orderDTO.setFechaPedido(java.sql.Date.valueOf(orderObject.get("fechaPedido").getAsString()));
                    orderDTO.setFechaEntrega(java.sql.Date.valueOf(orderObject.get("fechaEntrega").getAsString()));
                    supplierOrders.add(orderDTO);
                }

            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierDetailList;
    }
}
