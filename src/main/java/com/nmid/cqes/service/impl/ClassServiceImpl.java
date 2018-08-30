package com.nmid.cqes.service.impl;

import com.nmid.cqes.dao.impl.ClassesDaoImpl;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassesDaoImpl classesDao;

    @Override
    public FormatData getClassList() {
        return classesDao.getClassList();
    }
}
