import java.util.HashMap;
import java.util.Map;

public class Cart {
    // 메뉴 이름을 키로, 수량을 값으로 저장
    private Map<MenuItem, Integer> cartList;

    public Cart() {
        cartList = new HashMap<>();
    }

    // 장바구니에 항목 추가
    public void addItem(MenuItem item) {
        int count = cartList.getOrDefault(item, 0);
        cartList.put(item, count + 1);
    }

    public void display() {
    if (cartList.isEmpty()) {
        System.out.println("장바구니가 비어 있습니다.");
        return;
    }

    System.out.println("\n[ 장바구니 내용 ]");
    for (Map.Entry<MenuItem, Integer> entry : cartList.entrySet()) {
        MenuItem item = entry.getKey();
        int quantity = entry.getValue();
        System.out.printf("주문한 메뉴: %s %d개\n", item.getName(), quantity);
    }
}

    // 총 금액 계산
    public int getTotalPrice() {
        int total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cartList.entrySet()) {
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            total += item.getPrice() * quantity;
        }
        return total;
    }

    // 장바구니 비우기 (결제 완료 후 초기화용)
    public void clearCart() {
        cartList.clear();
    }

    // getter 함수
    public Map<MenuItem, Integer> getCartList() {
    return cartList;
    }

    public int getTotalPriceByCategory(String category) {
    int total = 0;
    for (Map.Entry<MenuItem, Integer> entry : cartList.entrySet()) {
        MenuItem item = entry.getKey();
        if (item.getCategory().equals(category)) {
            total += item.getPrice() * entry.getValue();
        }
    }
    return total;
}

public int getTotalPriceExcludingCategory(String category) {
    int total = 0;
    for (Map.Entry<MenuItem, Integer> entry : cartList.entrySet()) {
        MenuItem item = entry.getKey();
        if (!item.getCategory().equals(category)) {
            total += item.getPrice() * entry.getValue();
        }
    }
    return total;
}

}
