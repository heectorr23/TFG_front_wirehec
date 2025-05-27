package com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Response;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetEmployee {
    private static final Logger LOGGER = Logger.getLogger(GetEmployee.class.getName());

    public List<EmployeeDTO> sendGetEmployeeRequest() {
        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/employee/all"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Respuesta de la API: " + response.body());

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(EmployeeDTO.class, new EmployeeDTOTypeAdapter())
                    .create();

            employees = gson.fromJson(response.body(), new TypeToken<List<EmployeeDTO>>() {}.getType());

        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        if (employees.isEmpty()) {
            LOGGER.warning("No se encontraron empleados o la lista está vacía.");
        }

        return employees;
    }

    private static class EmployeeDTOTypeAdapter implements JsonDeserializer<EmployeeDTO> {
        @Override
        public EmployeeDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            EmployeeDTO employee = new EmployeeDTO();
            employee.setIdEmpleado(jsonObject.get("idEmpleado").getAsLong());
            employee.setNombreEmpleado(jsonObject.get("nombreEmpleado").getAsString());
            employee.setNifEmpleado(jsonObject.get("nifEmpleado").getAsString());
            employee.setTelefonoEmpleado(jsonObject.get("telefonoEmpleado").getAsInt());
            employee.setEmail(jsonObject.get("email").getAsString());
            employee.setUsername(jsonObject.has("username") && !jsonObject.get("username").isJsonNull() ? jsonObject.get("username").getAsString() : null);
            employee.setPasswordEmpleado(jsonObject.get("passwordEmpleado").getAsString());
            employee.setBeneficioEmpleado(jsonObject.get("beneficioEmpleado").getAsInt());
            employee.setHoraEntrada(LocalDateTime.parse(jsonObject.get("horaEntrada").getAsString()));
            employee.setHoraSalida(LocalDateTime.parse(jsonObject.get("horaSalida").getAsString()));
            employee.setSalario(jsonObject.get("salario").getAsBigDecimal());

            JsonArray rolesArray = jsonObject.getAsJsonArray("roles");
            List<RoleDTO> roles = new ArrayList<>();
            for (JsonElement roleElement : rolesArray) {
                JsonObject roleObject = roleElement.getAsJsonObject();
                RoleDTO role = new RoleDTO();
                role.setId(roleObject.get("id").getAsLong());
                role.setName(roleObject.get("name").getAsString());
                roles.add(role);
            }
            employee.setRoles(roles);

            return employee;
        }
    }
}