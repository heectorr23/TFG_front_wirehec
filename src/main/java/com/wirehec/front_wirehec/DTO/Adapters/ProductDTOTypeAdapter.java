package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.ProductDTO;

import java.io.IOException;

public class ProductDTOTypeAdapter extends TypeAdapter<ProductDTO> {
    @Override
    public void write(JsonWriter out, ProductDTO value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("nombreProducto").value(value.getNombreProducto());
        out.name("categoriaProducto").value(value.getCategoriaProducto());
        out.name("precioVenta").value(value.getPrecioVenta());
        out.name("stock").value(value.getStock());
        out.name("precioCoste").value(value.getPrecioCoste());
        out.endObject();
    }

    @Override
    public ProductDTO read(JsonReader in) throws IOException {
        return null;
    }
}
