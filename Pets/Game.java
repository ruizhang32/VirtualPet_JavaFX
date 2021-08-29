package Pets;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

    public ArrayList<Player> playerList ;

    public Game() {
        this.playerList = new ArrayList<>();
    }
}
