package vn.com.kns.portalapi.core.model.dataTables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.kns.portalapi.core.model.FilterInput;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DataTablesInput {
    private Integer draw;
    private Integer start;
    private Integer length;
    private List<Order> order;
    private List<Column> columns;
    private Search search;
    private FilterInput filter;
}
