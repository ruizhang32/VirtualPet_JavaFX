package Pets;

import java.io.Serializable;

public class Koala extends Pet implements Serializable {

    public Koala(String name) {
        super(name);
        species = "Koala";
        price = 120;
        favoriteFood = "Leave";
        favoriteToy = "TreeBranch";
    }
}
