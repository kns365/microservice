package com.kns.apps.microservice.configserver.application.helper;

import com.kns.apps.microservice.configserver.core.model.FilterInput;
import com.kns.apps.microservice.configserver.core.model.dataTables.Column;
import com.kns.apps.microservice.configserver.core.model.dataTables.DataTablesInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class FilterHelper {

    public static FilterInput handleDataTablesMappingFilter(DataTablesInput input) {
        FilterInput f = new FilterInput();
        for (Column col : input.getColumns()) {
            if (col.getSearch() == null || StringUtils.isEmpty(col.getSearch().getValue())) {
                continue;
            }
            String val = col.getSearch().getValue();
            switch (col.getData()) {
                case "name":
                    f.setName(val);
                    break;
                case "code":
                    f.setCode(val);
                    break;
                case "id":
                    f.setId(Long.valueOf(val));
                    break;
                case "username":
                    f.setUsername(val);
                    break;
                case "fullName":
                    f.setFullName(val);
                    break;
                case "email":
                    f.setEmail(val);
                    break;

                case "createdBy":
                    f.setCreatedBy(val);
                    break;
                case "serviceName":
                    f.setServiceName(val);
                    break;
                case "methodName":
                    f.setMethodName(val);
                    break;
                case "execDuration":
                    f.setExecDuration(Integer.valueOf(val));
                    break;
                case "clientIpAddress":
                    f.setClientIpAddress(val);
                    break;
                case "clientName":
                    f.setClientName(val);
                    break;
                case "browserInfo":
                    f.setBrowserInfo(val);
                    break;
                case "path":
                    f.setPath(val);
                    break;
                case "httpStatus":
                    f.setHttpStatus(val);
                    break;

                case "province":
                    f.setProvince(val);
                    break;
                case "country":
                    f.setCountry(val);
                    break;

                case "groupItem":
                    f.setGroupItem(val);
                    break;
                case "item":
                case "supply.item":
                    f.setItem(val);
                    break;
                case "unit":
                    f.setUnit(val);
                    break;
                case "price":
                    f.setPrice(Long.valueOf(val));
                    break;
                case "quantity":
                    f.setQuantity(Integer.valueOf(val));
                    break;

                case "supplier":
                    f.setSupplier(val);
                    break;
                case "supplierId":
                    f.setSupplierId(Long.valueOf(val));
                    break;

                case "shop":
                    f.setShop(val);
                    break;
                case "shopId":
                    f.setShopId(Long.valueOf(val));
                    break;

                case "barcode":
                case "supply.barcode":
                    f.setBarcode(val);
                    break;

                case "shopImport.shopOrder.code":
                    f.setShopOrder(val);
                    break;

                case "shopImport.code":
                    f.setShopImport(val);
                    break;

                case "step":
                    f.setStep(Integer.valueOf(val));
                    break;

                default:
                    break;
            }
        }
        return f;
    }

}
