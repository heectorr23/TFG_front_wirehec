package com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Request;

import com.google.gson.*;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PostEmployee {
    private static final Logger LOGGER = Logger.getLogger(PostEmployee.class.getName());

    public void sendPostEmployeeRequest(EmployeeDTO employeeDTO) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(EmployeeDTO.class, new EmployeeDTOTypeAdapter())
                    .create();

            String json = gson.toJson(employeeDTO);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/employee/save"))
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

    private static class EmployeeDTOTypeAdapter implements JsonSerializer<EmployeeDTO> {
        @Override
        public JsonElement serialize(EmployeeDTO employeeDTO, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nombreEmpleado", employeeDTO.getNombreEmpleado());
            jsonObject.addProperty("nifEmpleado", employeeDTO.getNifEmpleado());
            jsonObject.addProperty("telefonoEmpleado", employeeDTO.getTelefonoEmpleado());
            jsonObject.addProperty("email", employeeDTO.getEmail());
            jsonObject.addProperty("username", employeeDTO.getUsername());
            jsonObject.addProperty("passwordEmpleado", employeeDTO.getPasswordEmpleado());
            jsonObject.addProperty("beneficioEmpleado", employeeDTO.getBeneficioEmpleado());
            jsonObject.addProperty("horaEntrada", employeeDTO.getHoraEntrada().toString());
            jsonObject.addProperty("horaSalida", employeeDTO.getHoraSalida().toString());
            jsonObject.addProperty("salario", employeeDTO.getSalario().toString());
            JsonArray rolesArray = new JsonArray();
            for (RoleDTO role : employeeDTO.getRoles()) {
                JsonObject roleObject = new JsonObject();
                roleObject.addProperty("Name", role.getName());
                rolesArray.add(roleObject);
            }
            return jsonObject;
        }
    }
}
