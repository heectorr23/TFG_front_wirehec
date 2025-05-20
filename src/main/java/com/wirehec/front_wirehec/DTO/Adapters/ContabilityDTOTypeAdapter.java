package com.wirehec.front_wirehec.DTO.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;

import java.io.IOException;

public class ContabilityDTOTypeAdapter extends TypeAdapter<ContabilityDTO> {
    @Override
    public void write(JsonWriter out, ContabilityDTO value) throws IOException {
        out.beginObject();
        out.name("idContabilidad").value(value.getIdContabilidad());
        out.name("presupuestomensual").value(value.getPresupuestomensual());
        out.name("beneficio").value(value.getBeneficio());
        out.name("gasto").value(value.getGasto());
        out.endObject();
    }

    @Override
    public ContabilityDTO read(JsonReader in) throws IOException {
        return null;
    }
}
