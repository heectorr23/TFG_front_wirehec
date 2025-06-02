package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import com.wirehec.front_wirehec.DTO.CustomerDTO;

public class CustomerDTOTypeAdapter extends TypeAdapter<CustomerDTO> {
    @Override
    public void write(JsonWriter out, CustomerDTO value) throws IOException {
        out.beginObject();
        if (value.getId() != null) {
            out.name("id").value(value.getId());
        }
        out.name("name").value(value.getName());
        out.name("contacto").value(value.getContacto());
        out.name("telefono").value(value.getTelefono());
        // Incluir identificacion solo si no es nula
        if (value.getIdentificacion() != null) {
            out.name("identificacion").value(value.getIdentificacion());
        }
        out.name("email").value(value.getEmail());
        out.name("zona").value(value.getZona());
        out.name("direccion").value(value.getDireccion());
        out.endObject();
    }

    @Override
    public CustomerDTO read(JsonReader in) throws IOException {
        return null;
    }
}