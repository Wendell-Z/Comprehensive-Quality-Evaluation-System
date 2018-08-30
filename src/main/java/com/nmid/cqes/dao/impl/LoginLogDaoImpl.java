package com.nmid.cqes.dao.impl;

import com.nmid.cqes.dao.LoginLogDao;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class LoginLogDaoImpl implements LoginLogDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FormatData formatData;
    @Autowired
    private LoginLog loginLog;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FormatData updateLogin(String lastIp, int stuId) {
        String sqlStr = " UPDATE loginlog SET lastIp= ?, lastLogin = CURRENT_TIME WHERE stuId = ? ";
        Object[] params = new Object[]{lastIp, stuId};
        try {
            this.jdbcTemplate.update(sqlStr, params, new int[]{Types.VARCHAR, Types.INTEGER});
            formatData.set200("更新登录IP成功！");
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }

    @Override
    public FormatData insertLogin(String lastIp, int stuId) {
        String sqlStr = " INSERT INTO loginlog (stuId, lastIp) VALUES ( ? , ?) ";
        Object[] params = new Object[]{stuId, lastIp};
        try {
            jdbcTemplate.update(sqlStr, params, new int[]{Types.INTEGER, Types.VARCHAR});
            formatData.set200("更新登录IP成功！");
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }

    @Override
    public FormatData getStuLoginLog(int stuId) {
        String sqlStr = " SELECT * FROM loginlog WHERE stuId = ? ";
        try {
            jdbcTemplate.query(sqlStr, new Object[]{stuId}, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    loginLog.setStuId(rs.getInt("stuId"));
                    loginLog.setLoginId(rs.getInt("id"));
                    loginLog.setLastIp(rs.getString("lastIp"));
                    loginLog.setLastLogin(rs.getTimestamp("lastLogin"));
                    System.out.println(loginLog.getLastLogin());
                }
            });
            formatData.set200(loginLog);
            return formatData;
        } catch (Exception e) {
            formatData.set500();
            return formatData;
        }
    }
}
