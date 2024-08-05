package com.itgroup.carproject.dao;

import com.itgroup.carproject.bean.Car;
import com.itgroup.carproject.utility.Paging;
import oracle.ucp.ConnectionRetrievalInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDao extends SuperDao{

    public CarDao() {
        super();
    }

    public List<Car> selectAll() {
        Connection conn = null;
        String sql = "select * from cars order by pnum desc ";
        PreparedStatement pstmt = null ;
        ResultSet rs = null;

        List<Car> allData = new ArrayList<>();
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Car bean = this.makeBean(rs);
                allData.add(bean);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if (rs != null){
                    rs.close();
                }if (pstmt != null){
                    pstmt.close();
                }if (conn != null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return allData;
    }

    private Car makeBean(ResultSet rs) {
        Car bean = new Car();
        try {
            bean.setPnum(rs.getInt("pnum"));
            bean.setName(rs.getString("name"));
            bean.setCompany(rs.getString("company"));
            bean.setImage01(rs.getString("image01"));
            bean.setImage02(rs.getString("image02"));
            bean.setStock(rs.getInt("stock"));
            bean.setPrice(rs.getInt("price"));
            bean.setCategory(rs.getString("category"));
            bean.setContents(rs.getString("contents"));
            bean.setPoint(rs.getInt("point"));
            bean.setProductiondate(String.valueOf(rs.getDate("productiondate")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bean;
    }

    public Car selectByPK(int pnum) {
        Connection conn = null;
        String sql = " select * from cars ";
        sql += " where pnum = ? ";
        PreparedStatement pstmt = null ;
        ResultSet rs = null;

        Car bean = null;
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pnum);
            rs = pstmt.executeQuery();

            if(rs.next()){
                bean = this.makeBean(rs);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return bean ;
    }


    public int getTotalCount(String category) {
        int totalCount = 0 ;

        String sql = "select count (*) as mycnt from cars ";
        boolean bool = category == null || category.equals("all");
        if (!bool){
            sql += " where category = ? ";
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool){
                pstmt.setString(1, category);
            }
            rs = pstmt.executeQuery();

            if (rs.next()){
                totalCount = rs.getInt("mycnt");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try{
                if (rs != null){
                    rs.close();
                }if (pstmt != null){
                    pstmt.close();
                }if (conn != null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public List<Car> getPaginationData(Paging pageInfo) {
        Connection conn = null;
        String sql = " select pnum, name, company, image01, image02, stock, price, category, contents, point, productiondate ";
        sql += " from ( ";
        sql += " select pnum, name, company, image01, image02, stock, price, category, contents, point, productiondate, ";
        sql += " rank() over(order by pnum desc) as ranking ";
        sql += " from cars ";

        String mode = pageInfo.getMode();
        boolean bool = mode.equals(null) || mode.equals("null") || mode.equals("") || mode.equals("all");

        if (!bool){
            sql += "where category = ? ";
        }
        sql += " ) ";
        sql += " where ranking between ? and ? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null ;

        List<Car> allData = new ArrayList<>();
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, mode);
                pstmt.setInt(2, pageInfo.getBeginRow());
                pstmt.setInt(3, pageInfo.getEndRow());
            }else{
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2, pageInfo.getEndRow());
            }
            rs = pstmt.executeQuery();

            while (rs.next()){
                Car bean = this.makeBean(rs);
                allData.add(bean);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if (rs != null){
                    rs.close();
                }if (pstmt != null){
                    pstmt.close();
                }if (conn != null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return allData;
    }

    public List<Car> selectByCategory(String category) {
        Connection conn = null;

        String sql = " select * from cars ";

        boolean bool = category ==null || category.equals("all");
        if (!bool){
            sql += " where category = ? ";
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Car> allData = new ArrayList<>();
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (!bool){
                pstmt.setString(1, category);
            }

            rs = pstmt.executeQuery();

            while (rs.next()){
                Car bean = this.makeBean(rs);
                allData.add(bean);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if (rs != null){
                    rs.close();
                }
                if (pstmt != null){
                    pstmt.close();
                }if (conn != null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        return allData;
    }

    public int insertData(Car bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " insert into cars(pnum, name, company, image01, image02, stock, price, category, contents, point, productiondate)";

        sql += " values(seqcars.nextval,?,?,?,?,?,?,?,?,?,?)";


        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCompany());
            pstmt.setString(3, bean.getImage01());
            pstmt.setString(4, bean.getImage02());
            pstmt.setInt(5, bean.getStock());
            pstmt.setInt(6, bean.getPrice());
            pstmt.setString(7, bean.getCategory());
            pstmt.setString(8, bean.getContents());
            pstmt.setInt(9, bean.getPoint());
            pstmt.setString(10, bean.getProductiondate());

            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }


    public int updateData(Car bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " update cars set name = ?, company = ?, image01 = ?, image02 = ?, stock = ?, price = ?, category = ?, contents = ?, point = ?, productiondate = ? ";

        sql += " where pnum = ?";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCompany());
            pstmt.setString(3, bean.getImage01());
            pstmt.setString(4, bean.getImage02());
            pstmt.setInt(5, bean.getStock());
            pstmt.setInt(6, bean.getPrice());
            pstmt.setString(7, bean.getCategory());
            pstmt.setString(8, bean.getContents());
            pstmt.setInt(9, bean.getPoint());
            pstmt.setString(10, bean.getProductiondate());
            pstmt.setInt(11, bean.getPnum());

            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public int deleteData(int pnum) {
        System.out.println("기본 키=" + pnum);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " delete from cars ";
        sql += " where pnum = ? ";

        try{
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,pnum);

            cnt = pstmt.executeUpdate();

            conn.commit();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if (pstmt != null){
                    pstmt.close();
                }if (conn != null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return cnt;
    }
}
