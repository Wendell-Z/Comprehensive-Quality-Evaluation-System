package com.nmid.cqes.dao.impl;

import com.nmid.cqes.dao.FileDao;
import com.nmid.cqes.domain.FormatData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileDaoImpl implements FileDao {

    private final static String FILE_URL = "http://127.0.0.1:8080/cqes/prizeFiles/";
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FormatData formatData;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FormatData uploadFile(MultipartFile multipartFile, int stuId, String stuName, String prizeName) {


        return null;
    }
}
