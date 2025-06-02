public class MenuItem {
    private String name;
    private int price;

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;

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
}
