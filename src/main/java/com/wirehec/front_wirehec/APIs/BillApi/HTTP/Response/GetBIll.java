package com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.DTO.FacturaDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetBIll {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

    public List<FacturaDTO> sendGetBillRequest() {
        List<FacturaDTO> facturaList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8080/api/bill/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                FacturaDTO facturaDTO = new FacturaDTO();
                facturaDTO.setId(jsonObject.get("id").getAsLong());
                facturaDTO.setPrecio(jsonObject.get("Precio").getAsBigDecimal());
                facturaDTO.setZona(jsonObject.get("Zona").getAsString());
                facturaDTO.setDireccion(jsonObject.get("Direccion").getAsString());
                facturaList.add(facturaDTO);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return facturaList;
    }
}