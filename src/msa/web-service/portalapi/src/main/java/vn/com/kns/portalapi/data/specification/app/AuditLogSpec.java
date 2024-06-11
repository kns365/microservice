package vn.com.kns.portalapi.data.specification.app;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.application.helper.DateHelper;
import vn.com.kns.portalapi.core.constant.PagingConst;
import vn.com.kns.portalapi.core.entity.app.AuditLog;
import vn.com.kns.portalapi.core.entity.app.AuditLog_;
import vn.com.kns.portalapi.core.model.FilterInput;

import java.util.Date;

public class AuditLogSpec {

    private AuditLogSpec() {
        //empty
    }

    public static Specification<AuditLog> filterBy(FilterInput f) {
        Date minFromDate = DateHelper.getMinDay("yyyyMMdd", f.getFromDate());
        Date maxToDate = DateHelper.getMaxDay("yyyyMMdd", f.getToDate());
        if (minFromDate == null) {
            minFromDate = DateHelper.getMinDay(DateHelper.getPastDate(PagingConst.DEFAULT_PAST_DAY));
        }
        if (maxToDate == null) {
            maxToDate = DateHelper.getMaxDay();
        }
        return Specification// 1 input filter use OR, multi input filter use AND
                .where(createdDateBetween(minFromDate, maxToDate))
                .and(hasId(f.getId()))
                .and(createByLike(f.getCreatedBy()))
                .and(hasExecDurationGreaterThanOrEqualTo(f.getExecDuration()))
                .and(clientIpAddressLike(f.getClientIpAddress()))
                .and(clientNameLike(f.getClientName()))
                .and(pathLike(f.getPath()))
                .and(httpStatusLike(f.getHttpStatus()))
//                .and(sasMasterData_roNameLike(f.getRoName()))
//                .and(sasMasterData_loanStatusLike(f.getLoanStatus()))
//                .and(hasStep(f.getStep()))
//                .and(hasAuditLogAuditLogId(f.getAuditLogAuditLogId()))
//                .and(allocateAuditLog_fullNameLike(f.getAuditLogAuditLogFullName()))
                ;
    }

    private static Specification<AuditLog> createdDateBetween(Date fromDate, Date toDate) {
        return (root, query, cb) -> (fromDate == null || toDate == null) ? cb.conjunction() : cb.between(root.get(AuditLog_.createdDate), fromDate, toDate);
    }

    private static Specification<AuditLog> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(AuditLog_.id), val);
    }

    private static Specification<AuditLog> createByLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(AuditLog_.createdBy)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<AuditLog> hasExecDurationGreaterThanOrEqualTo(Integer val) {
        return ((root, query, cb) -> val == null || val <= 0 ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get(AuditLog_.execDuration), val));
    }

    private static Specification<AuditLog> clientIpAddressLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(AuditLog_.clientIpAddress)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<AuditLog> clientNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(AuditLog_.clientName)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<AuditLog> pathLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(AuditLog_.path)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<AuditLog> httpStatusLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(AuditLog_.httpStatus)), "%" + val.toLowerCase() + "%"));
    }

}

   
