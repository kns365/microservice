package vn.com.kns.portalapi.core.model.dataTables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.kns.portalapi.core.model.PagingOutput;

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
