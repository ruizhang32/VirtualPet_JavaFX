package Pets;

import java.io.Serializable;

public class Alpaca extends Pet  implements Serializable {

    public Alpaca(String name) {
        super(name);
        species = "Alpaca";
        price = 60;
        favoriteFood = "Grass";
        favoriteToy = "HumanTouch";
    }
}
