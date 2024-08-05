package com.itgroup.carproject.utility;

import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static final String FXML_PATH = "/com/itgroup/carproject/fxml/";
    public static final String IMAGE_PATH = "/com/itgroup/carproject/images/";

    public static Map<String, String> categorymap = new HashMap<>();

    private static String makeMapData(String category, String mode){
        categorymap.put("휘발유", "gasoline");
        categorymap.put("경유", "diesel");
        categorymap.put("하이브리드", "hev");
        categorymap.put("전기", "ev");

        if (mode.equals("value")){
            return categorymap.get(category);
        }else{
            for (String key : categorymap.keySet()){
                if (categorymap.get(key).equals(category)){
                    return key;
                }
            }
        }
        return null;
    }

    public static String getCategoryName(String category, String mode) {
        return makeMapData(category, mode);
    }

    public static void showAlert(Alert.AlertType alertType, String[] message){
        Alert alert = new Alert(alertType);
        alert.setTitle(message[0]);
        alert.setHeaderText(message[1]);
        alert.setContentText(message[2]);
        alert.showAndWait();
    }

    public static LocalDate getDatePicker(String productionDate) {
        System.out.println(productionDate);

        int year = Integer.valueOf(productionDate.substring(0, 4));
        int month = Integer.valueOf(productionDate.substring(5, 7));
        int day = Integer.valueOf(productionDate.substring(8));
        return LocalDate.of(year, month, day);
    }
}


