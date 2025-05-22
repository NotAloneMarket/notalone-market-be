package com.ddwu.notalonemarket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class DbTestDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct  // 애플리케이션 시작 후 자동 실행
    public void testConnection() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT sysdate FROM dual")) {

            if (rs.next()) {
                System.out.println("✅ DB 연결 성공! 현재 시간: " + rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("❌ DB 연결 실패: " + e.getMessage());
        }
    }
}
