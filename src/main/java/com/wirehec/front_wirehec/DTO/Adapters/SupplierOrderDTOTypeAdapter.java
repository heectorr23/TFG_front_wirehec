package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SupplierOrderDTOTypeAdapter extends TypeAdapter<SupplierOrderDTO> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void write(JsonWriter out, SupplierOrderDTO value) throws IOException {
        out.beginObject();
        out.name("idPedidoProveedor").value(value.getIdPedidoProveedor());
        out.name("fechaPedido").value(value.getFechaPedido() != null ? value.getFechaPedido().format(DATE_FORMAT) : null);
        out.name("fechaEntrega").value(value.getFechaEntrega() != null ? value.getFechaEntrega().format(DATE_FORMAT) : null);
        out.endObject();
    }

    @Override
    public SupplierOrderDTO read(JsonReader in) throws IOException {
        SupplierOrderDTO order = new SupplierOrderDTO();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "idPedidoProveedor":
                    order.setIdPedidoProveedor(in.nextLong());
                    break;
                case "fechaPedido":
                    order.setFechaPedido(parseDate(in));
                    break;
                case "fechaEntrega":
                    order.setFechaEntrega(parseDate(in));
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return order;
    }

    private LocalDate parseDate(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return LocalDate.parse(in.nextString(), DATE_FORMAT);
    }
}