import java.util.Scanner;

public class Kiosk {
    private Screen screen;
    private Cart cart;
    private MenuList menuList;
    private Payment payment;
    private Scanner scanner;
    private Admin admin;
    private Coupon coupon;
    private Printer printer;


    public Kiosk() {
        screen = new Screen();
        cart = new Cart();
        menuList = new MenuList();
        payment = new Payment();
        scanner = new Scanner(System.in);
        admin = new Admin(menuList);  // admin 연결
        coupon = new Coupon();
        printer = new Printer();
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

            while (true) {
                System.out.print("더 주문하시겠습니까?(Y/N) 장바구니를 보려면 C를 입력하세요: ");
                String next = scanner.nextLine();

                if (next.equalsIgnoreCase("C")) {
                    cart.display();  // ← 장바구니 출력
                } else if (next.equalsIgnoreCase("Y")) {
                    break;  // 주문 계속
                } else if (next.equalsIgnoreCase("N")) {
                    clickPaymentButton(); // 결제하기
                    return;
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            }
        }
    }


    //카드번호 검사(8자리)
    private boolean isValidCardNumber(String cardNumber) {
    return cardNumber.matches("\\d{8}");
    }

    //카드 유효기간 검사
    private boolean isValidExpiry(String expiry) {
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


   public void clickPaymentButton() {
    screen.displayFinalAmount(cart);

    String input = scanner.nextLine();
    if (input.equalsIgnoreCase("Y")) {

        // 쿠폰 사용 확인
        System.out.print("쿠폰을 사용하시겠습니까? (Y/N): ");
        String useCoupon = scanner.nextLine();

        if (useCoupon.equalsIgnoreCase("Y")) {
            while (true) {
                System.out.print("쿠폰 번호를 입력하세요 (0 입력 시 건너뜀): ");
                String couponCode = scanner.nextLine();

                if (coupon.isValid(couponCode)) {
                    System.out.println("쿠폰이 적용되었습니다!\n");
                    onPaymentSuccess("쿠폰", couponCode, null, cart);  // 쿠폰 사용 시 바로 결제 완료(기프티콘)
                    return;
                } else if (couponCode.equals("0")) {
                    System.out.println("쿠폰 사용을 건너뜁니다.");
                    break;  // 카드결제로 진행
                } else {
                    System.out.println("유효하지 않은 쿠폰입니다. 다시 입력해주세요.");
                }
            }
        }

        // 카드 결제(쿠폰 사용X)
        System.out.print("카드번호(8자리)를 입력하세요: ");
        String cardInput = scanner.nextLine();

        if (isValidCardNumber(cardInput)) {
            System.out.print("카드 유효기간을 입력하세요 (MM/YY): ");
            String exp = scanner.nextLine();

            if (isValidExpiry(exp)) {
              onPaymentSuccess("카드", cardInput, exp, cart);
            } else {
                System.out.println("유효하지 않은 유효기간입니다.");
                onPaymentFailure();
            }
        } else {
            System.out.println("유효하지 않은 카드번호입니다.");
            onPaymentFailure();
        }

    } else {
        onPaymentFailure();
    }
}


    public void onPaymentSuccess(String method, String codeOrCard, String expiry, Cart cart) {
        payment.processPayment(cart.getTotalPrice());
        System.out.println("결제가 완료되었습니다. 이용해 주셔서 감사합니다.");
       
        //영수증 출력
        printer.printReceipt(method, codeOrCard, expiry, cart);
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
