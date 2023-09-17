import java.util.List;

public class Ships {

    // Initialisation of the battleships and their properties.
    public List<Battleship> deployShips() {
        List<Battleship> ships = List.of(
                new Battleship("Aircraft Carrier", 5),
                new Battleship("Battleship", 4),
                new Battleship("Submarine", 3),
                new Battleship("Cruiser", 3),
                new Battleship("Destroyer", 2)
        );
        return ships;
    }


}