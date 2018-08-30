package com.nmid.cqes.web;

import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.service.impl.StuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class StudentController {
    @Autowired
    private StuServiceImpl stuService;
    @Autowired
    private FormatData formatData;

    /**
     * 登录 返回登录处理信息
     *
     * @param stuId
     * @param pwd
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public FormatData singIn(int stuId, String pwd, HttpServletRequest request, HttpSession session) {
        formatData = stuService.signIn(stuId, pwd, request.getRemoteAddr());
        if (formatData.getStatus().equals("200")) {
            session.setAttribute("stuId", stuId);
            return formatData;
        }
        return formatData;
    }

    /**
     * 退出
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public FormatData logout(HttpSession session) {
        // 移除session
        session.removeAttribute("stuId");
        formatData.set200("退出成功！");
        return formatData;
    }

    /**
     * 返回指定学生信息
     *
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/stuInfo", method = RequestMethod.GET)
    @ResponseBody
    public FormatData getStudentInfo(int stuId) {
        return stuService.getStudentInfo(stuId);
    }

    /**
     * 返回学生信息列表
     *
     * @return
     */
    @RequestMapping(value = "/stuList", method = RequestMethod.GET)
    @ResponseBody
    public FormatData getstudentList() {
        return stuService.getStudentInfoList();
    }

    /**
     * 返回指定学生所有奖项
     *
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/stuPrizes", method = RequestMethod.GET)
    @ResponseBody
    public FormatData getStudentPrizes(int stuId) {
        return stuService.getStudentPrizes(stuId);
    }

    /**
     * 返回指定班级的学生信息
     *
     * @param classNum
     * @return
     */
    @RequestMapping(value = "/classStu", method = RequestMethod.GET)
    @ResponseBody
    public FormatData getClassStuInfo(String classNum) {
        return stuService.getClassStudent(classNum);
    }

    @RequestMapping(value = "/loginLog", method = RequestMethod.GET)
    @ResponseBody
    public FormatData getStudentLoginLog(int stuId) {
        return stuService.getStudentLoginLog(stuId);
    }

    /**
     * 返回主页 接口测试页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
