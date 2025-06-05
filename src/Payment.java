import java.util.Scanner;

public class Payment {
    private Scanner scanner = new Scanner(System.in);

    public boolean processPayment(int amount) {
        Coupon coupon = new Coupon();

        System.out.printf("결제할 금액 : %d원\n", amount);

        // 쿠폰 사용 여부
        while (true) {
            System.out.print("쿠폰을 사용하시겠습니까? (Y/N): ");
            String useCoupon = scanner.nextLine();

            if (useCoupon.equalsIgnoreCase("Y")) {
                System.out.print("쿠폰 코드를 입력하세요: ");
                int code = Integer.parseInt(scanner.nextLine());

                int discount = coupon.CouponAvailable(code);
                if (discount > 0) {
                    System.out.printf("쿠폰이 적용되어 %d원이 할인됩니다.\n", discount);
                    amount -= discount;
                    break;
                } else {
                    System.out.println("유효하지 않은 쿠폰입니다.");
                    // 다시 쿠폰 여부를 묻는 루프로 돌아감
                }
            } else if (useCoupon.equalsIgnoreCase("N")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N을 입력하세요.");
            }
        }

        System.out.printf("최종 결제 금액: %d원\n", amount);
        System.out.print("결제하시겠습니까? (Y/N): ");
        String totalPayment = scanner.nextLine();

        if (!totalPayment.equalsIgnoreCase("Y")) {
            System.out.println("결제가 취소되었습니다.");
            System.out.println("이용해주셔서 감사합니다.");
            return false;
        }

        // 카드 결제 정보 입력
        System.out.print("카드번호(8자리)를 입력하세요: ");
        String cardInput = scanner.nextLine();
        if (!isValidCardNumber(cardInput)) {
            System.out.println("유효하지 않은 카드번호입니다.");
            return false;
        }

        System.out.print("카드 유효기간을 입력하세요 (MM/YY): ");
        String expiry = scanner.nextLine();
        if (!isValidExpiry(expiry)) {
            System.out.println("유효하지 않은 유효기간입니다.");
            return false;
        }

        System.out.println("결제가 완료되었습니다.");
        return true;
    }

    // 카드번호 유효성 검사 (8자리 숫자)
    public boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{8}");
    }

    // 카드 유효기간 검사 (MM/YY 형식, 25년 6월 이상)
    public boolean isValidExpiry(String expiry) {
        if (!expiry.matches("\\d{2}/\\d{2}")) {
            return false;
        }

        String[] parts = expiry.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        if (month < 1 || month > 12 || year < 25) {
            return false;
        }

        if (year == 25 && month < 6) {
            return false;
        }

        return true;
    }
}
