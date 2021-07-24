package org.xr.happy.controller.login;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.annotation.Validator;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.view.ResultJsonView;
import org.xr.happy.common.vo.UserVo;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @JsonView(value = ResultJsonView.UserSimple.class)
    @RequestMapping(value = "/valid",method = RequestMethod.POST)
    public Result<UserVo> testValidator(@RequestBody @Validator UserVo userVo){
        logger.info("UserVo:{}", JSON.toJSONString(userVo));
        UserVo vo = new UserVo();
        vo.setUsername("steven");
        vo.setPassword("12345678");
        return Result.success(vo);
    }

    @JsonView(value = ResultJsonView.all.class)
    @RequestMapping(value = "/jsonview",method = RequestMethod.POST)
    public Result jsonView(@RequestBody @Validator UserVo userVo){

        UserVo vo = new UserVo();
        vo.setUsername("steven");
        vo.setPassword("12345678");
        logger.info("UserVo:{}", JSON.toJSONString(userVo));
        return Result.success(userVo);
    }
}
