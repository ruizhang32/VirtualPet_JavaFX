package Pets;

import java.io.Serializable;

public class GuineaPig extends Pet implements Serializable {

    public GuineaPig(String name) {
        super(name);
        species = "GuineaPig";
        price = 60;
        favoriteFood = "Carrot";
        favoriteToy = "Roller";
    }
}
