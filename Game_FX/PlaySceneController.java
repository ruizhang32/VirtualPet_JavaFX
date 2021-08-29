package Game_FX;

import Pets.Pet;
import Pets.Player;
import Pets.Toy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import static java.lang.Integer.parseInt;
import static Game_FX.Main.currentGame;
import static Game_FX.NewPlayerController.currentPlayer;
import static Game_FX.Utils.*;

public class PlaySceneController implements Initializable {

    @FXML
    private ListView<String> statusListView;
    @FXML
    private ListView<String> statusListView2;
    @FXML
    private Button nextDayButton;
    @FXML
    private Button myPetButton1;
    @FXML
    private Button myPetButton2;
    @FXML
    private Button myPetButton0;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label currentPlayerNameLabel;
    @FXML
    private Label dayOfGame;
    @FXML
    private Label totalDaysLabel;
    @FXML
    private ListView<String> inventoryListView1;
    @FXML
    private ListView<String> inventoryListView2;
    @FXML
    private ImageView petPic1;
    @FXML
    private ImageView petPic2;
    @FXML
    private ImageView petPic3;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button chooseAPetButton;
    @FXML
    private Button petStoreButton;
    @FXML
    private Button toyStoreButton;
    @FXML
    private Button foodStoreButton;
    @FXML
    private Button actionButton;

    private Pet currentPet = null;
    private int currentPetIndex = 0;
    private int petListLen = currentPlayer.getMyPetList().size();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeButtonEffect(chooseAPetButton);
        changeButtonEffect(petStoreButton);
        changeButtonEffect(toyStoreButton);
        changeButtonEffect(foodStoreButton);
        changeButtonEffect(nextDayButton);
        changeButtonEffect(actionButton);
    }

//  L2  when click 'next' & 'action' buttons, this method will be referenced.
    void showPlaySceneData(Player currentPlayer) throws InterruptedException {

        updatePetsButtonPic(currentPlayer,petPic1,petPic2,petPic3);

//        update player info: account, daysToPlay, name, gameDay
        updatePlayerInfo();

//        case1 pet list petListLen is 0, click, nothing happens
        if (petListLen == 0) {
            setSelectedButton(myPetButton1, originalLengthB, originalLengthB,originalColourCode);
            setSelectedButton(myPetButton2, originalLengthB, originalLengthB,originalColourCode);
            setSelectedButton(myPetButton0, originalLengthB, originalLengthB,originalColourCode);
            initStatus();
            updateInventoryLV(currentPlayer);
        }
//        case2: only 1 pet
        else if (petListLen == 1) {
            setSelectedButton(myPetButton0, selectedLengthB, selectedLengthB,selectedColourCode);
            currentPet = currentPlayer.getMyPetList().get(0);
//            updateWarning(currentPet);
            popWarningWindow(currentPlayer);
            //show status of the selected pet
            updatePetStatusLV(currentPlayer, currentPet);
            updateInventoryLV(currentPlayer);
        }
//      case3: 2-3pets
        else {
            //find the selected pet
            currentPet = currentPlayer.getMyPetList().get(currentPetIndex);
            popWarningWindow(currentPlayer);
            updatePetStatusLV(currentPlayer, currentPet);
            updateInventoryLV(currentPlayer);
        }
    }

//  L3  used by showPlaySceneData
    private void updatePlayerInfo() {
        currentPlayerNameLabel.setText(currentPlayer.getName());
        totalDaysLabel.setText(Integer.toString(currentPlayer.getDaysToWin()));
        dayOfGame.setText(Integer.toString(currentPlayer.getGameDay()));
        balanceLabel.setText(Integer.toString(currentPlayer.getAccount()));
        scoreLabel.setText(Integer.toString(currentPlayer.getScore()));
    }

