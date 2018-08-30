package com.nmid.cqes.web;

import com.google.gson.Gson;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.PrizeInfo;
import com.nmid.cqes.service.impl.PrizeInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PrizeInfoController {

    @Autowired
    private PrizeInfoServiceImpl prizeInfoService;

    @PostMapping(value = "/status")
    @ResponseBody
    public FormatData changeStatus(int prizeId, byte status) {
        return prizeInfoService.updateStatus(prizeId, status);
    }

    @PostMapping(value = "/delPrize")
    @ResponseBody
    public FormatData deletePrize(int prizeId) {
        return prizeInfoService.deletePrizeInfo(prizeId);
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public FormatData uploadFile(MultipartFile file, String prizeInfo, HttpServletRequest request) {
        return prizeInfoService.addPrizeInfo(new Gson().fromJson(prizeInfo, PrizeInfo.class), file,
                request.getSession().getServletContext().getRealPath("/prizeFiles"));
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public FormatData updateFile(MultipartFile file, String prizeInfo, HttpServletRequest request) {
        return prizeInfoService.updatePrizeInfo(new Gson().fromJson(prizeInfo, PrizeInfo.class), file,
                request.getSession().getServletContext().getRealPath("/prizeFiles"));
    }
}
