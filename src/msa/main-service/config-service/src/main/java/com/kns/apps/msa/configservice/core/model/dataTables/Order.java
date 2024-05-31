package com.kns.apps.msa.configservice.core.model.dataTables;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    private Integer column;
    private String dir;
}
