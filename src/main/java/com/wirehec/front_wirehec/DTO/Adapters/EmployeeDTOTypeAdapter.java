package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDTOTypeAdapter extends TypeAdapter<EmployeeDTO> {
    @Override
    public void write(JsonWriter out, EmployeeDTO value) throws IOException {
        out.beginObject();
        out.name("idEmpleado").value(value.getIdEmpleado());
        out.name("nombreEmpleado").value(value.getNombreEmpleado());
        out.name("nifEmpleado").value(value.getNifEmpleado());
        out.name("telefonoEmpleado").value(value.getTelefonoEmpleado());
        out.name("email").value(value.getEmail());
        out.name("username").value(value.getUsername());
        out.name("passwordEmpleado").value(value.getPasswordEmpleado());

        out.name("roles").beginArray();
        for (RoleDTO role : value.getRoles()) {
            out.beginObject();
            out.name("id").value(role.getId());
            out.name("name").value(role.getName());
            out.endObject();
        }
        out.endArray();

        out.name("beneficioEmpleado").value(value.getBeneficioEmpleado());
        out.name("horaEntrada").value(value.getHoraEntrada().toString());
        out.name("horaSalida").value(value.getHoraSalida().toString());
        out.name("salario").value(value.getSalario().toString());
        out.endObject();
    }

    @Override
    public EmployeeDTO read(JsonReader in) throws IOException {
        JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();

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