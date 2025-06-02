public class Payment {

    // 입력은 Kiosk에서 하고, 이 메서드는 단순히 결제만 수행
    public boolean processPayment(int amount) {
        System.out.printf("총 %d원을 결제합니다...\n", amount);
        System.out.printf("쿠폰을 사용하시겠습니까?\n");
        System.out.println("결제가 완료되었습니다.");
        return true;
    }
}
