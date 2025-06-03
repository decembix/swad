import java.util.Arrays;
import java.util.List;

public class Coupon {
    private final List<String> validCodes = Arrays.asList("burger", "drink");

    // 쿠폰 코드가 유효한지 검사
    public boolean isValid(String code) {
        return validCodes.contains(code.toLowerCase());
    }
}