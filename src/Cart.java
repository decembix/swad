import java.util.HashMap;
import java.util.Map;

public class Cart {
    // 메뉴 이름을 키로, 수량을 값으로 저장
    private Map<MenuItem, Integer> cartList; //원래 cartList와 이미 카트에 들어간 아이템의 속성을 체크할 cartItem을 따로 두었는데, 둘이 겹친다고 판단하여 cartItem 삭제

    public Cart() {
        cartList = new HashMap<>();
    }

    // 장바구니에 항목 추가
    public void addItem(MenuItem item) {
        int count = cartList.getOrDefault(item, 0);
        cartList.put(item, count + 1);
    }

    // 총 결제 금액 반환
    public int getTotalAmount() {
        int total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cartList.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void display() {
    if (cartList.isEmpty()) {
        System.out.println("장바구니가 비어 있습니다.");
        return;
    }

    System.out.println("[ 장바구니 내용 ]");
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
    public void clear() {
        cartList.clear();
    }
}
