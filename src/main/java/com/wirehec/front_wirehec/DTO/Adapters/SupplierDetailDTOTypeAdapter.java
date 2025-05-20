package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.io.IOException;

public class SupplierDetailDTOTypeAdapter extends TypeAdapter<SupplierDetailDTO> {
    @Override
    public void write(JsonWriter out, SupplierDetailDTO value) throws IOException {
        out.beginObject();
        out.name("idDetalleProveedor").value(value.getIdDetalleProveedor());
        out.name("supplier");
        if (value.getSupplier() != null) {
            out.beginObject();
            out.name("idProveedor").value(value.getSupplier().getIdProveedor());
            out.name("nombreProveedor").value(value.getSupplier().getNombreProveedor());
            out.name("cifProveedor").value(value.getSupplier().getCifProveedor());
            out.name("emailProveedor").value(value.getSupplier().getEmailProveedor());
            out.name("categoriaProveedor").value(value.getSupplier().getCategoriaProveedor());
            out.name("productoProveedor").value(value.getSupplier().getProductoProveedor());
            out.endObject();
        } else {
            out.nullValue();
        }
        out.name("supplierOrders");
        if (value.getSupplierOrders() != null) {
            out.beginArray();
            for (SupplierOrderDTO order : value.getSupplierOrders()) {
                out.beginObject();
                out.name("idPedidoProveedor").value(order.getIdPedidoProveedor());
                out.name("FechaPedido").value(order.getFechaPedido().toString());
                out.name("FechaEntrega").value(order.getFechaEntrega().toString());
                out.endObject();
            }
            out.endArray();
        } else {
            out.nullValue();
        }
        out.endObject();
    }

    @Override
    public SupplierDetailDTO read(JsonReader in) throws IOException {
        return null;
    }
}
