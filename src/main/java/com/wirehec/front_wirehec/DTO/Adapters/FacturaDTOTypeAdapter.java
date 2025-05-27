package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.FacturaDTO;

import java.io.IOException;
import java.math.BigDecimal;

public class FacturaDTOTypeAdapter extends TypeAdapter<FacturaDTO> {

    @Override
    public void write(JsonWriter out, FacturaDTO value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("precio").value(value.getPrecio());
        out.name("zona").value(value.getZona());
        out.name("direccion").value(value.getDireccion());
        out.endObject();
    }

    @Override
    public FacturaDTO read(JsonReader in) throws IOException {
        FacturaDTO factura = new FacturaDTO();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    factura.setId(in.nextLong());
                    break;
                case "precio":
                    factura.setPrecio(new BigDecimal(in.nextString()));
                    break;
                case "zona":
                    factura.setZona(in.nextString());
                    break;
                case "direccion":
                    factura.setDireccion(in.nextString());
                    break;
            }
        }
        in.endObject();
        return factura;
    }
}