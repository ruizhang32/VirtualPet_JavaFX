package Pets;

import java.io.Serializable;

public class Panda extends Pet implements Serializable {

    public Panda(String name) {
        super(name);
        species = "Panda";
        price = 120;
        favoriteFood = "Bamboo";
        favoriteToy = "ToyHorse";
    }
}
