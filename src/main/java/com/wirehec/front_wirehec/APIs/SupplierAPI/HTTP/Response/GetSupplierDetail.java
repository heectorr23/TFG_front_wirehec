package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetSupplierDetail {
    private static final Logger LOGGER = Logger.getLogger(GetSupplierDetail.class.getName());

    public List<SupplierDetailDTO> sendGetSupplierDetailRequest() throws IOException, InterruptedException {
        List<SupplierDetailDTO> supplierDetailList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/supplierDetail/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                SupplierDetailDTO supplierDetailDTO = new SupplierDetailDTO();

                if (jsonObject.has("idDetalleProveedor") && !jsonObject.get("idDetalleProveedor").isJsonNull()) {
                    supplierDetailDTO.setIdDetalleProveedor(jsonObject.get("idDetalleProveedor").getAsLong());
                }

                if (jsonObject.has("supplierEntity") && !jsonObject.get("supplierEntity").isJsonNull()) {
                    JsonObject supplierObject = jsonObject.get("supplierEntity").getAsJsonObject();
                    SupplierDTO supplierDTO = new SupplierDTO();

                    if (supplierObject.has("idProveedor") && !supplierObject.get("idProveedor").isJsonNull()) {
                        supplierDTO.setIdProveedor(supplierObject.get("idProveedor").getAsLong());
                    }
                    if (supplierObject.has("nombreProveedor") && !supplierObject.get("nombreProveedor").isJsonNull()) {
                        supplierDTO.setNombreProveedor(supplierObject.get("nombreProveedor").getAsString());
                    }
                    if (supplierObject.has("cifProveedor") && !supplierObject.get("cifProveedor").isJsonNull()) {
                        supplierDTO.setCifProveedor(supplierObject.get("cifProveedor").getAsInt());
                    }
                    if (supplierObject.has("emailProveedor") && !supplierObject.get("emailProveedor").isJsonNull()) {
                        supplierDTO.setEmailProveedor(supplierObject.get("emailProveedor").getAsString());
                    }
                    if (supplierObject.has("categoriaProveedor") && !supplierObject.get("categoriaProveedor").isJsonNull()) {
                        supplierDTO.setCategoriaProveedor(supplierObject.get("categoriaProveedor").getAsString());
                    }
                    if (supplierObject.has("productoProveedor") && !supplierObject.get("productoProveedor").isJsonNull()) {
                        supplierDTO.setProductoProveedor(supplierObject.get("productoProveedor").getAsString());
                    }

                    supplierDetailDTO.setSupplier(supplierDTO);
                }

                if (jsonObject.has("supplierOrderEntities") && !jsonObject.get("supplierOrderEntities").isJsonNull()) {
                    JsonArray supplierOrdersArray = jsonObject.get("supplierOrderEntities").getAsJsonArray();
                    List<SupplierOrderDTO> supplierOrders = new ArrayList<>();

                    for (int j = 0; j < supplierOrdersArray.size(); j++) {
                        JsonObject orderObject = supplierOrdersArray.get(j).getAsJsonObject();
                        SupplierOrderDTO orderDTO = new SupplierOrderDTO();

                        if (orderObject.has("idPedidoProveedor") && !orderObject.get("idPedidoProveedor").isJsonNull()) {
                            orderDTO.setIdPedidoProveedor(orderObject.get("idPedidoProveedor").getAsLong());
                        }
                        if (orderObject.has("fechaPedido") && !orderObject.get("fechaPedido").isJsonNull()) {
                            orderDTO.setFechaPedido(LocalDate.parse(orderObject.get("fechaPedido").getAsString()));
                        }
                        if (orderObject.has("fechaEntrega") && !orderObject.get("fechaEntrega").isJsonNull()) {
                            orderDTO.setFechaEntrega(LocalDate.parse(orderObject.get("fechaEntrega").getAsString()));
                        }

                        supplierOrders.add(orderDTO);
                    }

                    supplierDetailDTO.setSupplierOrders(supplierOrders);
                }

                supplierDetailList.add(supplierDetailDTO);
            }
        } catch (JsonSyntaxException e) {
            LOGGER.severe("Error al analizar la respuesta JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierDetailList;
    }
}