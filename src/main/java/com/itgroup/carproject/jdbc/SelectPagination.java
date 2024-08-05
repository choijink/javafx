package com.itgroup.carproject.jdbc;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.dao.CarDao;
import com.itgroup.carproject.utility.Paging;

import java.util.List;
import java.util.Scanner;

public class SelectPagination {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("몇 페이지 볼거니?");
        String pageNumber = scan.next();

        System.out.println("페이지 당 몇 건씩 볼꺼니?");
        String pageSize = scan.next();

        System.out.println("all, gasoline, diesel, hev, ev 중 1개 입력");
        String mode = scan.next();

        CarDao dao = new CarDao();
        int totalCount = dao.getTotalCount(mode);

        String url = "carList.jsp";
        String keyword = "";

        Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, mode, keyword);
        pageInfo.displayInformation();

        List<Car> carList = dao.getPaginationData(pageInfo);

        for (Car bean : carList){
            ShowData.printBean(bean);
        }
    }
}
