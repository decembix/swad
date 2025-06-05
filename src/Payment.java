import java.util.Scanner;

public class Payment {
    private Scanner scanner = new Scanner(System.in);

    public boolean processPayment(int amount) {
        Coupon coupon = new Coupon();

        System.out.printf("총 %d원을 결제합니다...\n", amount);

        while (true) {
            System.out.print("쿠폰을 사용하시겠습니까? (Y/N): ");
            String useCoupon = scanner.nextLine();

            if (useCoupon.equalsIgnoreCase("Y")) {
                System.out.print("쿠폰 코드를 입력하세요: ");
                int code = Integer.parseInt(scanner.nextLine());

                int discount = coupon.CouponAvailable(code);
                if (discount > 0) {
                    System.out.printf("✅ 쿠폰이 적용되어 %d원이 할인됩니다.\n", discount);
                    amount -= discount;
                    break;
                } else {
                    System.out.println("❌ 유효하지 않은 쿠폰입니다.");
                }
            } else if (useCoupon.equalsIgnoreCase("N")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N을 입력하세요.");
            }
        }

        System.out.printf("💳 최종 결제 금액: %d원\n", amount);
        System.out.print("결제하시겠습니까? (Y/N): ");
        String totalPayment = scanner.nextLine();

        if (totalPayment.equalsIgnoreCase("Y")) {
            System.out.println("✅ 결제가 완료되었습니다.");
            return true;  // 결제 성공
        } else {
            System.out.println("결제가 취소되었습니다.");
            System.out.println("🛍️ 이용해주셔서 감사합니다.");
            return false;  // 결제 취소 → 상위에서 메뉴 초기화 등 처리
        }
    }
}
