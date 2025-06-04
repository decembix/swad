public class Screen {

    public void displayInitialScreen() {
        System.out.println("[initial screen]");
        System.out.println("_______________");
        System.out.println("어서오세요. 주문하시겠습니까? (Y/N/관리자모드 M)");
    }

    public void displayCategories(MenuList menuList) {
    menuList.displayCategories();
}

public void displayMenuByCategory(MenuList menuList, String category) {
    System.out.println("______________________________________");
    System.out.println("[ " + category + " 메뉴 ]");
    menuList.displayMenuByCategory(category);
    System.out.println("______________________________________");
}


    public void displayMenu(MenuList menuList) {
        System.out.println("______________________________________");
        System.out.println("[ 주문 화면 ]");
        // menuList.displayMenu();
        System.out.println("______________________________________");
    }

    public void displayFinalAmount(Cart cart) {
        System.out.println("\n===================[ 최종 금액 화면 ]====================");
        cart.display(); // 장바구니 내역 출력
        System.out.printf("최종 금액 : %d원\n", cart.getTotalPrice());
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("[결제 화면]");
        System.out.print("결제하시겠습니까?[Y/N]: ");
    }
}
