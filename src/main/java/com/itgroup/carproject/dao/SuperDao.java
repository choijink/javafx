package com.itgroup.carproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SuperDao {
    private String driver = null;
    private String url = null;
    private String id = null;
    private String password = null;

    public SuperDao() {
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.url = "jdbc:oracle:thin:@localhost:1521:xe";
        this.id = "cars";
        this.password = "kiacar";
        try {
            Class.forName(driver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected Connection getConnection(){
        Connection cnec = null;
        try{
            cnec = DriverManager.getConnection(url, id, password);
            if (cnec != null){

            }else{
                System.out.println("접속 실패");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cnec ;
    }
}
