package vn.com.kns.portalapi.core.constant;

public enum ShopOrderStepConst {
    INIT(0),
    SENT(1),
    CONFIRM(2),
    PREPARING(3),
    DELIVERING(4),
    RECEIVED(5),
    IMPORTED(6),
    ;

    private final int status;

    ShopOrderStepConst(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.valueOf(getStatus());
    }
}
