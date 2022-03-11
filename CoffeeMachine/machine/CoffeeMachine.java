package machine;

import java.util.Scanner;
import java.util.Objects;

public class CoffeeMachine {

    public void start() {
        Scanner sc = new Scanner(System.in);
        
        String action = null;
		System.out.println("Write action (buy, fill, take, remaining, exit):");
        while (true) {
            if (command(action) == 1) {
                break;
            }
            action = sc.next();
        }
    }

    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private STATE state;
    private FILL_STATE fill_state;

    private enum STATE {
        IDLE, BUY, FILL
    }

    private enum FILL_STATE {
        FILL_MILK, FILL_WATER, FILL_BEANS, FILL_CUPS, NONE
    }

    public CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.cups = 9;
        this.money = 550;
        this.state = STATE.IDLE;
        this.fill_state = FILL_STATE.NONE;
    }

    public int command(String str) {
        if (str == null) {
            return 0;
        } else if (this.state == STATE.IDLE) {
            if (Objects.equals("buy", str)) {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                this.state = STATE.BUY;
            } else if (Objects.equals("fill", str)) {
                this.state = STATE.FILL;
                _fill(str);
            } else if (Objects.equals("take", str)) {
                _take();
                System.out.println("Write action (buy, fill, take, remaining, exit):");
            } else if (Objects.equals("remaining", str)) {
                _printResources();
                System.out.println("Write action (buy, fill, take, remaining, exit):");
            } else if (Objects.equals("exit", str)) {
                return 1;
            }
        } else {
            if (this.state == STATE.BUY) {
                _buy(str);
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                this.state = STATE.IDLE;
            } else if (this.state == STATE.FILL) {
                _fill(str);
                if (this.fill_state == FILL_STATE.NONE) {
                    this.state = STATE.IDLE;
                    System.out.println("Write action (buy, fill, take, remaining, exit):");
                }
            }
        }
        return 0;
    }

    private void _buy(String str) {
        int numb;
        try {
            numb = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return;
        }
        switch (numb) {
            case 1:
                _espresso();
                break;
            case 2:
                _latte();
                break;
            case 3:
                _cappuccino();
                break;
        }
    }

    private void _espresso() {
        // For the espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.
        int[] ingridients = new int[]{ 250, 0, 16, 1, 4};

        if (_check_resources(ingridients)) {
            this.water -= ingridients[0];
            this.milk -= ingridients[1];
            this.beans -= ingridients[2];
            this.cups -= ingridients[3];
            this.money += ingridients[4];
            System.out.println("I have enough resources, making you a coffee!");
        }
    }

    private void _latte() {
        // For the latte, the coffee machine needs 350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.
        int[] ingridients = new int[]{ 350, 75, 20, 1, 7};

        if (_check_resources(ingridients)) {
            this.water -= ingridients[0];
            this.milk -= ingridients[1];
            this.beans -= ingridients[2];
            this.cups -= ingridients[3];
            this.money += ingridients[4];
            System.out.println("I have enough resources, making you a coffee!");
        }
    }

    private void _cappuccino() {
        // And for the cappuccino, the coffee machine needs 200 ml of water, 100 ml of milk, and 12 g of coffee beans. It costs $6.
        int[] ingridients = new int[]{ 200, 100, 12, 1, 6};

        if (_check_resources(ingridients)) {
            this.water -= ingridients[0];
            this.milk -= ingridients[1];
            this.beans -= ingridients[2];
            this.cups -= ingridients[3];
            this.money += ingridients[4];
            System.out.println("I have enough resources, making you a coffee!");
        }
    }

    private boolean _check_resources(int[] ingridients) {
        boolean flag = true;

        if (this.water - ingridients[0] < 0) {
            flag = false;
            _print_msg(0);
        }
        if (this.milk - ingridients[1] < 0) {
            flag = false;
            _print_msg(1);
        }
        if (this.beans - ingridients[2] < 0) {
            flag = false;
            _print_msg(2);
        }
        if (this.cups - ingridients[3] < 0) {
            flag = false;
            _print_msg(3);
        }
        return flag;
    }

    private void _print_msg(int i) {
        String template;

        if (i == 0) {
            template = "water!";
        } else if (i == 1) {
            template = "milk!";
        } else if (i == 2) {
            template = "coffee beans!";
        } else {
            template = "disposable cups!";
        }
        System.out.println("Sorry, not enough " + template);
    }

    private void _fill(String str) {
        int numb;
        try {
            numb = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("Write how many ml of water you want to add:");
            this.fill_state = FILL_STATE.FILL_WATER;
            return;
        }
        switch (this.fill_state) {
            case FILL_WATER:
                this.water += numb;
                System.out.println("Write how many ml of milk you want to add:");
                this.fill_state = FILL_STATE.FILL_MILK;
                break;
            case FILL_MILK:
                this.milk += numb;
                System.out.println("Write how many grams of coffee beans you want to add:");
                this.fill_state = FILL_STATE.FILL_BEANS;
                break;
            case FILL_BEANS:
                this.beans += numb;
                System.out.println("Write how many disposable cups of coffee you want to add:");
                this.fill_state = FILL_STATE.FILL_CUPS;
                break;
            case FILL_CUPS:
                this.cups += numb;
                this.fill_state = FILL_STATE.NONE;
                break;
        }
    }

    private void _take() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
    }

    private void _printResources() {
        System.out.println("The coffee machine has:");
        System.out.println(this.water + " ml of water");
        System.out.println(this.milk + " ml of milk");
        System.out.println(this.beans + " g of coffee beans");
        System.out.println(this.cups + " disposable cups");
        System.out.println("$" + this.money + " of money");
    }
}