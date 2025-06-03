public class Payment {

    // 입력은 Kiosk에서 하고, 이 메서드는 단순히 결제만 수행
    public boolean processPayment(int amount) {
        System.out.printf("\n총 %d원을 결제합니다...\n", amount);
        return true;
    }
}
