import java.util.Scanner;

public class TestDrive {

    public static void main(String[] args) {

        CoffeeMachine m = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):  ");
            String input = scanner.next();
            m.takeUserInput(input);
        }
    }
}
