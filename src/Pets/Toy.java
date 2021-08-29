package Pets;

import java.io.Serializable;

public class Toy implements Serializable {

    protected int price;
    protected String name;
    private int durabilityLevels = 3;

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    boolean isBroken(){
        return durabilityLevels <= 0;
    }

    public int getDurabilityLevels() {
        return durabilityLevels;
    }

    void setDurabilityLevels(int durabilityLevels) {
        this.durabilityLevels = durabilityLevels;
    }

}
