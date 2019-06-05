package com.android.backend.controller.admin;

import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther:kiwi
 * 管理员Controller
 * @Date: 2019/6/5 13:47
 */
@CrossOrigin
@RestController
public class AdminController {

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST, produces = "application/json")
    public Result adminLogin(){


        return ResultFactory.buildSuccessResult("登录成功");
    }
}
