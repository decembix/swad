public class Coupon {
    private int discount = 1230;

    // 쿠폰 코드 입력받아 할인액 반환
    public int CouponAvailable(int code) {
        if (code > 10000 && code < 30000 && code % 1234 == 0) {
            return discount;
        } else {
            return 0;
        }
    }
}
