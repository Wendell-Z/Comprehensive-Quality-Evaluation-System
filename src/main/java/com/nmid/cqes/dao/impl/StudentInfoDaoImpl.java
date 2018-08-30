package com.nmid.cqes.dao.impl;

import com.google.gson.Gson;
import com.nmid.cqes.dao.StudentInfoDao;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.StudentInfo;
import com.nmid.cqes.utils.EduStu;
import com.nmid.cqes.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

@Repository
public class StudentInfoDaoImpl implements StudentInfoDao {

    private final static String STU_EXISTS_SQL = " SELECT count(*) FROM stuinfo  " +
            " WHERE stuId =? ";
    private final static String MATCH_PWD_SQL = " SELECT pwd FROM stuinfo WHERE stuId = ? ";
    private final static String GET_STUDENT_INFO_SQL = " SELECT * "
            + " FROM stuinfo WHERE stuId =? ";
    private final static String STUDENT_INFO_LIST_SQL = " SELECT * FROM stuinfo ORDER BY stuId ";
    private final static String CLASS_STUDENT_SQL = "SELECT * FROM stuinfo WHERE classNum = ? ORDER BY stuId";
    private final static String INSERT_STU_INFO_SQL = " INSERT INTO stuinfo (stuId , pwd , " +
            "totalScores ,  stuName , gender , college , major , grade , class , classNum ) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?) ";
    private final static String CLASS_NUM_EXISTS_SQL = " SELECT count(*) FROM classes  " +
            " WHERE classNum = ? ";
    @Autowired
    private LoginLogDaoImpl loginLogDao;
    @Autowired
    private FormatData formatData;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 实现登陆，比较学号和密码
     * 先在数据库中查找，若没有则访问学校接口
     * 访问学校接口成功后将信息存入数据库
     *
     * @param stuId
     * @param pwd
     * @return
     */
    @Override
    public FormatData signIn(final int stuId, final String pwd, final String lastIp) {

        int i = 0;
        try {
            i = jdbcTemplate.queryForObject(STU_EXISTS_SQL, new Object[]{stuId}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }

        if (1 == i) {
            String s = "";
            try {
                s = jdbcTemplate.queryForObject(MATCH_PWD_SQL, new Object[]{stuId}, String.class);
            } catch (Exception e) {
                formatData.set500();
                return formatData;
            }

            if (pwd.equals(s)) {
                loginLogDao.updateLogin(lastIp, stuId);
                formatData.set200("登录成功！");
                return formatData;
            } else {
                formatData.setStatus("801");
                formatData.setInfo("Illegal Parameter");
                formatData.setData("请确认输入是否正确！");
                return formatData;
            }

        } else {
            //调用学校接口
            FormatData formatData1 = new FormatData();
            try {
                formatData1 = new Gson().fromJson(new HttpClientUtil().sendPost("http://wx.idsbllp.cn/api/verify",
                        "stuNum=" + stuId + "&idNum=" + pwd), FormatData.class);

            } catch (Exception e) {
                e.printStackTrace();
                formatData1.setStatus("404");
                formatData1.setInfo("Not Found");
                formatData1.setData("服务器无法根据客户端的请求找到资源");
                return formatData1;
            }
            //确认是第一次登陆用户，将记录插入数据库中，返回登录成功
            if (formatData1.getStatus().equals("200")) {
                EduStu eduStu = new Gson().fromJson(formatData1.getData().toString(), EduStu.class);
                Object[] parmas = new Object[]{eduStu.getStuNum(), eduStu.getIdNum(), 0, eduStu.getName(),
                        eduStu.getGender(), eduStu.getCollege(), eduStu.getMajor(),
                        eduStu.getGrade(), eduStu.getClassNum() + "班", eduStu.getClassNum()};
                try {
                    jdbcTemplate.update(INSERT_STU_INFO_SQL, parmas, new int[]{Types.INTEGER, Types.VARCHAR, Types.SMALLINT,
                            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.SMALLINT, Types.VARCHAR, Types.VARCHAR});
                    loginLogDao.insertLogin(lastIp, stuId);
                    formatData1.set200("登录成功！");
                    return formatData1;
                } catch (Exception e) {
                    e.printStackTrace();
                    formatData1.set500();
                    return formatData1;
                }
            }
            return formatData1;
        }
    }

    /**
     * 返回指定学生信息
     *
     * @param stuId
     * @return
     */
    @Override
    public FormatData getStudentInfo(final int stuId) {
        final StudentInfo studentInfo = new StudentInfo();
        try {
            if (1 == jdbcTemplate.queryForObject(STU_EXISTS_SQL, new Object[]{stuId}, Integer.class)) {
                jdbcTemplate.query(GET_STUDENT_INFO_SQL, new Object[]{stuId},
                        new RowCallbackHandler() {
                            @Override
                            public void processRow(ResultSet rs) throws SQLException {
                                studentInfo.setID(rs.getInt("id"));
                                studentInfo.setStuId(rs.getInt("stuId"));
                                studentInfo.setTotalScores(rs.getShort("totalScores"));
                                studentInfo.setClassNum(rs.getString("classNum"));
                                studentInfo.setStuName(rs.getString("stuName"));
                                studentInfo.setGender(rs.getString("gender"));
                                studentInfo.setCollege(rs.getString("college"));
                                studentInfo.setMajor(rs.getInt("major"));
                                studentInfo.setGrade(rs.getShort("grade"));
                                studentInfo.setStuclass(rs.getString("class"));
                            }
                        });
                formatData.set200(studentInfo);
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

    /**
     * 返回所有学生信息
     *
     * @return
     */
    @Override
    public FormatData getStudentInfoList() {
        ArrayList<StudentInfo> studentInfos = new ArrayList<StudentInfo>();
        try {
            studentInfos =
                    (ArrayList<StudentInfo>) jdbcTemplate.query(STUDENT_INFO_LIST_SQL, new RowMapper<StudentInfo>() {
                        @Override
                        public StudentInfo mapRow(ResultSet rs, int i) throws SQLException {
                            StudentInfo studentInfo = new StudentInfo();
                            studentInfo.setID(rs.getInt("id"));
                            studentInfo.setStuId(rs.getInt("stuId"));
                            studentInfo.setTotalScores(rs.getShort("totalScores"));
                            studentInfo.setClassNum(rs.getString("classNum"));
                            studentInfo.setStuName(rs.getString("stuName"));
                            studentInfo.setGender(rs.getString("gender"));
                            studentInfo.setCollege(rs.getString("college"));
                            studentInfo.setMajor(rs.getInt("major"));
                            studentInfo.setGrade(rs.getShort("grade"));
                            studentInfo.setStuclass(rs.getString("class"));
                            System.out.println(studentInfo.getID());
                            return studentInfo;
                        }


                    });
            formatData.set200(studentInfos);
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }

        /*
        jdbcTemplate.query(sqlStr, new Object[]{}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                studentInfo.setID(rs.getInt("id"));
                studentInfo.setStuId(rs.getInt("stuId"));
                studentInfo.setTotalScores(rs.getShort("totalScores"));
                studentInfo.setClassNum(rs.getString("classNum"));
                studentInfo.setStuName(rs.getString("stuName"));
                studentInfo.setGender(rs.getString("gender"));
                studentInfo.setCollege(rs.getString("collage"));
                studentInfo.setMajor(rs.getInt("major"));
                studentInfo.setGrade(rs.getShort("grade"));
                studentInfo.setStuclass(rs.getString("class"));
                studentInfos.add(studentInfo);
            }
        });*/
    }

    /**
     * 返回指定班级学生
     *
     * @param classNum
     * @return
     */
    @Override
    public FormatData getClassStudent(String classNum) {
        ArrayList<StudentInfo> studentInfos = new ArrayList<StudentInfo>();
        try {
            if (1 == jdbcTemplate.queryForObject(CLASS_NUM_EXISTS_SQL, new Object[]{classNum}, Integer.class)) {
                studentInfos =
                        (ArrayList<StudentInfo>) jdbcTemplate.query(CLASS_STUDENT_SQL, new Object[]{classNum}, new RowMapper<StudentInfo>() {

                            @Override
                            public StudentInfo mapRow(ResultSet rs, int i) throws SQLException {
                                StudentInfo studentInfo = new StudentInfo();
                                studentInfo.setID(rs.getInt("id"));
                                studentInfo.setStuId(rs.getInt("stuId"));
                                studentInfo.setTotalScores(rs.getShort("totalScores"));
                                studentInfo.setClassNum(rs.getString("classNum"));
                                studentInfo.setStuName(rs.getString("stuName"));
                                studentInfo.setCollege(rs.getString("college"));
                                studentInfo.setMajor(rs.getInt("major"));
                                studentInfo.setGrade(rs.getShort("grade"));
                                return studentInfo;
                            }
                        });
                formatData.set200(studentInfos);
                return formatData;
            } else {
                formatData.setStatus("404");
                formatData.setInfo("Not Found");
                formatData.setData("班级不存在！");
                return formatData;
            }

        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }

    }
}
