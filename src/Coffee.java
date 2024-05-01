public class Coffee {
    protected int water, milk, beans, price;
    public Coffee(int water, int milk, int beans, int price) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.price = price;
    }
    public int getWater() { return water; }
    public int getMilk() { return milk; }
    public int getBeans() { return beans; }
    public int getPrice() { return price; }
}
