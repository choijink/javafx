package com.itgroup.carproject.application;

import com.itgroup.carproject.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarsMain extends Application {
    // 오늘은 날씨가 엄청 더워요.

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "CarsMain.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load(); // 승급
        Scene scene = new Scene(container);

//        String myStyle = getClass().getResource(Utility.CSS_PATH + "coffeeStyle.css").toString();
//        scene.getStylesheets().add(myStyle); // 스타일링 파일 지정하기

        stage.setTitle("Cars 프로그래밍");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