//  L3  used by showPlaySceneData
    private void updatePetStatusLV(Player currentPlayer, Pet pet) {

        String petName = pet.getName();
        String petSpecies = pet.getSpecies();
        String favFood = pet.getFavoriteFood();
        String favToy = pet.getFavoriteToy();
        String age = Integer.toString(pet.getAge());
        String tiredness = Integer.toString(pet.getTiredness());
        String hunger = Integer.toString(pet.getHunger());
        String toileting = Integer.toString(pet.getToiletLevel());
        String mood = Integer.toString(pet.getHappiness());
        String sickLevel = Boolean.toString(pet.isSick());
        String misbehaveLel = Boolean.toString(pet.isMad());
        String revivalTime = Integer.toString(pet.getReviveTimes());
        String weight = Integer.toString(pet.getWeight());
        String isAlive = Boolean.toString(pet.isAlive());
        String actionTimes = Integer.toString((pet.getActionTimes()));

        StringBuilder sb = new StringBuilder();
        if (currentPlayer.getMyToyList().size() != 0) {

            for (Toy toy : currentPlayer.getMyToyList()) {
                sb.append(toy.getDurabilityLevels()).append("(").append(toy.getName()).append(");");
            }
        }
        String toyDamLev = sb.toString();

        initStatusWithParams(petName, age, isAlive, petSpecies, favFood, favToy,sickLevel, misbehaveLel, weight, tiredness, hunger, toileting, mood,
                toyDamLev, revivalTime, actionTimes);
    }

//  L4  used by updatePetStatusLV
    private void initStatus() {

        ObservableList<String> items = FXCollections.observableArrayList(
                "Name:  ","Age:  ", "is Alive:  " , "Species:  ", "Favourite food:  ", "Favourite toy:  ",
                "is Sick:  ", "is Misbehaving:  ");
        ObservableList<String> items2 = FXCollections.observableArrayList(
                 "Weight:  ", "Tiredness:  ", "Hunger:  ", "Need for toilet:  ", "Mood:  ",
                "Toy damage:  ", "Revival Times:  ", "Action Times:  ");
        statusListView.setItems(items);
        statusListView2.setItems(items2);
        statusListView.setStyle("-fx-background-color: #e0ffff");
        statusListView2.setStyle("-fx-background-color: #e0ffff");
    }

//  L4  used by updatePetStatusLV
    private void initStatusWithParams(String petName, String age, String isAlive, String petSpecies, String favFood, String favToy, String sickLevel, String misbehaveLel,
                                      String weight, String tiredness, String hunger, String toileting, String mood,String toyDamLev, String revivalTime,String actionTimes
                                      ) {

        ObservableList<String> items = FXCollections.observableArrayList(
                "Name:  " + petName, "Age:  " + age, "is Alive:  " + isAlive, "Species:  " + petSpecies, "Favourite food:  " + favFood,
                "Favourite toy:  " + favToy, "is sick:  " + sickLevel, "is misbehaving:  " + misbehaveLel);
        ObservableList<String> items2 = FXCollections.observableArrayList(
                "Weight:  " + weight, "Tiredness:  " + tiredness, "Hunger:  " + hunger, "Need for toilet:  " + toileting,
                "Mood:  " + mood,  "Toy damage:  " + toyDamLev, "Revival Times:  " + revivalTime, "Action Times:  "+ actionTimes);
        statusListView.setItems(items);
        statusListView2.setItems(items2);
        statusListView.setCellFactory(cell -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                String level = null;
                if(item != null){
                    if ((item.startsWith("is Alive:  "))){
                        level = item.split(": {2}")[1];
                        if (level.equals("false")) {
                            setTextFill(Color.RED);
                        } else {
                            setTextFill(Color.BLACK);
                        }
                    }else if(item.startsWith("is sick:  ") || item.startsWith("is misbehaving:  ")){
                        level = item.split(": {2}")[1];
                        if (level.equals("true")) {
                            setTextFill(Color.RED);
                        } else {
                            setTextFill(Color.BLACK);
                        }
                    }else{
                        setTextFill(Color.BLACK);
                    }
                    setText(item);
                }
            }
        });

        statusListView2.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    int level = 0;
                    if(item != null){
                        if(item.startsWith("Toy damage:  ") || item.startsWith("Action Times:  ") || item.startsWith("Revival Times:  ")) setTextFill(Color.BLACK);
                        else{
                            if(isNumeric(item.split(": {2}")[1])) level = parseInt(item.split(": {2}")[1]);
                            if (level <= 5) setTextFill(Color.RED);
                            else setTextFill(Color.BLACK);
                        }
                        setText(item);
                    }
                }
            };
        });
        statusListView.setStyle("-fx-background-color: #e0ffff");
        statusListView2.setStyle("-fx-background-color: #e0ffff");
    }


