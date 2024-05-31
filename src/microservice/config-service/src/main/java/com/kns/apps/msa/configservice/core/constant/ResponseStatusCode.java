package com.kns.apps.msa.configservice.core.constant;

public enum ResponseStatusCode {
    FAILURE(-1),
    SUCCESS(0),//Truy vấn, thanh toán thành công
    SUCCESS_200(200),//Truy vấn, thanh toán thành công
    PAID(1),//Khách hàng/Hóa đơn tồn tại nhưng đã hết nợ
    EXPIRED(2),//Kết nối hết hạn, hạn chế truy cập về IP
    INVALID_AMOUNT(3),//Số tiền gạch nợ không hợp lệ
    USER_NOT_FOUND(4),//SVFC tự định nghĩa
    MISSING_FIELD(10),//Sai cấu trúc Message truyền/nhận
    OVER_LIMIT(11),//Số lần truy vấn/gạch nợ vượt quá ngưỡng quy định
    INVALID_USER(17),//Mã Khách hàng/Hóa đơn không hợp lệ
    INVALID_SIGNATURE(18),//Chữ ký không hợp lệ
    FIELD_FORMAT_ERROR(20),//Format của các trường thông tin không hợp lệ (VD: định dạng số, ngày tháng năm …)
    FIELD_VALUE_ERROR(21),//Giá trị các trường thông tin không hợp lệ
    UNKNOWN_NODE_ERROR(22),//Message truyền nhận có trường thông tin không có trên Specs
    ACCOUNT_BALANCE_ERROR(68),//Truy vấn thông tin dư nợ không thành công
    VALIDATION_ERROR(90),//Mã lỗi chung khi validate message không thành công, không thực hiện xử lý
    EXCEPTION(99),//Mã lỗi không tường minh khi xử lý truy vấn/gạch nợ gặp Unhandle Exception
    BAD_REQUEST(400),//Mã lỗi chung khi xử lý giao dịch thất bại tường minh
    TIMEOUT(999),//Mã lỗi không tường minh khi kết nối giữa các hệ thống gặp Timed Out

    DUPLICATE_DOCUMENT_REGISTRATION_NUMBER(77),//Lỗi Svfc response
    PAYMENT_AMOUNT_NOT_VALID(76),//Lỗi Svfc response, VCB cung cấp sau
    CARD_NUMBER_NOT_ON_FILE(14),//Lỗi Svfc response

    INTERNAL_SERVER_ERROR(500),
    DO_NOT_PAYMENT_CARD_16_NUMBER(99),
    CONTRACT_NOT_FOUND(2263),//CARD BalanceInquiry response
    LOAN_IS_EXPIRED(15),//LOAN getCustInfoByLoanNo response
    LOAN_IS_NOT_FOUND(11),//LOAN getCustInfoByLoanNo response


    ;

    private final int val;

    ResponseStatusCode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(getVal());
    }


}
