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
        cartList.put(item, cartList.getOrDefault(cartList, 0)); //같은 항목을 여러 번 추가시 횟수마다 카운트가 늘어나야 함
    }

    // 장바구니에 담긴 내용 출력 : 이 부분에서 display가 사용되도록 "더 주문하시겠습니까?(Y/N) 장바구니를 보려면 C를 입력하세요."" 를 출력하고고 입력이 C인 경우 카트를 볼 수 있도록 해줘. 이후 더 주문하시겠습니까?를 다시 띄워줘줘
    public void display() {
        if (cartList.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

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
}
