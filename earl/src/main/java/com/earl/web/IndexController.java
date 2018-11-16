package com.earl.web;


import com.earl.Tools.DBUtils;
import com.earl.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        System.out.println("*****************");
        return "index";
    }

    @RequestMapping("/getHeader")
    @ResponseBody
    public List<Map<String, Object>> getHeader(){
        DBUtils dbUtils = new DBUtils("jdbc:mysql://10.182.90.41:3358/logcloud_sys?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&tinyInt1isBit=false", "root", "scmwms");
        List<Map<String, Object>> list = dbUtils.selectList("SELECT * FROM sys_dict LIMIT 10");
        dbUtils.close();
        return list;
    }

}
