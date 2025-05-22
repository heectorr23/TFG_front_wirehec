package com.wirehec.front_wirehec.APIs.ContabilityApi.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetContability {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

    public List<ContabilityDTO> sendGetContabilityRequest() {
        List<ContabilityDTO> contabilityList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/contability/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                ContabilityDTO contabilityDTO = new ContabilityDTO();
                contabilityDTO.setIdContabilidad(jsonObject.get("idContabilidad").getAsLong());
                contabilityDTO.setPresupuestomensual(jsonObject.get("presupuestomensual").getAsFloat());
                contabilityDTO.setBeneficio(jsonObject.get("beneficio").getAsFloat());
                contabilityDTO.setGasto(jsonObject.get("gasto").getAsFloat());
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return contabilityList;
    }
}
