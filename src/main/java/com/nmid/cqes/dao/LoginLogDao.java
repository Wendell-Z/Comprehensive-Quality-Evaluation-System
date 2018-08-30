package com.nmid.cqes.dao;

import com.nmid.cqes.domain.FormatData;

public interface LoginLogDao {

    FormatData updateLogin(String lastIp, int stuId);

    FormatData insertLogin(String lastIp, int stuId);

    FormatData getStuLoginLog(int stuId);
}
