package Game;

public class Battleship {
    private String name;
    private int size;
    public Battleship(String name, int size) {
        this.name = name;
        this.size = size;
    }
    public String getName() { return name; }
    public int getSize() { return size; }

}