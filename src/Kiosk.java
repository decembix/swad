import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private String getCategoryByIndex(String input) {
        List<String> order = Arrays.asList("세트", "버거", "음료");
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < order.size()) {
                return order.get(index);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

   public void clickMenuButton() {
    while (true) {
        screen.displayCategories(menuList);

        System.out.print("카테고리를 선택하세요: ");
        String catInput = scanner.nextLine();
        String selectedCategory = getCategoryByIndex(catInput);

        if (selectedCategory == null) {
            System.out.println("잘못된 카테고리입니다.");
            continue;
        }

        screen.displayMenuByCategory(menuList, selectedCategory);

        System.out.print("주문할 메뉴 번호: ");
        String menuInput = scanner.nextLine();
        MenuItem selected = menuList.getMenuItemByCategoryAndIndex(selectedCategory, menuInput);
        if (selected != null) {
            cart.addItem(selected);
        } else {
            System.out.println("잘못된 선택입니다.");
        }

        while (true) {
            System.out.print("더 주문하시겠습니까?(Y/N) 장바구니를 보려면 C를 입력하세요: ");
            String next = scanner.nextLine();

            if (next.equalsIgnoreCase("C")) {
                cart.display();

                if (!cart.isEmpty()) {
                    System.out.print("\n수량을 수정하시겠습니까? (Y/N):  \n");
                    String modify = scanner.nextLine();

                    if (modify.equalsIgnoreCase("Y")) {
                        List<MenuItem> itemList = new ArrayList<>(cart.getCartList().keySet());
                        for (int i = 0; i < itemList.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, itemList.get(i).getName());
                        }

                        System.out.print("수정할 항목 번호를 선택하세요: ");
                        int choice = Integer.parseInt(scanner.nextLine()) - 1;

                        if (choice >= 0 && choice < itemList.size()) {
                            MenuItem selectedItem = itemList.get(choice);
                            System.out.print("수량을 늘릴까요(+), 줄일까요(-)?: ");
                            String op = scanner.nextLine();

                            if (op.equals("+")) {
                                cart.increaseQuantity(selectedItem);
                            } else if (op.equals("-")) {
                                cart.decreaseQuantity(selectedItem);
                            } else {
                                System.out.println("잘못된 입력입니다.");
                            }
                        }
                    }
                }

            } else if (next.equalsIgnoreCase("Y")) {
                break; // 다시 카테고리 선택으로
            } else if (next.equalsIgnoreCase("N")) {
                clickPaymentButton(); // 결제 화면으로
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

            System.out.print("쿠폰을 사용하시겠습니까? (Y/N): ");
            String useCoupon = scanner.nextLine();

            if (useCoupon.equalsIgnoreCase("Y")) {
                while (true) {
                    System.out.print("쿠폰 번호를 입력하세요 (0 입력 시 건너뜀): ");
                    String couponCode = scanner.nextLine();

                    if (coupon.isValid(couponCode)) {
                        String targetCategory = coupon.getCategoryForCoupon(couponCode);

                        int couponTotal = cart.getTotalPriceByCategory(targetCategory);
                        int remainingTotal = cart.getTotalPriceExcludingCategory(targetCategory);

                        System.out.printf("\n[%s] 카테고리에 쿠폰이 적용되어 %d원이 할인되었습니다.\n", targetCategory, couponTotal);

                        if (remainingTotal > 0) {
                            System.out.printf("\n잔여 항목(%d원)에 대해 카드 결제를 진행합니다.\n", remainingTotal);

                            System.out.print("카드번호(8자리)를 입력하세요: ");
                            String cardInput = scanner.nextLine();

                            if (isValidCardNumber(cardInput)) {
                                System.out.print("카드 유효기간을 입력하세요 (MM/YY): ");
                                String exp = scanner.nextLine();

                                if (isValidExpiry(exp)) {
                                    onPaymentSuccess("쿠폰+카드", couponCode, cardInput, exp, cart);

                                } else {
                                    System.out.println("유효하지 않은 유효기간입니다.\n");
                                    onPaymentFailure();
                                }
                            } else {
                                System.out.println("유효하지 않은 카드번호입니다.\n");
                                onPaymentFailure();
                            }
                        } else {
                            // 쿠폰으로 전부 결제됨
                            onPaymentSuccess("쿠폰", couponCode, null, null, cart);
                        }
                        return;

                    } else if (couponCode.equals("0")) {
                        System.out.println("쿠폰 사용을 건너뜁니다.\n");
                        break; // 카드결제로
                    } else {
                        System.out.println("유효하지 않은 쿠폰입니다. 다시 입력해주세요.\n");
                    }
                }
            }

            // 카드 결제만 진행
            System.out.print("카드번호(8자리)를 입력하세요: ");
            String cardInput = scanner.nextLine();

            if (isValidCardNumber(cardInput)) {
                System.out.print("카드 유효기간을 입력하세요 (MM/YY): ");
                String exp = scanner.nextLine();

                if (isValidExpiry(exp)) {
                    onPaymentSuccess("카드", null, cardInput, exp, cart);
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


    public void onPaymentSuccess(String method, String couponCode, String cardNumber, String expiry, Cart cart) {
    payment.processPayment(cart.getTotalPrice());

    printer.printReceipt(method, couponCode, cardNumber, expiry, cart);

    System.out.println("결제가 완료되었습니다. 이용해 주셔서 감사합니다.");
    cleanup();
}


    public void onPaymentFailure() {
        System.out.println("\n결제가 취소되었습니다. 초기화면으로 돌아갑니다.");
        cart.clearCart();
        start();
    }

    private void cleanup() {
        scanner.close();
        System.exit(0);
    }

    private void adminLoop() {
    while (true) {
        System.out.print("\n메뉴를 추가하시겠습니까? (A), 삭제하시겠습니까? (R): ");
        String action = scanner.nextLine();

        if (action.equalsIgnoreCase("A")) {
            admin.addMenuItem();
        } else if (action.equalsIgnoreCase("R")) {
            admin.removeMenuItem();
        } else {
            System.out.println("잘못된 입력입니다.\n");
        }

        System.out.print("\n계속 작업하시겠습니까? (C) / 종료하시겠습니까? (Q): ");
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
