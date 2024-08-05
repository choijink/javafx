package com.itgroup.carproject.jdbc;


import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;

import java.util.Scanner;

public class UpdateMain {
    public static void main(String[] args) {
        CarDao dao = new CarDao();
        Car bean = new Car();

        Scanner scan = new Scanner(System.in);
        System.out.println("차량 번호 : ");
        int pnum = scan.nextInt();

        System.out.println("상품 이름 : ");
        String name = scan.next();

        bean.setPnum(pnum);
        bean.setName(name);
        bean.setCompany("기아자동차");
        bean.setImage01("aa.png");
        bean.setImage02("bb.png");
        bean.setStock(9999);
        bean.setPrice(1111);
        bean.setCategory("hev");
        bean.setContents("별로 빠르지 않아요");
        bean.setPoint(15);
        bean.setProductiondate("2024/06/17");

        int cnt = -1 ;
        cnt = dao.updateData(bean);

        if (cnt == -1 ){
            System.out.println("정보 수정에 실패하였습니다.");
        }else {
            System.out.println("정보 수정에 성공하였습니다.");
        }
    }
}
