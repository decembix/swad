import java.util.Scanner;

public class Kiosk {
    private Screen screen;
    private Cart cart;
    private MenuList menuList;
    private Payment payment;
    private Scanner scanner;

    public Kiosk() {
        screen = new Screen();
        cart = new Cart();
        menuList = new MenuList();
        payment = new Payment();
        scanner = new Scanner(System.in);
    }

    public void start() {
        screen.displayInitialScreen();
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("Y")) {
            clickMenuButton();
        } else {
            System.out.println("이용해 주셔서 감사합니다.");
            cleanup();
        }
    }

    public void clickMenuButton() {
        while (true) {
            screen.displayMenu(menuList);
            System.out.print("주문할 음료수 번호: ");
            String input = scanner.nextLine();
            MenuItem selected = menuList.getMenuItemByNumber(input);
            if (selected != null) {
                cart.addItem(selected);
            } else {
                System.out.println("잘못된 선택입니다.");
            }

            System.out.print("더 주문하시겠습니까?(Y/N): ");
            String more = scanner.nextLine();
            if (!more.equalsIgnoreCase("Y")) break;
        }
        clickPaymentButton();
    }

    public void clickPaymentButton() {
        screen.displayFinalAmount(cart);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Y")) {
            onPaymentSuccess();
        } else {
            onPaymentFailure();
        }
    }

    public void onPaymentSuccess() {
        payment.processPayment(cart.getTotalPrice());
        System.out.println("결제가 완료되었습니다. 이용해 주셔서 감사합니다.");
        cleanup();
    }

    public void onPaymentFailure() {
        System.out.println("결제가 취소되었습니다. 초기화면으로 돌아갑니다.");
        cart.clearCart();
        start();
    }

    private void cleanup() {
        scanner.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.start();
    }
}
