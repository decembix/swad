import java.util.*;

public class Coupon {
    private Map<String, String> validCodes;

    // 쿠폰
    public Coupon() {
        validCodes = new HashMap<>();
        validCodes.put("burger", "버거");
        validCodes.put("drink", "음료");
        validCodes.put("setmenu", "세트");
    }

    // 쿠폰 유효한지 확인
    public boolean isValid(String code) {
        return validCodes.containsKey(code.toLowerCase());
    }

    // 쿠폰을 해당하는 카테고리 이름으로 변환
    public String getCategoryForCoupon(String code) {
        return validCodes.get(code.toLowerCase());
    }
}
