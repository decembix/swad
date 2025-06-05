import java.util.Scanner;

public class Admin {
    private MenuList menuList;
    private Scanner scanner;

    // 관리자 계정
    private final String ADMIN_ID = "adad";
    private final int ADMIN_PW = 1234;

    public Admin(MenuList menuList) {
        this.menuList = menuList;
        this.scanner = new Scanner(System.in);
    }

    // 관리자 인증
    public int isAdmin() {
        System.out.print("관리자 ID 입력: ");
        String inputID = scanner.nextLine();

        System.out.print("관리자 비밀번호 입력(숫자): ");
        int inputPW = Integer.parseInt(scanner.nextLine());

        if (inputID.equals(ADMIN_ID) && inputPW == ADMIN_PW) {
            System.out.println("인증 성공");
            return 1;
        } else {
            System.out.println("인증 실패");
            return 0;
        }
    }

    // 메뉴 추가
    public void addMenuItem() {
        System.out.print("추가할 메뉴 이름: ");
        String name = scanner.nextLine();

        System.out.print("가격 (숫자): ");
        int price = Integer.parseInt(scanner.nextLine());

        menuList.addMenuItem(new MenuItem(name, price));
        System.out.println("메뉴가 추가되었습니다.");
    }

    // 메뉴 삭제
    public void removeMenuItem() {
        System.out.println("삭제할 메뉴를 선택하세요:");
        menuList.displayMenu();

        System.out.print("삭제할 메뉴 번호: ");
        String input = scanner.nextLine();
        MenuItem item = menuList.getMenuItemByNumber(input);

        if (item != null) {
            menuList.removeMenuItem(item);
            System.out.println("메뉴가 삭제되었습니다.");
        } else {
            System.out.println("유효하지 않은 메뉴 번호입니다.");
        }
    }
}
