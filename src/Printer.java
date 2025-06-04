import java.util.Map;

public class Printer {
    public void printReceipt(String method, String couponCode, String cardNumber, String expiry, Cart cart) {
        System.out.println("\n========== [영수증] ==========");
        System.out.println("결제 방식: " + method);

        if (couponCode != null) {
            System.out.println("쿠폰 코드: " + couponCode);
        }

        if (cardNumber != null) {
            System.out.println("카드번호: " + cardNumber);
        }

        if (expiry != null) {
            System.out.println("유효기간: " + expiry);
        }

        System.out.println("\n[주문 내역]");
        for (Map.Entry<MenuItem, Integer> entry : cart.getCartList().entrySet()) {
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("- %s %d개\n", item.getName(), quantity);
        }

        System.out.printf("\n총 결제 금액: %d원\n", cart.getTotalPrice());
        System.out.println("================================\n");
    }
}