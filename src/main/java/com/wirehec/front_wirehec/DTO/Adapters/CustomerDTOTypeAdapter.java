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
        out.name("name").value(value.getName());
        out.name("contacto").value(value.getContacto());
        out.name("telefono").value(value.getTelefono());
        out.name("identificacion").value(value.getIdentificacion());
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
