package com.itgroup.carproject.bean;

public class Car {
    private int pnum ;
    private String name ;
    private String company ;
    private String image01 ;
    private String image02 ;
    private int stock ;
    private int price ;
    private String category ;
    private String contents ;
    private int point ;
    private String productiondate ;

    public Car() {
    }
    // 여기는 빈 클래스 입니다.
    @Override
    public String toString() {
        return "cars{" +
                "pnum=" + pnum +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", image01='" + image01 + '\'' +
                ", image02='" + image02 + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", contents='" + contents + '\'' +
                ", point=" + point +
                ", productiondate='" + productiondate + '\'' +
                '}';
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage01() {
        return image01;
    }

    public void setImage01(String image01) {
        this.image01 = image01;
    }

    public String getImage02() {
        return image02;
    }

    public void setImage02(String image02) {
        this.image02 = image02;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }
}
