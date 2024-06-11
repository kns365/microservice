package vn.com.kns.portalapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.kns.portalapi.application.helper.FilterHelper;
import vn.com.kns.portalapi.core.model.dataTables.Column;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.Order;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterInput {//edit FilterHelper

    private FilterHelper filterHelper;

    private String fromDate;
    private String toDate;

    private String name;
    private String code;

    //user
    private Long id;
    private String username;
    private String fullName;
    private String email;

    //auditLog
    private String createdBy;
    private String serviceName;
    private String methodName;
    private Integer execDuration;
    private String clientIpAddress;
    private String clientName;
    private String browserInfo;
    private String path;
    private String httpStatus;
    private String exception;
//    private String input;
//    private String output;

    private String country; //countryName
    private String province; //provinceName
    private String district; //districtName

    private String groupItem; //groupItemName
    private String item; //itemName
    private String unit; //unitName
    private Long price;
    private Integer quantity;
    private String supplier;//supplierName
    private Long supplierId;

    private String shop;//shopName
    private Long shopId;
    private String barcode;

    private String shopImport;
    private String shopOrder;

    private Integer step;


}
