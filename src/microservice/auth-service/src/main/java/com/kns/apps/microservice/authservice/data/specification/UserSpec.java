package com.kns.apps.microservice.authservice.data.specification;

import com.kns.apps.microservice.authservice.core.entity.User;
import com.kns.apps.microservice.authservice.core.entity.User_;
import com.kns.apps.microservice.configserver.application.helper.DateHelper;
import com.kns.apps.microservice.configserver.core.constant.PagingConst;
import com.kns.apps.microservice.configserver.core.model.FilterInput;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class UserSpec {

    private UserSpec() {
        //empty
    }

    public static Specification<User> filterBy(FilterInput f) {
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
                .and(usernameLike(f.getUsername()))
                .and(fullNameLike(f.getFullName()))
                .and(emailLike(f.getEmail()))
//                .and(sasMasterData_appIdLike(f.getAppId()))
//                .and(sasMasterData_productCodeLike(f.getProductCode()))
//                .and(sasMasterData_roBranchLike(f.getRoBranch()))
//                .and(sasMasterData_roIdLike(f.getRoId()))
//                .and(sasMasterData_roNameLike(f.getRoName()))
//                .and(sasMasterData_loanStatusLike(f.getLoanStatus()))
//                .and(hasStep(f.getStep()))
//                .and(hasUserUserId(f.getUserUserId()))
//                .and(allocateUser_fullNameLike(f.getUserUserFullName()))
                ;
    }

    private static Specification<User> createdDateBetween(Date fromDate, Date toDate) {
        return (root, query, cb) -> (fromDate == null || toDate == null) ? cb.conjunction() : cb.between(root.get(User_.createdDate), fromDate, toDate);
    }

    private static Specification<User> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(User_.id), val);
    }

    private static Specification<User> usernameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.username)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<User> fullNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.fullName)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<User> emailLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.email)), "%" + val.toLowerCase() + "%"));
    }

    // and(A or B)
//    private static Specification<Finding> hasfindingUserIdOrAllocateUserId(Long valA, Long valB) {
//        return (root, query, cb) -> (valA == null && valB == null) ? cb.conjunction() : cb.or(cb.equal(root.get(Finding_.findingUserId), valA), cb.equal(root.get(Finding_.allocateUserId), valB));
//    }

    //
//    private static Specification<User> hasUserUserId(Long val) {
//        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(User_.allocateUserId), val);
//    }
//
//
//    private static Specification<User> sasMasterData_batchIdLike(String val) {
//        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.sasMasterData).get(SasMasterData_.batchId)), "%" + val.toLowerCase() + "%"));
//    }


//    private static Specification<User> hasFuelType(FuelType fuelType) {
//        return (root, query, cb) -> fuelType == null ? cb.conjunction() : cb.equal(root.get(FUEL_TYPE), fuelType);
//    }
//    private static Specification<User> hasNumberOfDoors(Integer numberOfDoors) {
//        return (root, query, cb) -> numberOfDoors == null ? cb.conjunction() : cb.equal(root.get(NUMBER_OF_DOORS), numberOfDoors);
//    }
//    private static Specification<User> hasPriceGreaterThan(BigDecimal priceFrom) {
//        return (root, query, cb) -> priceFrom == null ? cb.conjunction() : cb.greaterThan(root.get(PRICE), priceFrom);
//    }
//    private static Specification<User> hasPriceLessThan(BigDecimal priceTo) {
//        return (root, query, cb) -> priceTo == null ? cb.conjunction() : cb.lessThan(root.get(PRICE), priceTo);
//    }
}

   
