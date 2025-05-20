package com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetEmployee {
    private static final Logger LOGGER = Logger.getLogger(GetBIll.class.getName());

    public List<EmployeeDTO> sendGetEmployeeRequest() {
        List<EmployeeDTO> employeeList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8080/api/employee/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Codigo de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setNombreEmpleado(jsonObject.get("nombreEmpleado").getAsString());
                employeeDTO.setNifEmpleado(jsonObject.get("nifEmpleado").getAsString());
                employeeDTO.setTelefonoEmpleado(jsonObject.get("telefonoEmpleado").getAsInt());
                employeeDTO.setEmail(jsonObject.get("email").getAsString());
                employeeDTO.setUsername(jsonObject.get("username").getAsString());
                employeeDTO.setPasswordEmpleado(jsonObject.get("passwordEmpleado").getAsString());
                employeeDTO.setBeneficioEmpleado(jsonObject.get("beneficioEmpleado").getAsInt());
                employeeDTO.setSalario(jsonObject.get("salario").getAsBigDecimal());
                employeeDTO.setHoraEntrada(LocalDateTime.parse(jsonObject.get("horaEntrada").getAsString(), formatter));
                employeeDTO.setHoraSalida(LocalDateTime.parse(jsonObject.get("horaSalida").getAsString(), formatter));

                JsonArray rolesArray = jsonObject.getAsJsonArray("roles");
                List<RoleDTO> roles = new ArrayList<>();
                for (int j = 0; j < rolesArray.size(); j++) {
                    JsonObject roleObject = rolesArray.get(j).getAsJsonObject();
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setId(roleObject.get("id").getAsInt());
                    roleDTO.setName(roleObject.get("name").getAsString());
                    roles.add(roleDTO);
                }
                employeeDTO.setRoles(roles);
            }
        } catch (Exception e) {
            LOGGER.severe("Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return employeeList;
    }
}