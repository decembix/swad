import java.util.Scanner;

public class Kiosk {
    private Screen screen;
    private Cart cart;
    private MenuList menuList;
    private Payment payment;
    private Scanner scanner;
    private Admin admin;

    public Kiosk() {
        screen = new Screen();
        cart = new Cart();
        menuList = new MenuList();
        payment = new Payment();
        scanner = new Scanner(System.in);
        admin = new Admin(menuList);  // admin 연결
    }
    public void start() {
        screen.displayInitialScreen();
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("Y")) {
            clickMenuButton();
        } else if (input.equalsIgnoreCase("M")) {
            if (admin.isAdmin() == 1) {
                adminLoop(); // 관리자 모드 진입
            } else {
                System.out.println("관리자 인증에 실패했습니다.");
                start(); // 다시 초기화면으로
            }
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

            
            System.out.print("더 주문하시겠습니까?(Y/N) 장바구니를 보려면 C를 입력하세요: ");
            String next = scanner.nextLine();

            if (next.equalsIgnoreCase("C")) {
                cart.display();  // ← 장바구니 출력
            } else if (next.equalsIgnoreCase("Y")) {
                break;  // 주문 계속
            } else if (next.equalsIgnoreCase("N")) {
                clickPaymentButton();  // 결제
                return;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
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

    private void adminLoop() {
    while (true) {
        System.out.print("메뉴를 추가하시겠습니까? (A), 삭제하시겠습니까? (R): ");
        String action = scanner.nextLine();

        if (action.equalsIgnoreCase("A")) {
            admin.addMenuItem();
        } else if (action.equalsIgnoreCase("R")) {
            admin.removeMenuItem();
        } else {
            System.out.println("잘못된 입력입니다.");
        }

        System.out.print("계속 작업하시겠습니까? (C) / 종료하시겠습니까? (Q): ");
        String next = scanner.nextLine();
        if (next.equalsIgnoreCase("Q")) {
            System.out.println("관리자 모드를 종료합니다.\n");
            break;
        }
    }

    start(); // 초기화면으로 복귀
    }
    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.start();
    }
    
}
