package com.nmid.cqes.dao.impl;

import com.nmid.cqes.dao.StudentPrizesDao;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.StudentPrizes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class StudentPrizesDaoImpl implements StudentPrizesDao {
    private final static String STU_INFO_SQL = "SELECT id , stuId , " +
            "stuName , totalScores FROM stuinfo WHERE stuId = ? ";
    private final static String STU_EXISTS_SQL = " SELECT count(*) FROM stuinfo  " +
            " WHERE stuId =? ";
    @Autowired
    FormatData formatData;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentPrizes studentPrizes;
    @Autowired
    private PrizeInfoDaoImpl prizeInfoDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FormatData getStudentPrizes(int stuId) {

        try {
            if (1 == jdbcTemplate.queryForObject(STU_EXISTS_SQL, new Object[]{stuId}, Integer.class)) {
                jdbcTemplate.query(STU_INFO_SQL, new Object[]{stuId}, new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        studentPrizes.setID(rs.getInt("id"));
                        studentPrizes.setStuId(rs.getInt("stuId"));
                        studentPrizes.setStuName(rs.getString("stuName"));
                        studentPrizes.setTotalScores(rs.getShort("totalScores"));
                    }
                });
                studentPrizes.setPrizeInfos(prizeInfoDao.prizeinfos(stuId));
                formatData.set200(studentPrizes);
                return formatData;
            } else {
                formatData.setStatus("404");
                formatData.setInfo("Not Found");
                formatData.setData("用户不存在！");
                return formatData;
            }

        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }
}
