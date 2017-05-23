package com.yonghui.portal.service.impl.test;

import java.sql.*;

public class TestSapJDBC {

    private static String driverName = "com.sap.db.jdbc.Driver";

    public static void main(String[] args)
            throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection("jdbc:sap://10.0.30.101:31015?reconnect=true ", "BRAVO", "Qwe12344");
        Statement stmt = con.createStatement();
        ResultSet res = null;
        String sql = "select top 10 * from \"BRAVO\".\"DIM_CATEGORY\"";
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
        }
    }
}