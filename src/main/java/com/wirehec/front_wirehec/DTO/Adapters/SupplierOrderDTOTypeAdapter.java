package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.io.IOException;

public class SupplierOrderDTOTypeAdapter extends TypeAdapter<SupplierOrderDTO> {
    @Override
    public void write(JsonWriter out, SupplierOrderDTO value) throws IOException {
        out.beginObject();
        out.name("idPedidoProveedor").value(value.getIdPedidoProveedor());
        out.name("FechaPedido").value(value.getFechaPedido().toString());
        out.name("FechaEntrega").value(value.getFechaEntrega().toString());
        out.endObject();
    }

    @Override
    public SupplierOrderDTO read(JsonReader in) throws IOException {
        return null;
    }
}

