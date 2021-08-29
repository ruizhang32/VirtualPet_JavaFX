package Pets;

import java.io.Serializable;

public class Cat extends Pet implements Serializable {

    public Cat(String name) {
        super(name);
        species = "Cat";
        price = 30;
        favoriteFood = "Fish";
        favoriteToy = "Scratcher";
    }
}