//  L3  used by showPlaySceneData
    private void updateInventoryLV(Player currentPlayer){
        if(currentPlayer.getMyFoodList().size()!=0){
            ObservableList<String> foodInventory = countInventory(currentPlayer.getMyFoodList());
            inventoryListView1.setItems(foodInventory);
            inventoryListView1.setStyle("-fx-background-color: #e0ffff");
        }
        if(currentPlayer.getMyToyList().size()!=0) {
            ObservableList<String> toyInventory = countInventory(currentPlayer.getMyToyList());
            inventoryListView2.setItems(toyInventory);
            inventoryListView2.setStyle("-fx-background-color: #e0ffff");
        }
    }

//  L4  used by updateInventoryLV
    private <T> ObservableList<String> countInventory(ArrayList<T> myList){

        String key;
        String count;
        String name;
        String result;
        Map<String, Integer> counts = new HashMap<>();
        ObservableList<String> inventoryList = FXCollections.observableArrayList ();

        if(myList.size() != 0){
            for (T t: myList) {
                name = t.getClass().getName().split("\\.")[1];
                counts.merge(name , 1, Integer::sum);
            }
            for (String s : counts.keySet()) {
                key = s;
                count = Integer.toString(counts.get(key));
                result = key + ("(") + (count) + (") ");
                inventoryList.add(result);
            }
        }
        return inventoryList;
    }

//  L1
    public void storeButtonHandler(ActionEvent event) throws IOException {

        Button button = (Button) event.getSource();

        if(button.getId().equalsIgnoreCase("petStoreButton")){
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("PetStorePane.fxml"));
            mainPane.setCenter(anchorPane);
        }
        if(button.getId().equalsIgnoreCase("foodStoreButton")){
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("FoodStorePane.fxml"));
            mainPane.setCenter(anchorPane);
        }
        if(button.getId().equalsIgnoreCase("toyStoreButton")){
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("ToyStorePane.fxml"));
            mainPane.setCenter(anchorPane);
        }
        if(button.getId().equalsIgnoreCase("actionButton")){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ActionPane.fxml"));
            AnchorPane anchorPane = loader.load();
            mainPane.setCenter(anchorPane);
            ActionController controller = loader.getController();
            controller.setActionSceneData(currentPlayer);
        }
    }

