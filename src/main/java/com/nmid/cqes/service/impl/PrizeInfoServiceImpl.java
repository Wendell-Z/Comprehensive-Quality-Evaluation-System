package com.nmid.cqes.service.impl;

import com.nmid.cqes.dao.impl.PrizeInfoDaoImpl;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.PrizeInfo;
import com.nmid.cqes.service.PrizeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PrizeInfoServiceImpl implements PrizeInfoService {
    @Autowired
    private PrizeInfoDaoImpl prizeInfoDao;

    @Override
    public FormatData addPrizeInfo(PrizeInfo prizeInfo, MultipartFile multipartFile, String targetDirectory) {
        return prizeInfoDao.addPrizeInfo(prizeInfo, multipartFile, targetDirectory);
    }

    @Override
    public FormatData updateStatus(int ID, byte status) {
        return prizeInfoDao.updateStatus(ID, status);
    }

    @Override
    public FormatData updatePrizeInfo(PrizeInfo prizeInfo, MultipartFile multipartFile, String targetDirectory) {
        return prizeInfoDao.updatePrizeInfo(prizeInfo, multipartFile, targetDirectory);
    }

    @Override
    public FormatData deletePrizeInfo(int ID) {
        return prizeInfoDao.deletePrizeInfo(ID);
    }

}
