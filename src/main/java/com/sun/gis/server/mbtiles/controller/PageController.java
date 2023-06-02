package com.sun.gis.server.mbtiles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    /**
     * 登录页
     *
     * @return 登录页
     */
    @RequestMapping(value = "/login")
    public String userLoginPage() {
        return "login";
    }


}
