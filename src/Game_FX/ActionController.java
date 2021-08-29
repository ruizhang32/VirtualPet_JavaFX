package Game_FX;

import Pets.Pet;
import Pets.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static Game_FX.NewPlayerController.currentPlayer;
import static Game_FX.Utils.*;

public class ActionController {

    @FXML
    private Text confirmText;
    @FXML
    private Text confirmText1;
    @FXML
    private Button okButton;
    @FXML
    private Button petButton1;
    @FXML
    private Button petButton2;
    @FXML
    private Button petButton0;
    @FXML
    private ImageView petPic1;
    @FXML
    private ImageView petPic2;
    @FXML
    private ImageView petPic3;

    private int index;
    private Pet currentPet = null;
    private Button selectedButton;
    private Button selectedPetButton;
    private int currentPetIndex;
    ArrayList<Button> myPetButtonGroup = new ArrayList<>();
    int petListLen = currentPlayer.getMyPetList().size();
    private String[] actionList ={"feed your pet.", "let pet go to bed.","toileting pet","play with your pet.", "take pet to hospital.", "punish your pet."};

    //    set inti action selectors pattern
    public void setActionButton(ActionEvent event) {

        resetSelectedButton(selectedButton, originalLengthS,originalLengthS);

        selectedButton = (Button) event.getSource();
        String foodButtonId = selectedButton.getId();
        index = parseInt(foodButtonId.substring(foodButtonId.length()-1));

        setSelectedButton(selectedButton,selectedLengthS,selectedLengthS,selectedColourCode);

        okButton.setVisible(true);
        confirmText.setVisible(true);
        confirmText.setText("You want to " + actionList[index]);
        if(index == 4) confirmText1.setVisible(true);
        else confirmText1.setVisible(false);
    }

