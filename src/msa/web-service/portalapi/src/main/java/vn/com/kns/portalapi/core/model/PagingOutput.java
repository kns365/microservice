package vn.com.kns.portalapi.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingOutput<T> {

    private int pageNo;
    private int pageSize;
    private long totalFilters;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private List<T> content;

    public PagingOutput(List<T> content) {
        this.content = content;
    }
}
