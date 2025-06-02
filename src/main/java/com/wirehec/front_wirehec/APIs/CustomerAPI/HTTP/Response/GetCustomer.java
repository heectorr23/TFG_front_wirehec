package com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.DTO.CustomerDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetCustomer {
    private static final Logger LOGGER = Logger.getLogger(GetCustomer.class.getName());

    public List<CustomerDTO> sendGetCustomerRequest() {
        List<CustomerDTO> customerList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/customer/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                CustomerDTO customerDTO = new CustomerDTO();

                customerDTO.setId(jsonObject.has("id") && !jsonObject.get("id").isJsonNull() ? jsonObject.get("id").getAsLong() : null);
                customerDTO.setName(jsonObject.has("name") && !jsonObject.get("name").isJsonNull() ? jsonObject.get("name").getAsString() : null);
                customerDTO.setContacto(jsonObject.has("contacto") && !jsonObject.get("contacto").isJsonNull() ? jsonObject.get("contacto").getAsString() : null);
                customerDTO.setTelefono(jsonObject.has("telefono") && !jsonObject.get("telefono").isJsonNull() ? jsonObject.get("telefono").getAsInt() : 0);
                customerDTO.setIdentificacion(jsonObject.has("identificacion") && !jsonObject.get("identificacion").isJsonNull() ? jsonObject.get("identificacion").getAsString() : null);
                customerDTO.setEmail(jsonObject.has("email") && !jsonObject.get("email").isJsonNull() ? jsonObject.get("email").getAsString() : null);
                customerDTO.setZona(jsonObject.has("zona") && !jsonObject.get("zona").isJsonNull() ? jsonObject.get("zona").getAsString() : null);
                customerDTO.setDireccion(jsonObject.has("direccion") && !jsonObject.get("direccion").isJsonNull() ? jsonObject.get("direccion").getAsString() : null);

                customerList.add(customerDTO);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return customerList;
    }
}