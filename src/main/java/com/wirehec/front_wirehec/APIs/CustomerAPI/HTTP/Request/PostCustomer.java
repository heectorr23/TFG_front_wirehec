package com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;
import com.wirehec.front_wirehec.DTO.CustomerDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostCustomer {
    private static final Logger LOGGER = Logger.getLogger(PostCustomer.class.getName());

    public void sendPostCustomerRequest(CustomerDTO customerDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ContabilityDTO.class, new CustomerDTOTypeAdapter())
                    .create();

            String json = gson.toJson(customerDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/customer/save"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud POST: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class CustomerDTOTypeAdapter implements JsonSerializer<CustomerDTO> {
        @Override
        public JsonElement serialize(CustomerDTO customerDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", customerDTO.getName());
            jsonObject.addProperty("contacto", customerDTO.getContacto());
            jsonObject.addProperty("telefono", customerDTO.getTelefono());
            jsonObject.addProperty("identificacion", customerDTO.getIdentificacion());
            jsonObject.addProperty("email", customerDTO.getEmail());
            jsonObject.addProperty("zona", customerDTO.getZona());
            jsonObject.addProperty("direccion", customerDTO.getDireccion());
            return jsonObject;
        }
    }
}
