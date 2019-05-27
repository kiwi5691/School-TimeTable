package com.android.backend.controller;

import com.android.backend.dao.RolePermissionMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.UserLogin;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import com.android.backend.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Objects;

@RestController
@CrossOrigin
@SessionAttributes("role")
public class UserController {

    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    /**
     *@Auther kiwi
     *@Data 2019/5/27
     @param  * @param loginInfoVo
     * 测试用例
      * @param bindingResult
     *@reutn com.android.backend.util.Result
    */
    /*@RequestMapping(value = "/user_student/login", method = RequestMethod.POST, produces = "application/json")
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
    }*/
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    public Result login(UserLogin loginInfoVo, int rid, BindingResult bindingResult, Model model) {
        System.out.println(loginInfoVo.getUserName()+" --"+loginInfoVo.getUserPassword());
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if(userLoginMapper.Checklogin(loginInfoVo)!=1) {
            String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
            return ResultFactory.buildFailResult(message);
        }
        model.addAttribute("role",rolePermissionMapper.CheckRoles(rid));
        return ResultFactory.buildSuccessResult("登陆成功。");
    }


}
