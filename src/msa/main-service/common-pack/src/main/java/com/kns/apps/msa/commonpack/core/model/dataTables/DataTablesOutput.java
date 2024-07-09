package com.kns.apps.msa.commonpack.core.model.dataTables;

import com.kns.apps.msa.commonpack.core.model.PagingOutput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataTablesOutput<T> {

    private Integer draw;
    private Integer recordsFiltered;
    private Integer recordsTotal;
    private List<T> data;

    public DataTablesOutput(List<T> data) {
        this.data = data;
    }

    public DataTablesOutput(DataTablesInput input, PagingOutput output) {
        this.draw = input.getDraw();
        this.recordsFiltered = (int) output.getTotalElements();
        this.recordsTotal = (int) output.getTotalElements();
        this.data = output.getContent();
    }
}
