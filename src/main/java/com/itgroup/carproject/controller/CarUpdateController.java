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

public class CarUpdateController implements Initializable {

    @FXML
    private TextField fxmlPnum; // 상품 번호인 이 항목은 숨김 처리 예정입니다.
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
    private ComboBox<String> fxmlCategory;
    @FXML
    private TextField fxmlContents;
    @FXML
    private TextField fxmlPoint;
    @FXML
    private DatePicker fxmlProductiondate;
    @FXML
    private TextField fxmlName;

    private Car oldBean = null; // 수행될 행의 정보
    private Car newBean = null; // 데이터 베이스의 수정할 bean 객체

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("수정 하자");
    }

    public void setbean(Car bean) {
        this.oldBean = bean;

        fillPreviousData();

        fxmlPnum.setVisible(false);
    }

    private void fillPreviousData() {

        fxmlPnum.setText(String.valueOf(this.oldBean.getPnum()));
        fxmlName.setText(this.oldBean.getName());
        fxmlCompany.setText(this.oldBean.getCompany());
        fxmlImage01.setText(this.oldBean.getImage01());
        fxmlImage02.setText(this.oldBean.getImage02());
        fxmlStock.setText(String.valueOf(this.oldBean.getStock()));
        fxmlPrice.setText(String.valueOf(this.oldBean.getPrice()));

        String category = this.oldBean.getCategory();
        fxmlCategory.setValue(Utility.getCategoryName(category, "key"));

        fxmlContents.setText(this.oldBean.getContents());
        fxmlPoint.setText(String.valueOf(this.oldBean.getPoint()));

        String productiondate = this.oldBean.getProductiondate();
        if (productiondate == null || productiondate.equals("null")) {

        } else {
            fxmlProductiondate.setValue((Utility.getDatePicker(productiondate)));
        }
    }

    public void onCarUpdate(ActionEvent event) {
        // 먼저, 유효성 검사를 진행합니다.
        boolean bool = validationCheck();

        // 사용자가 변경한 내역을 데이터베이스에 업데이트 시킵니다.
        if (bool == true) {
            CarDao dao = new CarDao();
            int cnt = -1; //-1이면 실패
            cnt = dao.updateData(this.newBean);

            if (cnt == -1) {
                System.out.println("수정 실패");

            } else { // 수정이 되었으므로 창을 닫습니다.
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } else {
            System.out.println("유효성 검사를 통과하지 못했습니다.");
        }
    }

    private boolean validationCheck() {
        // 유효성 검사를 통화하면 true가 됩니다.
        // 수정을 위한 핵심 키(primary key)
        int pnum = Integer.valueOf(fxmlPnum.getText().trim());
        String[] message = null;

        String name = fxmlName.getText().trim();
        if (name.length() <= 1 || name.length() >= 15) {
            message = new String[]{"유효성 검사 : 이름", "길이 제한 위배", "이름은 2글자 이상 14글자 이하이어야 합니다."};
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
        bool = image01.endsWith(".jpg") || image01.endsWith(".png");
        if (!bool) {
            message = new String[]{"유효성 검사 : 이미지01", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false; // 순차적으로 나오려면 꼭 적어라
        }

        String image02 = null;

        if (fxmlImage02.getText() != null && fxmlImage02.getText().length() != 0) {
            image02 = fxmlImage02.getText().trim();

            bool = image02.endsWith(".jpg") || image02.endsWith(".png");
            if (!bool) {
                message = new String[]{"유효성 검사 : 이미지02", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이어야 합니다."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
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
                message = new String[]{"유효성 검사 : 단가", "단가 범위 위반", "단가는 1000원 이상 10000원 이하로 입력해주세요."};
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
            category = fxmlCategory.getSelectionModel().getSelectedItem();
            System.out.println("선택된 항목");
            System.out.println(category);
        }

        String contents = fxmlContents.getText().trim();
        if (contents.length() <= 4 || contents.length() >= 100) {
            message = new String[]{"유효성 검사 : 상품 설명", "길이 제한 위배", "이름은 5글자 이상 30글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int point = 0;
        try {
            String _point = fxmlPoint.getText().trim();
            point = Integer.valueOf(_point);

            if (point < 10 || point > 200) {
                message = new String[]{"유효성 검사 : 포인트", "포인트 범위 위반", "포인트는 10만점 이상 100만점 이하로 입력해주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            } else {
                String selectedItem = fxmlCategory.getSelectionModel().getSelectedItem();
                System.out.println("선택된 항목");
                System.out.println(selectedItem);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 포인트", "무효한 숫자 형식", "올바른 숫자 형식을 입력해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        LocalDate _productionDate = fxmlProductiondate.getValue();

        String productionDate = "";
        if (_productionDate == null) {
            message = new String[]{"유효성 검사 : 생산일자", "무효한 날짜 형식", "올바른 생산 일자 형식을 입력해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        } else {
            productionDate = _productionDate.toString();
            productionDate = productionDate.replace("-", "/"); // -를 / 바꾸는 스트링 메소드
        }

        // 유효성 검사가 통과되면 비로소 객체 생성합니다.
        this.newBean = new Car();
        newBean.setPnum(pnum); // 중요) 이 상품 번호를 근거로 수정이 됩니다.
        newBean.setName(name);
        newBean.setCompany(company);
        newBean.setImage01(image01);
        newBean.setImage02(image02);
        newBean.setStock(stock);
        newBean.setPrice(price);

        newBean.setCategory(Utility.getCategoryName(category, "value"));
        newBean.setContents(contents);
        newBean.setPoint(point);
        newBean.setProductiondate(productionDate);

        return true;
    }
}
