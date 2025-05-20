package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.FacturaDTO;

import java.io.IOException;

public class FacturaDTOTypeAdapter extends TypeAdapter<FacturaDTO> {

    @Override
    public void write(JsonWriter out, FacturaDTO value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("Precio").value(value.getPrecio());
        out.name("Zona").value(value.getZona());
        out.name("Direccion").value(value.getDireccion());
        out.endObject();
    }

    @Override
    public FacturaDTO read(JsonReader in) throws IOException {
        return null;
    }
}
