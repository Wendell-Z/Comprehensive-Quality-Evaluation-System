package com.nmid.cqes.web;

import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.service.impl.ClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClassController {
    @Autowired
    private ClassServiceImpl classService;

    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    @ResponseBody
    public FormatData getClassList() {
        return classService.getClassList();
    }
}
