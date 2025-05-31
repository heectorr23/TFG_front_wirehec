package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SupplierDetailDTOTypeAdapter extends TypeAdapter<SupplierDetailDTO> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void write(JsonWriter out, SupplierDetailDTO value) throws IOException {
        out.beginObject();
        out.name("idDetalleProveedor").value(value.getIdDetalleProveedor());
        out.name("supplierEntity");
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
        out.name("supplierOrderEntities");
        if (value.getSupplierOrders() != null) {
            out.beginArray();
            for (SupplierOrderDTO order : value.getSupplierOrders()) {
                out.beginObject();
                out.name("idPedidoProveedor").value(order.getIdPedidoProveedor());
                out.name("fechaPedido").value(order.getFechaPedido() != null ? order.getFechaPedido().format(DATE_FORMAT) : null);
                out.name("fechaEntrega").value(order.getFechaEntrega() != null ? order.getFechaEntrega().format(DATE_FORMAT) : null);
                out.endObject();
            }
            out.endArray();
        } else {
            out.nullValue();
        }
        out.name("idProduct").value(value.getIdProduct());
        out.endObject();
    }

    @Override
    public SupplierDetailDTO read(JsonReader in) throws IOException {
        SupplierDetailDTO supplierDetailDTO = new SupplierDetailDTO();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "idDetalleProveedor":
                    supplierDetailDTO.setIdDetalleProveedor(in.nextLong());
                    break;
                case "supplierEntity":
                    if (in.peek() != JsonToken.NULL) {
                        SupplierDTO supplier = new SupplierDTO();
                        in.beginObject();
                        while (in.hasNext()) {
                            String supplierField = in.nextName();
                            switch (supplierField) {
                                case "idProveedor":
                                    supplier.setIdProveedor(in.nextLong());
                                    break;
                                case "nombreProveedor":
                                    supplier.setNombreProveedor(in.nextString());
                                    break;
                                case "cifProveedor":
                                    supplier.setCifProveedor(in.nextInt());
                                    break;
                                case "emailProveedor":
                                    supplier.setEmailProveedor(in.nextString());
                                    break;
                                case "categoriaProveedor":
                                    supplier.setCategoriaProveedor(in.nextString());
                                    break;
                                case "productoProveedor":
                                    supplier.setProductoProveedor(in.nextString());
                                    break;
                                default:
                                    in.skipValue();
                                    break;
                            }
                        }
                        in.endObject();
                        supplierDetailDTO.setSupplier(supplier);
                    } else {
                        in.nextNull();
                    }
                    break;
                case "supplierOrderEntities":
                    if (in.peek() != JsonToken.NULL) {
                        List<SupplierOrderDTO> orders = new ArrayList<>();
                        in.beginArray();
                        while (in.hasNext()) {
                            SupplierOrderDTO order = new SupplierOrderDTO();
                            in.beginObject();
                            while (in.hasNext()) {
                                String orderField = in.nextName();
                                switch (orderField) {
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
                            orders.add(order);
                        }
                        in.endArray();
                        supplierDetailDTO.setSupplierOrders(orders);
                    } else {
                        in.nextNull();
                    }
                    break;
                case "idProduct":
                    supplierDetailDTO.setIdProduct(in.nextLong());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return supplierDetailDTO;
    }

    private LocalDate parseDate(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return LocalDate.parse(in.nextString(), DATE_FORMAT);
    }
}