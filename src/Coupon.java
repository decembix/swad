import java.util.*;

public class Coupon {
    private Map<String, String> validCodes;

    public Coupon() {
        validCodes = new HashMap<>();
        validCodes.put("burger", "버거");
        validCodes.put("drink", "음료");
        validCodes.put("setmenu", "세트");
    }

    public boolean isValid(String code) {
        return validCodes.containsKey(code.toLowerCase());
    }

    public String getCategoryForCoupon(String code) {
        return validCodes.get(code.toLowerCase());
    }
}
