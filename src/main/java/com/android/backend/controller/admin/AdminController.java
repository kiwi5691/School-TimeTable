package com.android.backend.controller.admin;

import com.android.backend.dtd.VueLoginInfoVo;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
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

    // 用不打算用哦
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
}
