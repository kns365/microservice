package com.kns.apps.msa.configservice.core.model.dataTables;

import com.kns.apps.msa.configservice.core.model.FilterInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
