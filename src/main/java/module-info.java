module com.itgroup.carproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ucp;


//    opens com.itgroup.carproject to javafx.fxml;
//    exports com.itgroup.carproject;
    exports com.itgroup.carproject.www;
    opens com.itgroup.carproject.www to javafx.fxml;

    // JavaFX 애플리케이션에서 발생하는 접근 제어 문제
    // graphics 모듈이 application 폴더를 접근할 수 있도록 처리합니다.
    exports com.itgroup.carproject.application to javafx.graphics;
//
//    // 모듈 com.itgroup.www가 com.itgroup.controller 패키지를 javafx.fxml 모듈에 공개합니다.
    opens com.itgroup.carproject.controller to javafx.fxml;
//
//    opens com.itgroup.bean to javafx.base;
    opens com.itgroup.carproject.application to javafx.fxml;

    opens com.itgroup.carproject.bean to javafx.base;

    // fxml 모듈이 com.itgroup.controller 패키지에 접근이 가능하도록 설정합니다.
//    exports com.itgroup.controller to javafx.fxml;

}