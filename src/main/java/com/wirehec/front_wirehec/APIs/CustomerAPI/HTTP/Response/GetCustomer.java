package com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;
import com.wirehec.front_wirehec.DTO.CustomerDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetCustomer {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

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
                customerDTO.setName(jsonObject.get("name").getAsString());
                customerDTO.setContacto(jsonObject.get("contacto").getAsString());
                customerDTO.setTelefono(jsonObject.get("telefono").getAsInt());
                customerDTO.setIdentificacion(jsonObject.get("identificacion").getAsString());
                customerDTO.setEmail(jsonObject.get("email").getAsString());
                customerDTO.setZona(jsonObject.get("zona").getAsString());
                customerDTO.setDireccion(jsonObject.get("direccion").getAsString());
                customerList.add(customerDTO);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return customerList;
    }
}
