package com.nmid.cqes.service.impl;


import com.nmid.cqes.dao.impl.LoginLogDaoImpl;
import com.nmid.cqes.dao.impl.StudentInfoDaoImpl;
import com.nmid.cqes.dao.impl.StudentPrizesDaoImpl;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StudentInfoDaoImpl studentInfoDao;
    @Autowired
    private StudentPrizesDaoImpl studentPrizesDao;
    @Autowired
    private LoginLogDaoImpl loginLogDao;


    @Override
    public FormatData signIn(int stuId, String pwd, String lastIp) {

        return studentInfoDao.signIn(stuId, pwd, lastIp);
    }

    @Override
    public FormatData getStudentInfo(int stuId) {

        return studentInfoDao.getStudentInfo(stuId);
    }

    @Override
    public FormatData getStudentInfoList() {
        return studentInfoDao.getStudentInfoList();
    }

    @Override
    public FormatData getClassStudent(String classNum) {
        return studentInfoDao.getClassStudent(classNum);
    }

    @Override
    public FormatData getStudentPrizes(int stuId) {
        return studentPrizesDao.getStudentPrizes(stuId);
    }

    @Override
    public FormatData getStudentLoginLog(int stuId) {
        return loginLogDao.getStuLoginLog(stuId);
    }
}
