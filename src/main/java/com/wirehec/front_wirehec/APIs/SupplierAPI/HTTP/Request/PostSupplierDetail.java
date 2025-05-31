package com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.DTO.Adapters.SupplierDetailDTOTypeAdapter;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostSupplierDetail {
    private static final Logger LOGGER = Logger.getLogger(PostSupplierDetail.class.getName());
    private static boolean isRequestInProgress = false; // Bandera para evitar solicitudes duplicadas

    public void sendPostSupplierDetailRequest(SupplierDetailDTO supplierDetailDTO) {
        if (isRequestInProgress) {
            LOGGER.warning("Solicitud POST ya en progreso. Operación cancelada.");
            return; // Evitar solicitudes duplicadas
        }
        isRequestInProgress = true;

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SupplierDetailDTO.class, new SupplierDetailDTOTypeAdapter())
                    .create();
            String json = gson.toJson(supplierDetailDTO);

            // Log para verificar el JSON
            System.out.println("JSON enviado: " + json);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/supplierDetail/save"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Código de estado: " + response.statusCode());
            System.out.println("Respuesta del servidor: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isRequestInProgress = false; // Restablecer la bandera
        }
    }
}