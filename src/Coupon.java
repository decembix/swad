public class Coupon {
    private int discount = 1230;

    // 쿠폰 코드 입력받아 할인액 반환
    public int CouponAvailable(int code) {
        if (code > 10000 && code < 30000 && code % 1234 == 0) {//1234 배수이며 10000-30000사이인 값을 코드로 설정
            return discount;
        } else {
            return 0;
        }
    }
}
