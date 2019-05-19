package com.android.backend.controller;

import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import com.android.backend.vo.StudentVo;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Objects;

@RestController
@CrossOrigin
public class UserController {

    @RequestMapping(value = "/user_student/login", method = RequestMethod.POST, produces = "application/json")
    public Result login(StudentVo loginInfoVo, BindingResult bindingResult) {

        System.out.println(loginInfoVo.getUsername()+" --"+loginInfoVo.getPassword());
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if (!Objects.equals("123", loginInfoVo.getUsername()) || !Objects.equals("123", loginInfoVo.getPassword())) {
            String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult("登陆成功。");
    }
}
