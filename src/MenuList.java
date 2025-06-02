import java.util.ArrayList;
import java.util.List;

/**
 * MenuList 클래스는 Kiosk에서 사용할 수 있는 메뉴 아이템 목록을 관리합니다.
 */
public class MenuList {
    private List<MenuItem> menuItems;

    public MenuList() {
        menuItems = new ArrayList<>();
        // 예시 메뉴 초기화
        menuItems.add(new MenuItem("아이스 아메리카노", 4000));
        menuItems.add(new MenuItem("아이스 카페라떼", 4500));
        menuItems.add(new MenuItem("초코라떼", 5500));

        //카테고리 별로 나눌거고 menuItems array대신 category_burger, category_Drink, category_Side로 사용
        //display
    }





    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
    }

    public void displayMenu() {
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            System.out.printf("%d. %s \\%d\n", i + 1, item.getName(), item.getPrice());
        }
    }

    public MenuItem getMenuItemByNumber(String input) {
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < menuItems.size()) {
                return menuItems.get(idx);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ 숫자를 입력해주세요.");
        }
        return null;
    }
}
