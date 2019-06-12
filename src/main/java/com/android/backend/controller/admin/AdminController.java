package com.android.backend.controller.admin;

import com.android.backend.Vo.VueLoginInfoVo;
import com.android.backend.service.FrontEndService;
import com.android.backend.service.UserInfoService;
import com.android.backend.util.JSONChange;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther:kiwi
 * 管理员Controller
 * @Date: 2019/6/5 13:47
 */
@CrossOrigin
@RestController
public class AdminController {



    /**
     * @Auther kiwi
     * 注入
     */
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FrontEndService frontEndService;

    private static  Logger logger =  LoggerFactory.getLogger(AdminController.class);

    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public Result login(@Valid @RequestBody VueLoginInfoVo loginInfoVo, BindingResult bindingResult) {

        logger.info("start test\n");
        logger.info("username is: :"+loginInfoVo.getUsername());
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if (!Objects.equals("admin", loginInfoVo.getUsername()) || !Objects.equals("123456", loginInfoVo.getPassword())) {
            String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult("登陆成功。");
    }


    @RequestMapping(value = "/api/user/listpage", method = RequestMethod.GET, produces = "application/json")
    public Result showStudentInfo(SpringDataWebProperties.Pageable page,String name) throws JsonProcessingException {



        String jsonStr = JSONChange.objToJson(frontEndService.getList());


        logger.info("开始发送list page，下例");
        logger.info(jsonStr);

        return ResultFactory.buildSuccessResult(jsonStr);
    }

}
