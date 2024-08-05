package com.itgroup.carproject.controller;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;
import com.itgroup.carproject.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CarInsertController implements Initializable {

    @FXML
    private TextField fxmlName;
    @FXML
    private TextField fxmlCompany;
    @FXML
    private TextField fxmlImage01;
    @FXML
    private TextField fxmlImage02;
    @FXML
    private TextField fxmlStock;
    @FXML
    private TextField fxmlPrice;
    @FXML
    private ComboBox fxmlCategory;
    @FXML
    private TextField fxmlContents;
    @FXML
    private TextField fxmlPoint;
    @FXML
    private DatePicker fxmlProductiondate;

    Car bean = null; // 상품 1개를 의미하는 빈 클래스
    private CarDao dao;

    public void oncarInsert(ActionEvent event) {
        // 기입한 상품 목록을 데이터 베이스에 추가합니다.
        // event 객체는 해당 이벤트를 발생시킨 객체입니다.
        System.out.println(event);
        boolean bool = validationCheck();
        if (bool == true) {
            int cnt = -1 ;
            cnt = insertDatabase();
            if (cnt == 1){ // 인서트 성공시
                Node source = (Node) event.getSource(); // 강등
                Stage stage = (Stage) source.getScene().getWindow(); // 강등
                stage.close(); // 현재 창을 닫습니다.
            }
        } else {
            System.out.println("등록 실패");
        }
    }

    private int insertDatabase() {
        // 1건의 데이터인 bean을 dao를 사용하여 데이터 베이스에 추가합니다.
        int cnt = -1;
        cnt = dao.insertData(this.bean);
        if (cnt != 1) {
            String[] message = new String[]{"차량 등록", "길이 제한 위배", "차량 등록을 실패하였습니다."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
        return cnt ;
    }

    private boolean validationCheck() {
        // 유효성 검사를 통화하면 true가 됩니다.
        String[] message = null;

        String name = fxmlName.getText().trim();
        if (name.length() <= 2 || name.length() >= 15) {
            message = new String[]{"유효성 검사 : 이름", "길이 제한 위배", "이름은 3글자 이상 10글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false; // 순차적으로 나오려면 꼭 적어라
        }

        String company = fxmlCompany.getText().trim();
        if (company.length() <= 2 || company.length() >= 16) {
            message = new String[]{"유효성 검사 : 제조 회사", "길이 제한 위배", "이름은 3글자 이상 15글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false; // 순차적으로 검사하려면 꼭 적어라
        }

        String image01 = fxmlImage01.getText().trim();

        if (image01 == null || image01.length() < 5) {
            message = new String[]{"유효성 검사 : 이미지01", "필수 입력 체크", "1번 이미지는 필수 입력 사항입니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false; // 순차적으로 나오려면 꼭 적어라
        }

        boolean bool = false;
        // startsWith()와 endsWith()
        bool = image01.endsWith(".jpg") || image01.endsWith(".png");
        if (!bool) {
            message = new String[]{"유효성 검사 : 이미지01", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false; // 순차적으로 나오려면 꼭 적어라
        }

        String image02 = fxmlImage02.getText().trim();

        if (image02.length() == 0) { // 사용자가 2번 이미지를 입력하지 않는 경우
            image02 = null;
        }

        if (image02 != null) {
            bool = image02.endsWith(".jpg") || image02.endsWith(".png");
            if (!bool) {
                message = new String[]{"유효성 검사 : 이미지02", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이어야 합니다."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false; // 순차적으로 나오려면 꼭 적어라
            }
        }

        int stock = 0;
        try {
            String _stock = fxmlStock.getText().trim();
            stock = Integer.valueOf(_stock);

            if (stock < 10 || stock > 100) {
                message = new String[]{"유효성 검사 : 재고", "허용 숫자 위반", "재고는 10개 이상 100개 이하로 입력해주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 재고", "무효한 숫자 형식", "올바른 숫자 형식을 입력해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int price = 0;
        try {
            String _price = fxmlPrice.getText().trim();
            price = Integer.valueOf(_price);

            if (price < 1000 || price > 10000) {
                message = new String[]{"유효성 검사 : 단가", "단가 범위 위반", "단가는 1000(만)원 이상 10000(만)원 이하로 입력해주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 단가", "무효한 숫자 형식", "올바른 숫자 형식을 입력해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int selectedIndex = fxmlCategory.getSelectionModel().getSelectedIndex();
        String category = null;

        if (selectedIndex == 0) {
            message = new String[]{"유효성 검사 : 카테고리", "미선택", "원하시는 카테고리를 반드시 선택해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        } else {
            category = (String) fxmlCategory.getSelectionModel().getSelectedItem();
            System.out.println("선택된 항목");
            System.out.println(category);
        }

        String contents = fxmlContents.getText().trim();
        if (contents.length() <= 4 || contents.length() >= 31) {
            message = new String[]{"유효성 검사 : 차량 설명", "길이 제한 위배", "이름은 5글자 이상 30글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int point = 0;
        try {
            String _point = fxmlPoint.getText().trim();
            point = Integer.valueOf(_point);

            if (point < 30 || point > 100) {
                message = new String[]{"유효성 검사 : 포인트", "포인트 범위 위반", "포인트는 30(만)점 이상 100(만)점 이하로 입력해주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            } else {
                String selectedItem = (String) fxmlCategory.getSelectionModel().getSelectedItem();
                System.out.println("선택된 항목");
                System.out.println(selectedItem);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 포인트", "무효한 숫자 형식", "올바른 숫자 형식을 입력해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        LocalDate _productiondate = fxmlProductiondate.getValue();

        String productiondate = "";
        if (_productiondate == null) {
            message = new String[]{"유효성 검사 : 생산일자", "무효한 날짜 형식", "올바른 생산 일자 형식을 입력해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        } else {
            productiondate = _productiondate.toString();
            productiondate = productiondate.replace("-", "/"); // -를 / 바꾸는 스트링 메소드
        }

        // 유효성 검사가 통과되면 비로소 객체 생성합니다.
        this.bean = new Car();
//        bean.setPnum(0); // 시퀀스가 처리해줌
        bean.setName(name);
        bean.setCompany(company);
        bean.setImage01(image01);
        bean.setImage02(image02);
        bean.setStock(stock);
        bean.setPrice(price);

        // 사용자가 입력한 key인 한글 카테고리 이름을 value인 영문으로 변환시켜 셋팅합니다.
        bean.setCategory(Utility.getCategoryName(category, "value"));
        bean.setContents(contents);
        bean.setPoint(point);
        bean.setProductiondate(productiondate);

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new CarDao();

        //최초 시작시 콤보 박스의 0번째 항목 선택하기
        fxmlCategory.getSelectionModel().select(0);

    }
}
