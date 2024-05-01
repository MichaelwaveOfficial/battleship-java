import java.util.Scanner;

public class CoffeeMachine {
    // Scanner for user input.
    Scanner scanner = new Scanner(System.in);
    // Determine whether the machine is running or not.
    private boolean running = true;
    // Machines resources.
    private int money = 550, cupsAvailable = 9, water = 400, milk = 540, beans = 120;
    // Fixed values representing machine states.
    private enum MachineFunctions {
        BUY, FILL, TAKE, REMAINING, EXIT
    }
    /**
     * Processes users choice of program functionality.
     * @param input
     */
    public void takeUserInput(String input) {
        // prompt user to choose functionality. Otherwise, handle incorrect input.
        try {
            // Convert input string to uppercase to match stored enumerations.
            MachineFunctions requiredFunction = MachineFunctions.valueOf(input.toUpperCase());
            // case statement to handle multiple options.
            switch (requiredFunction) {
                case BUY:
                    buyCoffee();
                    break;
                case FILL:
                    machineSupplies();
                    break;
                case TAKE:
                    takeMoney();
                    break;
                case REMAINING:
                    machineState(water,milk,beans,cupsAvailable,money);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input!");
        }
    }
    /**
     * Prompt choice of coffee.
     */
    public void buyCoffee() {
        try {
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
            String userInput = scanner.next();

            if (cupsAvailable > 0) {
                switch (userInput) {
                    case "1":
                        // Brew Espresso.
                        brewBeverage(new Espresso());
                        break;
                    case "2":
                        // Brew Latte.
                        brewBeverage(new Latte());
                        break;
                    case "3":
                        // Brew Cappuccino.
                        brewBeverage(new Cappuccino());
                        break;
                    case "back":
                        // Circle user back to main menu.
                        TestDrive.main(null);
                        break;
                    default:
                        // No selection made.
                        System.out.println("A selection was not made.\n");
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input!");
        }
    }
    /**
     * Compares resources required to those available to determine if product can be produced.
     */
    public void brewBeverage(Coffee coffee) {
        if (cupsAvailable > 0) {
            if (water > coffee.water) {
                if (milk > coffee.milk) {
                    if (beans > coffee.beans) {
                        // if all pre-requisites met, deduct resources. Increment cash earned.
                        cupsAvailable--;
                        water -= coffee.water;
                        beans -= coffee.beans;
                        milk -= coffee.milk;
                        money += coffee.price;
                        System.out.println("I have enough resources, making you a coffee!\n");
                    } else {
                        System.out.println("Not enough beans.\n");
                    }
                } else {
                    System.out.println("Not enough milk.\n");
                }
            }else {
                System.out.println("Sorry, not enough water!\n");
            }
        } else {
            System.out.println("Not enough cups.\n");
        }
    }
    /**
     * Increment machines resources when they are getting low.
     */
    public void machineSupplies(){
        System.out.println("Write how many ml of water you want to add: ");
        water += scanner.nextInt();

        System.out.println("Write how many ml of milk you want to add: ");
        milk += scanner.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add: ");
        beans += scanner.nextInt();

        System.out.println("Write how many disposable cups you want to add: ");
        cupsAvailable += scanner.nextInt();
    }
    /**
     * Empty machine of its accumulated profits.
     */
    public void takeMoney() {
        if (money > 0) {
            System.out.println("I gave you $" + money + "\n");
            money = 0;
        } else {
            System.out.println("No money left.\n");
        }
    }
    /**
     * Inquire about the coffee machines resource levels.
     * @param water
     * @param milk
     * @param beans
     * @param cups
     * @param money
     */
    public void machineState(int water, int milk, int beans, int cups, int money) {
        System.out.println("The coffee machine has:\n" +
                water + " ml of water\n" +
                milk + " ml of milk\n" +
                beans + " g of coffee beans\n" +
                cups + " disposable cups\n" +
                "$" + money + " of money\n");
    }
}
