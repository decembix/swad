public class Payment {

    // 입력은 Kiosk에서 하고, 이 메서드는 단순히 결제만 수행
    public boolean processPayment(int amount) {
        System.out.printf("\n총 %d원을 결제합니다...\n", amount);
        return true;
    }

    //카드번호 검사(8자리)
    public boolean isValidCardNumber(String cardNumber) {
    return cardNumber.matches("\\d{8}");
    }

    //카드 유효기간 검사
    public boolean isValidExpiry(String expiry) {
        // MM/YY 확인
     if (!expiry.matches("\\d{2}/\\d{2}")) {
        return false;
    }

    String[] parts = expiry.split("/");
    int month = Integer.parseInt(parts[0]);
    int year = Integer.parseInt(parts[1]);

    // 월이 유효하지 않거나 연도가 25보다 작은 경우 실패
    if (month < 1 || month > 12 || year < 25) {
        return false;
    }

    // 25년이면 6월보다 작을 경우 실패
    if (year == 25 && month < 6) {
        return false;
    }

    return true;
    }
}
