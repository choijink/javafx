package com.itgroup.carproject.jdbc;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;

import java.util.List;

public class SelectAllMain {
    public static void main(String[] args) {
        CarDao dao = new CarDao();
        List<Car> allCar = dao.selectAll();
        System.out.println(allCar.size());

        for (Car bean : allCar){
            ShowData.printBean(bean);
        }
    }
}
