package Pets;

import java.io.Serializable;
import java.util.Random;

import static Game_FX.Utils.showAlertWindow;

public abstract class Pet implements Serializable {

    private int age;
    private int completelyDeadFlag;
    private int hunger;
    private int happiness;
    private int tiredness;
    private int toiletLevel;
    private int weight;
    protected String name;
    String species;
    private boolean isMad;
    private boolean isSick;
    public int price;
    private int actionTimes;
    private int reviveTimes;
    String favoriteFood;
    String favoriteToy;
    private static final int Max = 8;
    private static final int Min = 3;

    public int getCompletelyDeadFlag() {
        return completelyDeadFlag;
    }

    public void setCompletelyDeadFlag(int completelyDeadFlag) {
        this.completelyDeadFlag = completelyDeadFlag;
    }

    public String getName() {
        return name;
    }

    public static int getMin() {
        return Min;
    }

    public int getActionTimes() {
        return actionTimes;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public int getReviveTimes() {
        return reviveTimes;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public String getFavoriteToy() {
        return favoriteToy;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int tiredness) {
        this.tiredness = tiredness;
    }

    public int getToiletLevel() {
        return toiletLevel;
    }

    public void setToiletLevel(int toiletLevel) {
        this.toiletLevel = toiletLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setActionTimes(int actionTimes) {this.actionTimes = actionTimes; }

    public boolean isMad() {
        return isMad;
    }

    void setMad(boolean isMad) {
        this.isMad = isMad;
    }

    public boolean isSick() {
        return isSick;
    }

    void setSick(boolean isSick) {
        this.isSick = isSick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    protected Pet(String name){
        this.name = name;
        age = 1;
        isMad = false;
        isSick = false;
        hunger = 7;
        happiness = 7;
        tiredness = 7;
        weight = 7;
        toiletLevel = 7;
        actionTimes = 2;
        reviveTimes = 1;
        completelyDeadFlag = 0;
    }

    public void randomEvents(){
        Random random = new Random();
        int chance = random.nextInt() % 3;
        if (chance == 1) setMad(true);
        else if (chance == 2) setSick(true);
    }

    public boolean isAlive(){

        return happiness > Min &&
                hunger > Min &&
                toiletLevel > Min &&
                tiredness > Min &&
                weight > Min ;
    }

    public String deathReason(Pet pet){
        String deathReason = null;
        if (happiness <= Min) deathReason = pet.getName() + " dies because of sadness";
        else if (hunger <= Min) deathReason =  pet.getName() + " dies because of too hungry.";
        else if (tiredness <= Min) deathReason =  pet.getName() + " dies because of tiredness.";
        else if (toiletLevel <= Min) deathReason =  pet.getName() + " dies because of no toileting.";
        else if (weight <= Min) deathReason =  pet.getName() + " dies because malnutrition.";

        return deathReason;
    }

    public String moodChange(){
        StringBuilder moodStuats = new StringBuilder();
        if (isAlive()){
            if (happiness <= Min) moodStuats.append("Pet feels sad.\n");
            if (hunger <= Min) moodStuats.append("Pet feels hungry.\n");
            if (tiredness <= Min) moodStuats.append("Pet feels tired.\n");
            if (toiletLevel <= Min) moodStuats.append("Pet wants to go toilet.\n");

            if (happiness > Max) happiness = Max;
            if (hunger > Max) hunger = Max;
            if (toiletLevel > Max) toiletLevel = Max;
            if (tiredness > Max) tiredness = Max;
            if (weight > Max) weight = Max;
        }
        return moodStuats.toString();
    }

    public void sleep(){
        actionTimes --;
        tiredness += 2;
    }

    public void play(Toy toy){

        actionTimes --;
        if (toy.getName().equals(favoriteToy)){
            hunger -= 6;
            happiness += 5;
            tiredness -= 5;
            toiletLevel -= 3;
            toy.setDurabilityLevels(toy.getDurabilityLevels()-2);
        }else{
            hunger -= 4;
            happiness += 2;
            tiredness -= 2;
            toiletLevel -= 1;
            toy.setDurabilityLevels(toy.getDurabilityLevels()-1);
        }

        if(toy.isBroken()) showAlertWindow("Toy is broken.");
    }

    public void toilet(){
        actionTimes --;
        happiness += 2;
        toiletLevel += 2;
        if(weight > 0) weight --;
    }

    public void revive(){
        reviveTimes--;
        hunger = 5;
        happiness = 5;
        tiredness = 5;
        toiletLevel = 5;
        actionTimes = 2;
        isMad = false;
        isSick = false;
        weight = 5;
    }

    public void eat(Food food){
        actionTimes --;
        if (food.getName().equals(favoriteFood)){
            hunger += food.getNutrition() / 2;
            happiness += food.getTastiness() / 2;
            toiletLevel += 2;
            if(weight > 0) weight += 3;
        }else{
            hunger += food.getNutrition() / 5;
            happiness += food.getTastiness() / 5;
            toiletLevel += 1;
            if(weight > 0) weight += 1;
        }
    }
}
