package Pets;

import java.io.Serializable;

public class Dog extends Pet implements Serializable {

    public Dog(String name) {
        super(name);
        species = "Dog";
        price = 30;
        favoriteFood = "Meat";
        favoriteToy = "BoneStick";
    }
}
