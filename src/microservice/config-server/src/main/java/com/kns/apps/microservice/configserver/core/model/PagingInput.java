package com.kns.apps.microservice.configserver.core.model;

import com.kns.apps.microservice.configserver.application.helper.FilterHelper;
import com.kns.apps.microservice.configserver.core.constant.PagingConst;
import com.kns.apps.microservice.configserver.core.model.dataTables.Column;
import com.kns.apps.microservice.configserver.core.model.dataTables.DataTablesInput;
import com.kns.apps.microservice.configserver.core.model.dataTables.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

@Getter
@Setter
@AllArgsConstructor
public class PagingInput {

    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortDir;
    private FilterInput filter;

    public PagingInput(DataTablesInput input) {
        this.pageNo = input.getStart() != null ? (input.getStart() < input.getLength() ? PagingConst.DEFAULT_PAGE_NUMBER : input.getStart() / input.getLength()) : PagingConst.DEFAULT_PAGE_NUMBER;
        this.pageSize = input.getLength() != null ? input.getLength() : PagingConst.DEFAULT_PAGE_SIZE;
        this.filter = FilterHelper.handleDataTablesMappingFilter(input);

        Order order = input.getOrder().get(0);
        if (order == null) {
            this.sortBy = "id";
            this.sortDir = "desc";
        } else {
            Column orderCol = input.getColumns().get(order.getColumn());
            this.sortBy = orderCol.getData();
            this.sortDir = order.getDir();
        }

        if (input.getFilter() != null) {
            if (StringUtils.isNotBlank(input.getFilter().getFromDate())) {
                this.getFilter().setFromDate(input.getFilter().getFromDate());
            }
            if (StringUtils.isNotBlank(input.getFilter().getToDate())) {
                this.getFilter().setToDate(input.getFilter().getToDate());
            }
        }
    }


}
