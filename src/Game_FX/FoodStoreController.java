package Game_FX;

import Pets.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import static java.lang.Integer.parseInt;
import static Game_FX.NewPlayerController.currentPlayer;
import static Game_FX.Utils.*;

public class FoodStoreController extends AnchorPane{

    @FXML
    private Button foodButton0;
    @FXML
    private Button foodButton1;
    @FXML
    private Button foodButton2;
    @FXML
    private Button foodButton3;
    @FXML
    private Button foodButton4;
    @FXML
    private Button foodButton5;
    @FXML
    private Button okButton;
    @FXML
    private Label welcomeLabel1;
    @FXML
    private Label welcomeLabel2;

    private int index;
    private Button selectedButton = null;
    private String[] priceList ={"20", "5","10","50", "10", "20"};
    public static String[] foodList = {"Meat", "Fish","Leave","Bamboo", "Carrot", "Grass"};


    public void setFoodButton(ActionEvent event) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        resetSelectedButton(selectedButton,originalLengthB,originalLengthB);

        selectedButton = (Button) event.getSource();
        String foodButtonId = selectedButton.getId();
        index = parseInt(foodButtonId.substring(foodButtonId.length()-1));

        Utils.setSelectedButton(selectedButton,selectedLengthB,selectedLengthB,selectedColourCode);

        okButton.setVisible(true);
        welcomeLabel1.setVisible(true);
        welcomeLabel2.setVisible(true);
        welcomeLabel1.setText("Price: $" + priceList[index] +".");
        welcomeLabel2.setText("Press 'OK' to buy.");
    }

    public void setOkButton() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        int balance = currentPlayer.getAccount();

        if(balance < 20) {
            showAlertWindow("You don't have enough money.");
        }
        else{
            Food newFood = (Food) Class.forName("Pets." + foodList[index]).newInstance();
            int newToyPrice = newFood.getPrice();
            if (balance < newToyPrice) {
                showAlertWindow("Sorry, you don't have enough money for " + newFood.getName() +".");
            }
            else{
                currentPlayer.getMyFoodList().add(newFood);
                currentPlayer.setAccount(currentPlayer.getAccount() - newFood.getPrice());
                currentPlayer.setScore(currentPlayer.getScore()+10);
                showInfoWindow("Congrats, you got a " + newFood.getName());
            }
        }
        resetSelectedButton(selectedButton,originalLengthB,originalLengthB);
        okButton.setVisible(false);
        welcomeLabel1.setVisible(false);
        welcomeLabel2.setVisible(false);
    }
}
