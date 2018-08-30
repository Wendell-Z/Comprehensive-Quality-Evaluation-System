package com.nmid.cqes.dao;

import com.nmid.cqes.domain.FormatData;
import org.springframework.web.multipart.MultipartFile;

public interface FileDao {

    FormatData uploadFile(MultipartFile multipartFile, int stuId, String stuName, String prizeName);
}
