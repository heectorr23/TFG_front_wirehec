package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.SupplierDTO;

import java.io.IOException;

public class SupplierDTOTypeAdapter extends TypeAdapter<SupplierDTO> {
    @Override
    public void write(JsonWriter out, SupplierDTO value) throws IOException {
        out.beginObject();
        out.name("idProveedor").value(value.getIdProveedor());
        out.name("nombreProveedor").value(value.getNombreProveedor());
        out.name("cifProveedor").value(value.getCifProveedor());
        out.name("emailProveedor").value(value.getEmailProveedor());
        out.name("categoriaProveedor").value(value.getCategoriaProveedor());
        out.name("productoProveedor").value(value.getProductoProveedor());
        out.endObject();
    }

    @Override
    public SupplierDTO read(JsonReader in) throws IOException {
        return null;
    }
}
