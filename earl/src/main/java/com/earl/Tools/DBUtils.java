package com.earl.Tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    static final String jdbc_driver = "com.mysql.jdbc.Driver";

    private String dbUrl;
    private String user;
    private String pass;
    private static  Connection connection;
    private static  Statement statement;

    public DBUtils(String dbUrl,String user,String pass){
        this.dbUrl = dbUrl;
        this.user = user;
        this.pass = pass;
        try {
            Class.forName(jdbc_driver);
            connection = DriverManager.getConnection(dbUrl, user, pass);
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Map<String,Object>> selectList(String sql){

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Map<String,Object>> rs = new ArrayList<>();
            ResultSetMetaData data = resultSet.getMetaData();
            int columnCount = data.getColumnCount();
            while (resultSet.next()){

                Map<String,Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                     map.put(data.getColumnLabel(i),resultSet.getObject(i));
                }
                rs.add(map);
            }
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Long selectCount(String sql){

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                return resultSet.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0l;
    }

    public static void close(){
        try {
            if(statement!=null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDbUrl() {
        return dbUrl;
    }

    private void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    private void setStatement(Statement statement) {
        this.statement = statement;
    }
}
