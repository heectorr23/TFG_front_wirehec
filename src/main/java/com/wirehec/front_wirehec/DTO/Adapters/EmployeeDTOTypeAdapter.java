package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;

public class EmployeeDTOTypeAdapter extends TypeAdapter<EmployeeDTO> {
    @Override
    public void write(JsonWriter out, EmployeeDTO value) throws IOException {
        out.beginObject();
        out.name("nombreEmpleado").value(value.getNombreEmpleado());
        out.name("nifEmpleado").value(value.getNifEmpleado());
        out.name("telefonoEmpleado").value(value.getTelefonoEmpleado());
        out.name("email").value(value.getEmail());
        out.name("username").value(value.getUsername());
        out.name("passwordEmpleado").value(value.getPasswordEmpleado());

        out.name("roles").beginArray();
        for (RoleDTO role : value.getRoles()) {
            out.beginObject();
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
        return null;
    }
}
