package com.itgroup.carproject.jdbc;

import com.itgroup.carproject.bean.Car;

public class ShowData {

    public static void printBean(Car bean){
        int pnum = bean.getPnum() ;
        String name = bean.getName();
        String company = bean.getCompany();
        String image01 = bean.getImage01();
        String image02 = bean.getImage02();
        int stock = bean.getStock();
        int price = bean.getPrice();
        String category = bean.getCategory();
        String contents = bean.getContents();
        int point = bean.getPoint();
        String productiondate = bean.getProductiondate();


        System.out.println("상품 번호 : " + pnum);
        System.out.println("상품명 : " + name);
        System.out.println("제조 회사 : " + company);
        System.out.println("이미지01 : " + image01);
        System.out.println("이미지02 : " + image02);
        System.out.println("재고 : " + stock);
        System.out.println("단가 : " + price);
        System.out.println("카테고리 : " + category);
        System.out.println("상품 설명 : " + contents);
        System.out.println("적립 포인트 : " + point);
        System.out.println("입고 일자 : " + productiondate);
        System.out.println("===============================");
    }
}
