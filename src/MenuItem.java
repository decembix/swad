public class MenuItem {
    private String name;
    private int price;
    private String category;

    public MenuItem(String name, int price, String category) {
    this.name = name;
    this.price = price;
    this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int isAvailavle(){
        //MenuList에 this라는 Item이 있으면 return 1 없으면 retutn 0

        return 1;
    }

    public int isinMenu(){
        //MenuList에 this라는 Item이 있으면 return 1 없으면 retutn 0

        return 1;
    }

    public String getCategory() {
        return category;
    }
}
