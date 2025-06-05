import java.util.*;

public class MenuList {
    private Map<String, List<MenuItem>> categoryMap;

    public MenuList() {
        categoryMap = new HashMap<>();

        // 카테고리
        categoryMap.put("버거", new ArrayList<>());
        categoryMap.put("음료", new ArrayList<>());
        categoryMap.put("세트", new ArrayList<>());

        // 메뉴
        categoryMap.get("버거").add(new MenuItem("불고기 버거", 4500, "버거"));
        categoryMap.get("버거").add(new MenuItem("치즈 버거", 4000, "버거"));
        categoryMap.get("버거").add(new MenuItem("치킨 버거", 4500, "버거"));
        categoryMap.get("음료").add(new MenuItem("콜라", 2000, "음료"));
        categoryMap.get("음료").add(new MenuItem("사이다", 2000, "음료"));
        categoryMap.get("음료").add(new MenuItem("오렌지 주스", 2500, "음료"));
        categoryMap.get("세트").add(new MenuItem("불고기버거 세트", 5500, "세트"));
        categoryMap.get("세트").add(new MenuItem("치즈버거 세트", 5000, "세트"));
        categoryMap.get("세트").add(new MenuItem("치킨버거 세트", 5500, "세트"));
    }

    // 카테고리 보여주기
   public void displayCategories() {
    System.out.println(); 
    System.out.println("[카테고리]");

    List<String> order = Arrays.asList("세트", "버거", "음료");
    for (int i = 0; i < order.size(); i++) {
        System.out.printf("%d. %s\n", i + 1, order.get(i));
        }
    }

    // 카테고리별 메뉴 보여주기
    public void displayMenuByCategory(String category) {
        List<MenuItem> items = categoryMap.get(category);
        if (items == null || items.isEmpty()) {
            System.out.println("해당 카테고리에 메뉴가 없습니다.");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);
            System.out.printf("%d. %s \\%d\n", i + 1, item.getName(), item.getPrice());
        }
    }

    // 번호 선택 -> 해당 카테고리 메뉴 보여주기
    public MenuItem getMenuItemByCategoryAndIndex(String category, String input) {
        List<MenuItem> items = categoryMap.get(category);
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < items.size()) {
                return items.get(idx);
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
        }
        return null;
    }

    // 메뉴 추가(관리자용)
    public void addMenuItem(String category, MenuItem item) {
        List<MenuItem> list = categoryMap.get(category);
        if (list != null) {
            list.add(item);
        }
    }

    // 메뉴 삭제(관리자용)
    public void removeMenuItem(String category, int index) {
        List<MenuItem> list = categoryMap.get(category);
        if (list != null && index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }

    // 메뉴 보여주기(관리자용)
    public void displayMenuForAdmin(String category) {
        List<MenuItem> list = categoryMap.get(category);
        for (int i = 0; i < list.size(); i++) {
            MenuItem item = list.get(i);
            System.out.printf("%d. %s \\%d\n", i + 1, item.getName(), item.getPrice());
        }
    }

    public Set<String> getCategories() {
        return categoryMap.keySet();
    }
}
