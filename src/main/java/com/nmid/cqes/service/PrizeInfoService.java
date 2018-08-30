package com.nmid.cqes.service;

import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.PrizeInfo;
import org.springframework.web.multipart.MultipartFile;

public interface PrizeInfoService {

    //提交一条奖项信息
    FormatData addPrizeInfo(PrizeInfo prizeInfo, MultipartFile multipartFile, String targetDirectory);

    //奖项状态刷新
    FormatData updateStatus(int ID, byte status);

    //修改一条奖项信息
    FormatData updatePrizeInfo(PrizeInfo prizeInfo, MultipartFile multipartFile, String targetDirectory);

    //删除一条奖项信息
    FormatData deletePrizeInfo(int ID);


}
