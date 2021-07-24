package org.xr.happy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.xr.happy.common.vo.UserVo;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/websocket")
    public String index() {
        logger.info("websocket test6666 ...");
        return "index";
    }


    @GetMapping("/test")
    public String webs() {
        logger.info("websocket test6666 ...");
        return "webs";
    }

    @GetMapping("/tmlf")
    public String tmlf(ModelMap modelMap) {

        ArrayList<Object> objects = new ArrayList<>();

    /*    Collections.addAll(objects, new UserVo(123L, "steven", "123456", "steven.renqq.com", "444"),
                new UserVo(124L, "宋心茹", "123456", "sxr.renqq.com", "5565"));*/

        modelMap.addAttribute("userList", objects);

        logger.info("websocket test6666 ...");
        return "login";
    }
}
