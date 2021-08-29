package Pets;

import java.io.Serializable;

public class Food  implements Serializable {

    protected String name;
    protected int price;
    protected int nutrition = 10;
    protected int tastiness = 10;

    public int getPrice() {
        return price;
    }

    public int getNutrition() {
        return nutrition;
    }

    public int getTastiness() {
        return tastiness;
    }

    public String getName() {
        return name;
    }

}
