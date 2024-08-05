package com.itgroup.carproject.controller;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;
import com.itgroup.carproject.utility.Paging;
import com.itgroup.carproject.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CarsViewcontroller implements Initializable {
    public ObservableList<Car> dataList = null;
    @FXML
    private ImageView imageView;
    private CarDao dao = null;

    private String mode = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new CarDao();

        setTableColumns();
        setPagination(0);

        ChangeListener<Car> tableListener = new ChangeListener<Car>() {
            @Override
            public void changed(ObservableValue<? extends Car> observableValue, Car oldValue, Car newValue) {
                if (newValue != null) {
                    System.out.println("차량 정보");
                    System.out.println(newValue);

                    String imageFile = "";
                    if (newValue.getImage01() != null) {
                        imageFile = Utility.IMAGE_PATH + newValue.getImage01().trim();
                    } else {
                        imageFile = Utility.IMAGE_PATH + "noimage.jpg";
                    }

                    Image someImage = null;
                    if (getClass().getResource(imageFile) == null) {
                        imageView.setImage(null);
                    } else {
                        someImage = new Image(getClass().getResource(imageFile).toString());
                        imageView.setImage(someImage);
                    }
                }
            }
        };
        carsTable.getSelectionModel().selectedItemProperty().addListener(tableListener);

    }

    public Pagination pagination;

    private void setPagination(int pageIndex) {
        pagination.setPageFactory(this::createPage);
        pagination.setCurrentPageIndex(pageIndex);

        imageView.setImage(null);
    }

    private Node createPage(Integer pageIndex) {
        String mode = null;

        int totalCount = 0;
        totalCount = dao.getTotalCount(this.mode);

        Paging pageInfo = new Paging(String.valueOf(pageIndex + 1), "10", totalCount, null, this.mode, null);

        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);

        VBox vbox = new VBox(carsTable);

        return vbox;
    }

    @FXML
    private Label pageStatus;

    private void fillTableData(Paging pageInfo) {
        List<Car> carList = dao.getPaginationData(pageInfo);

        dataList = FXCollections.observableArrayList(carList);

        carsTable.setItems(dataList);
        pageStatus.setText(pageInfo.getPagingStatus());
    }

    @FXML
    private TableView<Car> carsTable;

    private void setTableColumns() {
        String[] fields = {"pnum", "name", "company", "category", "stock", "productiondate"};
        String[] colNames = {"차량 번호", "차량 이름", "제조사", "카테고리", "재고", "생산일자"};

        TableColumn tableColumn = null;

        for (int i = 0; i < fields.length; i++) {
            tableColumn = carsTable.getColumns().get(i);
            tableColumn.setText(colNames[i]);

            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));
        }
    }

    @FXML
    private ComboBox<String> fieldSearch;

    public void choiceSelect(ActionEvent event) {
        String category = fieldSearch.getSelectionModel().getSelectedItem();
        System.out.println("필드 검색 모드 : [" + category + "]");

        this.mode = Utility.getCategoryName(category, "value");
        System.out.println("필드 검색 모드 : [" + mode + "]");

        setPagination(0);
    }

    public void onInsert(ActionEvent event) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "CarInsert.fxml";

        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load();

        Scene scene = new Scene(container);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("차량 등록하기");
        stage.showAndWait();

        setPagination(0);

    }

    public void onUpdate(ActionEvent event) throws Exception {
        int idx = carsTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0) {
            System.out.println("선택된 색인 번호 : " + idx);

            String fxmlFile = Utility.FXML_PATH + "CarUpdate.fxml";
            URL url = getClass().getResource(fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent container = fxmlLoader.load();

            Car bean = carsTable.getSelectionModel().getSelectedItem();

            CarUpdateController controller = fxmlLoader.getController();
            controller.setbean(bean);

            Scene scene = new Scene(container);
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("차량 정보 수정하기");
            stage.showAndWait();

            setPagination(0);
        } else {
            String[] message = new String[]{"차량 선택 확인", "차량 미선택", "수정하고자 하는 차량을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void onDelete(ActionEvent event) {
        int idx = carsTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제 확인 메세지");
            alert.setHeaderText("삭제 항목 선택 대화 상자");
            alert.setContentText("이 항목을 삭제 하시겠습니까?");

            Optional<ButtonType> response = alert.showAndWait();

            if (response.get() == ButtonType.OK){
                Car bean = carsTable.getSelectionModel().getSelectedItem();
                int pnum = bean.getPnum();
                int cnt = -1 ;
                cnt = dao.deleteData(pnum);

                if (cnt != -1){
                    System.out.println("삭제 성공");
                    setPagination(0);

                }else {
                    System.out.println("삭제 실패");
                }
            }else {
                System.out.println("삭제를 취소하였습니다.");
            }
        }else {
            String[] message = new String[]{"삭제할 목록 확인", "삭제할 대상 미선택", "삭제할 행을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }
    }

    public void onSaveFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        Button myBtn = (Button) event.getSource();
        Window window = myBtn.getScene().getWindow();
        File savedFile = chooser.showSaveDialog(window);
        if (savedFile != null){
            FileWriter fw = null;
            BufferedWriter bw = null;
            try{
                fw = new FileWriter(savedFile);
                bw = new BufferedWriter(fw); // 승급

                for (Car bean : dataList){
                    bw.write(bean.toString());
                    bw.newLine(); // 엔터키
                }

                System.out.println("파일 저장이 완료되었습니다.");
            }catch(Exception ex){
                ex.printStackTrace();

            }finally {
                try{
                    if(bw!=null){bw.close();}
                    if(fw!=null){fw.close();}
                }catch (Exception ex2){
                    ex2.printStackTrace();
                }
            }

        }else{
            System.out.println("파일 저장이 취소 되었습니다.");
        }
    }

    public void onClosing(ActionEvent event) {
        System.out.println("프로그램을 종료합니다.");
        Platform.exit();
    }
}

