package Game_FX;

import Pets.Pet;
import Pets.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

import java.util.Optional;

public class Utils {

    static String selectedColourCode = "-fx-background-color:eee6b4";
    static String originalColourCode = "-fx-background-color:ffffff";
//    big pattern for buttons
    static int selectedLengthB = 110;
    static int originalLengthB = 100;
//    small pattern for buttons
    static int selectedLengthS = 79;
    static int originalLengthS = 75;

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    static void changeButtonEffect(Button button){
        final String IDLE_BUTTON_STYLE = "-fx-background-color: #EEA1CB;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color:  #eee6b4; -fx-effect: dropshadow( three-pass-box, rgba(0, 0, 0, 0.6), 5, 0.0, 0, 1); ";
        button.setStyle(IDLE_BUTTON_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(HOVERED_BUTTON_STYLE));
        button.setOnMouseExited(e -> button.setStyle(IDLE_BUTTON_STYLE));
    }

    static void resetSelectedButton(Button selectedButton, int originalLength, int originalLength1){
        if(selectedButton != null) Utils.setSelectedButton(selectedButton,originalLength,originalLength1,originalColourCode);
    }

    static void setSelectedButton(Button button, int length, int length2, String colourCode){
        button.setPrefWidth(length);
        button.setPrefHeight(length2);
        button.setStyle(colourCode);
    }

    static void popWarningWindow(Player currentPlayer){

        StringBuilder warningInfo = new StringBuilder();

        for(Pet pet : currentPlayer.getMyPetList()){
            if (!pet.isAlive()) {
                String deathReason = pet.deathReason(pet);
                showAlertWindow(deathReason);
//              if pet is dead, check if there is any revival times
                if (pet.getReviveTimes() != 0) {
                    reviveAlertWindow(pet);
                }else{
                    showInfoWindow("Sorry, " + pet.getName() + " is DEAD.\n");
                }
            }
//          if pet alive, check if it is mad or sick
            else{
                if (!pet.isSick() && !pet.isMad()) return;
                else{
                    if (pet.isMad()) warningInfo.append("Warning: ").append(pet.getName()).append(" is MAD!!!\n");
                    if (pet.isSick())  warningInfo.append("Warning:  ").append(pet.getName()).append(" is SICK!!!\n");
                    showAlertWindow(warningInfo.toString());
                }
            }
        }
    }

    static void showInfoWindow(String content){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.initModality(Modality.WINDOW_MODAL);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText(content);
        a.showAndWait();
    }

    public static void showAlertWindow(String content){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.initModality(Modality.WINDOW_MODAL);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText(content);
        a.showAndWait();
    }

    private static void reviveAlertWindow(Pet pet){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.initModality(Modality.WINDOW_MODAL);
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("Do you want to revive for " + pet.getName() + " ?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            pet.revive();
        }else{
            pet.setCompletelyDeadFlag(1);
        }
    }

    static void updatePetsButtonPic(Player currentPlayer, ImageView petPic1, ImageView petPic2, ImageView petPic3){
        String petPicURL = "Images/question-mark.png";

        if(currentPlayer.getMyPetList().size()!=0){
            for(int i=0; i < currentPlayer.getMyPetList().size();i++){
                String species = currentPlayer.getMyPetList().get(i).getSpecies();
                switch(species){
                    case("Dog"):{
                        petPicURL = "../Images/dog.png";
                        break;
                    }
                    case("Cat"):{
                        petPicURL = "../Images/cat.png";
                        break;
                    }
                    case("Koala"):{
                        petPicURL = "../Images/koala1.png";
                        break;
                    }
                    case("Panda"):{
                        petPicURL = "../Images/panda.png";
                        break;
                    }
                    case("GuineaPig"):{
                        petPicURL = "../Images/guinea-pig1.png";
                        break;
                    }
                    case("Alpaca"):{
                        petPicURL = "../Images/alpaca.png";
                    }
                }
                if(i==0){
                    petPic1.setImage(new Image(Utils.class.getResourceAsStream(petPicURL)));
                }else if(i==1){
                    petPic2.setImage(new Image(Utils.class.getResourceAsStream(petPicURL)));
                }else if(i==2){
                    petPic3.setImage(new Image(Utils.class.getResourceAsStream(petPicURL)));
                }
            }
        }
    }
}
