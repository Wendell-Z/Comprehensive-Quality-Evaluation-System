package com.nmid.cqes.dao.impl;

import com.nmid.cqes.dao.ClassesDao;
import com.nmid.cqes.domain.FormatData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClassesDaoImpl implements ClassesDao {
    private final static String CLASS_LIST_SQL = " SELECT classNum FROM classes ORDER BY classNum ";
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FormatData formatData;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FormatData getClassList() {
        try {
            formatData.set200(jdbcTemplate.queryForList(CLASS_LIST_SQL));
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }
}