    public void setOkButton(ActionEvent event) {
        int balance = currentPlayer.getAccount();
        confirmText.setVisible(false);
        confirmText1.setVisible(false);

        if (currentPlayer.getMyPetList().size() == 0) {
            showAlertWindow("You don't have any pet.");
        } else {
            if(!currentPet.isAlive()){
                showAlertWindow("Sorry, your pet is dead.");
            }else if(currentPet.getActionTimes() == 0){
                showAlertWindow("You have already act twice for " + currentPet.getName() + " today.");
            }else if(currentPet.isAlive() && (currentPet.getActionTimes() == 2||currentPet.getActionTimes() == 1)) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                switch (index) {
                    case (0): {
                        eatAndPlay(currentPlayer.getMyFoodList(), stage);
                        break;
                    }
                    case (1): {
                        currentPet.sleep();
                        confirmText.setVisible(true);
                        confirmText.setText("Zzzzzz Zzzzzz");
                        break;
                    }
                    case (2): {
                        currentPet.toilet();
                        confirmText.setVisible(true);
                        confirmText.setText("Feel good ~~~");
                        break;
                    }
                    case (3): {
                        eatAndPlay(currentPlayer.getMyToyList(), stage);
                        break;
                    }
                    case (4): {
                        if (balance < 50) confirmText1.setVisible(true);
                        else currentPlayer.treatPet(currentPet);
                        confirmText.setVisible(true);
                        confirmText.setText("Feel better !");
                        break;
                    }
                    case (5): {
                        if (!currentPet.isMad()) {
                            okButton.setVisible(false);
                            confirmText.setVisible(true);
                            confirmText.setText("I am normal !");
                        } else {
                            currentPlayer.punishPet(currentPet);
                            confirmText1.setText("Let me out !");
                            confirmText1.setVisible(true);
                            break;
                        }
                    }
                    currentPlayer.setScore(currentPlayer.getScore()+10);
                }
            }
        }
    }

    private <T> void eatAndPlay(ArrayList myList,Stage stage){
        //        create a BorderPane with VBox & Pane to pop up
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxWidth(250);
        borderPane.setMaxHeight(350);
        borderPane.setStyle("-fx-background-color: #fff0f5; alignment: CENTER; -fx-border-color: #ffffff");

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #fff0f5; alignment: TOP_CENTER; Insets: 0,0,20px,0");
        vBox.setPrefWidth(220);
        vBox.setPrefHeight(300);

        Pane pane = new Pane();
        pane.setPrefWidth(250);
        pane.setPrefHeight(80);
        pane.setStyle("-fx-background-color: #fff0f5; alignment: CENTER");

        Label title = new Label("Select an item");
        title.setStyle("-fx-text-fill:#cf4cb7; -fx-font-size: 17");
        title.setLayoutX(60);
        title.setLayoutY(20);

        Label msgLabel = new Label("msgLabel");
        msgLabel.setVisible(false);
        msgLabel.setStyle("alignment: CENTER; -fx-text-fill:#ffffff;-fx-background-color:#ea0b0b");
        msgLabel.setLayoutX(60);
        msgLabel.setLayoutY(50);

        pane.getChildren().add(msgLabel);
        pane.getChildren().add(title);

        Button confirmButton = new Button("OK");
        confirmButton.setStyle("alignment: CENTER; -fx-text-fill:#ffffff;-fx-background-color:#eea1cb");
        confirmButton.setLayoutX(180.0);
        confirmButton.setLayoutY(20.0);
        confirmButton.setVisible(true);
//                    confirmButton.setPadding(new Insets(10,10,20,0));
        pane.getChildren().add(confirmButton);

        borderPane.setTop(pane);
        borderPane.setCenter(vBox);

        Scene secondScene = new Scene(borderPane,255,355);
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);

        newWindow.show();

        ToggleGroup group = new ToggleGroup();

        final String[] selectedFood = new String[2];
        if(myList.size()!=0) {
            for (int i = 0; i < myList.size(); i++) {
                String itemName = myList.get(i).getClass().getName().split("\\.")[1];
                RadioButton button = new RadioButton(itemName);
                button.setId(Integer.toString(i));
                button.setToggleGroup(group);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        selectedFood[0] = button.getText();
                        selectedFood[1] = button.getId();
                    }
                });
                button.setPadding(new Insets(20, 20, 0, 30));
                vBox.getChildren().add(button);
            }
        }

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                msgLabel.setText(selectedFood[0] + " selected");
                msgLabel.setVisible(true);
                if(Arrays.asList(FoodStoreController.foodList).contains(selectedFood[0])){
                    currentPet.eat(currentPlayer.getMyFoodList().get(parseInt(selectedFood[1])));
                    confirmText.setVisible(true);
                    if(currentPlayer.getMyFoodList().get(parseInt(selectedFood[1])).getName().equals(currentPet.getFavoriteFood())){
                        confirmText.setText("Very tasty ! ");
                    }else{
                        confirmText.setText("Better than nothing. ");
                    }
                    currentPlayer.getMyFoodList().remove(currentPlayer.getMyFoodList().get(parseInt(selectedFood[1])));
                }
                else if(Arrays.asList(ToyStoreController.toyList).contains(selectedFood[0])){
                    currentPet.play(currentPlayer.getMyToyList().get(parseInt(selectedFood[1])));
                    confirmText.setVisible(true);
                    if(currentPlayer.getMyFoodList().get(parseInt(selectedFood[1])).getName().equals(currentPet.getFavoriteToy())){
                        confirmText.setText("WaHahahahaha! ");
                    }else{
                        confirmText.setText("Better than nothing. ");
                    }
                }
                newWindow.close();
            }
        });
    }

    //    set pet selectors pattern
    public void selectPetButtonHandler(ActionEvent event) {
        resetSelectedButton(selectedPetButton,originalLengthB,originalLengthB);

        if(petListLen > 0){
            selectedPetButton = (Button) event.getSource();
            String index = selectedPetButton.getId().substring(selectedPetButton.getId().length()-1);
            currentPetIndex = parseInt(index);
            currentPet= currentPlayer.getMyPetList().get(currentPetIndex);

//        case3: click something is null, choose no1 for default
            if(currentPetIndex > petListLen -1){
                setSelectedButton(petButton0,selectedLengthB,selectedLengthB,selectedColourCode);
            }
//        case4: click the right button
            else if(currentPetIndex <= petListLen -1){
                switch (currentPetIndex) {
                    case 0:
                        setSelectedButton(petButton0,selectedLengthB,selectedLengthB,selectedColourCode);
                        break;
                    case 1:
                        setSelectedButton(petButton1,selectedLengthB,selectedLengthB,selectedColourCode);
                        break;
                    case 2:
                        setSelectedButton(petButton2,selectedLengthB,selectedLengthB,selectedColourCode);
                        break;
                }
            }
        }
    }

    //    find and set current pet
    void setActionSceneData(Player currentPlayer){

        updatePetsButtonPic(currentPlayer,petPic1,petPic2,petPic3);

        if(petListLen > 0) {
            if (petListLen == 1) {
                setSelectedButton(petButton0, selectedLengthB, selectedLengthB,selectedColourCode);
                currentPet = currentPlayer.getMyPetList().get(0);
            }
            else {
                currentPet = currentPlayer.getMyPetList().get(currentPetIndex);
            }
        }
    }
}
