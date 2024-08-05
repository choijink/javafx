package com.itgroup.carproject.jdbc;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;

import java.util.List;
import java.util.Scanner;

public class SelectBranchCategory {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("all, gasoline, diesel, hev, ev 중 1개 입력");

        String category = scan.next();

        CarDao dao = new CarDao();
        List<Car> allCar = dao.selectByCategory(category);
        System.out.println("차량 수 : " + allCar.size());

        for (Car bean : allCar){
            ShowData.printBean(bean);
        }
    }
}
