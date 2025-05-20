package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Request.DeleteBill;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class DeleteSupplierOrder {
    private static final Logger LOGGER = Logger.getLogger(DeleteBill.class.getName());

    public void sendDeleteSupplierOrderRequest(SupplierOrderDTO supplierOrderDTO) {
        try {

            Long id = supplierOrderDTO.getIdPedidoProveedor();


            HttpClient client = HttpClient.newHttpClient();


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/orderSupplier/delete/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            if (response.statusCode() != 200) {
                LOGGER.severe("Error al eliminar el recurso: " + response.body());
            }

        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
