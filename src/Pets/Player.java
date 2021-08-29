package Pets;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Player implements Serializable {

    private String name;
    private int daysToWin;
    private int gameDay;
    private int score;
    private int account;
    private ArrayList<Pet> myPetList;
    private ArrayList<Toy> myToyList;
    private ArrayList<Food> myFoodList;

    public ArrayList<Pet> getMyPetList() {
        return myPetList;
    }
    public ArrayList<Toy> getMyToyList() {
        return myToyList;
    }
    public ArrayList<Food> getMyFoodList() {
        return myFoodList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getDaysToWin() {
        return daysToWin;
    }

    public int getGameDay() {
        return gameDay;
    }

    public void setGameDay(int gameDay) {
        this.gameDay = gameDay;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player(String name, int daysToWin) {
        this.name = name;
        this.daysToWin = daysToWin;
        gameDay = 1;
        account = 180;
        myPetList = new ArrayList<>();
        myFoodList = new ArrayList<>();
        myToyList = new ArrayList<>();
        score = 0;
    }

    public void treatPet(Pet pet){
        account-= 50;
        pet.setHappiness(pet.getHappiness()+3);
        pet.setActionTimes(pet.getActionTimes()-1);
        pet.setSick(false);
    }

    public void punishPet(Pet pet){
        pet.setActionTimes(pet.getActionTimes()-1);
        pet.setHappiness(pet.getHappiness()-3);
        pet.setMad(false);
    }
}