//  L1
    public void setNextDayButton(ActionEvent event) throws IOException, InterruptedException {

        if(currentPlayer.getMyPetList().size() != 0) {
//            ask if player wants to use revival times
            popWarningWindow(currentPlayer);

            if (petListLen == 3 && currentPlayer.getMyPetList().stream().allMatch(pet -> pet.getCompletelyDeadFlag()==1)) {

                BorderPane borderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));
                Pane finalPanel = FXMLLoader.load(getClass().getResource("FinalScene.fxml"));
                borderPane.setCenter(finalPanel);

                Stage stage1 = (Stage) nextDayButton.getScene().getWindow();
                stage1.getScene().setRoot(borderPane);
                stage1.show();

                showScore();

            } else {
                if (currentPlayer.getGameDay() == currentPlayer.getDaysToWin()) {
                    if (currentPlayer.getMyPetList().stream().anyMatch(Pet::isAlive)) {

                        BorderPane borderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));
                        Pane finalPanel = FXMLLoader.load(getClass().getResource("WinFinalScene.fxml"));
                        borderPane.setCenter(finalPanel);

                        Stage stage1 = (Stage) nextDayButton.getScene().getWindow();
                        stage1.getScene().setRoot(borderPane);
                        stage1.show();
                        showScore();
                    }
                } else if(currentPlayer.getGameDay() < currentPlayer.getDaysToWin()){
                    for (Pet pet : currentPlayer.getMyPetList()) {
                        if (pet.isAlive()) {
                            if (!pet.isMad() && !pet.isSick()) {
                                pet.randomEvents();
                            }
                            pet.setAge(pet.getAge() + 1);
                            pet.setHunger(pet.getHunger() - 2);
                            pet.setHappiness(pet.getHappiness() - 1);
                            pet.setTiredness(pet.getTiredness() - 2);
                            pet.setToiletLevel(pet.getToiletLevel() - 2);
                            pet.setActionTimes(2);
                            if(pet.moodChange()=="") showAlertWindow(pet.moodChange());
                            updatePetStatusLV(currentPlayer, pet);
                        }
                    }
                    currentPlayer.setAccount(currentPlayer.getAccount() + 80);
                    currentPlayer.setGameDay(currentPlayer.getGameDay() + 1);
                    currentPlayer.setScore(currentPlayer.getScore()+10);
                    updatePlayerInfo();

                    BorderPane borderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));

                    FXMLLoader loader1 = new FXMLLoader();
                    loader1.setLocation(getClass().getResource("PlayScene.fxml"));
                    BorderPane playSceneBorderPanel =loader1.load();
                    borderPane.setCenter(playSceneBorderPanel);

                    PlaySceneController controller = loader1.getController();
                    controller.showPlaySceneData(currentPlayer);

                    Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    stage.getScene().setRoot(borderPane);
                    stage.show();
                }
            }
        }
    }

    private void showScore(){
        StringBuilder scoreInfo = new StringBuilder();
        for(Player player : currentGame.playerList){
            scoreInfo.append(player.getName()).append(": ").append(player.getScore()).append("\n");
        }
        showInfoWindow(scoreInfo.toString());
    }

//  L1
    public void setChooseAPetButton(ActionEvent event) throws IOException, InterruptedException {
        BorderPane mainBorderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("PlayScene.fxml"));
        BorderPane playSceneBorderPane =loader1.load();
        mainBorderPane.setCenter(playSceneBorderPane);

        PlaySceneController controller = loader1.getController();
        controller.showPlaySceneData(currentPlayer);

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(mainBorderPane);
        stage.show();
    }

//  L1
    public void setMyPetButtonHandler(ActionEvent event){

        setSelectedButton(myPetButton1,originalLengthB,originalLengthB,originalColourCode);
        setSelectedButton(myPetButton2,originalLengthB,originalLengthB,originalColourCode);
        setSelectedButton(myPetButton0,originalLengthB,originalLengthB,originalColourCode);

        if(petListLen>0){
            Button selectedPetButton = (Button) event.getSource();
            String index = selectedPetButton.getId().substring(selectedPetButton.getId().length()-1);
            currentPetIndex = parseInt(index);
            currentPet= currentPlayer.getMyPetList().get(currentPetIndex);

            if(currentPetIndex == 0 && petListLen ==0){
                setSelectedButton(myPetButton0,originalLengthB,originalLengthB,originalColourCode);
            }
//        case3: click something is null, choose no1 for default
            else if(currentPetIndex > petListLen -1){
                setSelectedButton(myPetButton0,selectedLengthB,selectedLengthB,selectedColourCode);
                updatePetStatusLV(currentPlayer,currentPet);
            }
//        case4: click the right button
            else if(currentPetIndex <= petListLen -1){
                if (currentPetIndex == 0) setSelectedButton(myPetButton0,selectedLengthB,selectedLengthB,selectedColourCode);
                else if(currentPetIndex == 1) setSelectedButton(myPetButton1,selectedLengthB,selectedLengthB,selectedColourCode);
                else if(currentPetIndex == 2) setSelectedButton(myPetButton2,selectedLengthB,selectedLengthB,selectedColourCode);
                updatePetStatusLV(currentPlayer,currentPet);
            }
        }
    }
}
