package vn.com.kns.portalapi.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import vn.com.kns.portalapi.application.helper.FilterHelper;
import vn.com.kns.portalapi.core.constant.PagingConst;
import vn.com.kns.portalapi.core.model.dataTables.Column;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.Order;

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
