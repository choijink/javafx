package com.itgroup.carproject.jdbc;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;

import java.util.Scanner;

public class InsertMain {
    public static void main(String[] args) {
        CarDao dao = new CarDao();
        Car bean = new Car();

        Scanner scan = new Scanner(System.in);
        System.out.println("차량 이름 : ");
        String name = scan.nextLine();

        bean.setCompany("현대자동차");
        bean.setName(name);
        bean.setImage01("xx.jpg");
        bean.setImage02("yy.jpg");
        bean.setStock(300);
        bean.setPrice(6000);
        bean.setCategory("ev");
        bean.setContents("엄청 빨라요");
        bean.setPoint(0);
        bean.setProductiondate(null);

        int cnt = -1 ;
        cnt = dao.insertData(bean);

        if (cnt == -1){
            System.out.println("상품 등록에 실패하였습니다.");
        }else {
            System.out.println("상품 등록에 성공하였습니다.");
        }
    }
}
