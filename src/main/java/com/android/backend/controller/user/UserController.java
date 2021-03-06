
package com.android.backend.controller.user;

import com.android.backend.dao.RolePermissionMapper;
import com.android.backend.dao.StudentInfoMapper;
import com.android.backend.dao.TeacherInfoMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.UserLogin;
import com.android.backend.service.UserLoginService;
import com.android.backend.service.UserInfoService;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@Api(value="用户登录注册接口Controller")
public class UserController {
    /**
     *@Auther kiwi
     *初始化logger变量
    */
    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     *@Auther kiwi
     *注入
     */
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private StudentInfoMapper studentInfoMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

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

    /**
     *@Auther kiwi
     *@Data 2019/5/27
     * 用户登录controller
     @param  * @param loginInfoVo
     * @param rid
     * @param bindingResult
     * @param model
     *@reutn com.android.backend.util.Result
     */
    @ApiOperation(value="登录接口", notes="登录接口" ,httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name", value="用户姓名", dataType = "String", required=true, paramType="form"),
    })
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    public Result login(UserLogin loginInfoVo, int rid,
                        BindingResult bindingResult, Model model) {

        String username = loginInfoVo.getUserName();
        logger.info("用户paswd为:"+loginInfoVo.getUserPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(username, loginInfoVo.getUserPassword());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
       logger.info(loginInfoVo.getUserName()+" -----"+loginInfoVo.getUserPassword());

        try{
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户or state=0");
            String message = String.format("无此账户/未激活");
            return ResultFactory.buildFailResult(message);
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            String message = String.format("密码不正确");
            return ResultFactory.buildFailResult(message);
        }catch(LockedAccountException lae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            String message = String.format("账户已锁定");
            return ResultFactory.buildFailResult(message);
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            String message = String.format("用户名或密码错误次数过多");
            return ResultFactory.buildFailResult(message);
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            String message = String.format("用户名或密码不正确");
            return ResultFactory.buildFailResult(message);
        }
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if(currentUser.isAuthenticated()) {
            model.addAttribute("role", rolePermissionMapper.CheckRoles(rid));

            logger.info("rid is"+rid);
            if(rid==1){
                try{
                    studentInfoMapper.selectIsRid(String.valueOf(username));
                    return ResultFactory.buildSuccessResult("登陆成功。");
                }catch (Exception e){
                    return ResultFactory.buildFailResult("账号密码没错，但是请选择好你的角色");
                }
            }else if(rid==2) {
                try{
                    teacherInfoMapper.selectIsRid(String.valueOf(username));
                    return ResultFactory.buildSuccessResult("登陆成功。");
                }catch (Exception e){
                    return ResultFactory.buildFailResult("账号密码没错，但是请选择好你的角色");
                }
            }
          return ResultFactory.buildFailResult("未知错误");
        }else{
            String message = String.format("登陆失败");
            return ResultFactory.buildFailResult(message);
        }

    }


    /**
     *@Auther kiwi
     *@Data 2019/5/29
     * 用户注册controller
     @param  * @param loginInfoVo
     * @param Nickname
     * @param rid
     * @param bindingResult
     * @param model
     *@reutn com.android.backend.util.Result
    */

    @RequestMapping(value="/user/register",method = RequestMethod.POST,produces = "application/json")
    public Result register(UserLogin loginInfoVo,String Nickname,String rid, BindingResult bindingResult, Model model) {

        logger.info("-----注册------");
        logger.info("用户名：" + loginInfoVo.getUserName());


        try {
            userLoginMapper.selectIsName(loginInfoVo);
            logger.info("是否存在此账户:"+userLoginMapper.selectIsName(loginInfoVo));
        } catch (Exception e) {

            //md5 盐加密密码
            String hashAlgorithmName = "MD5";//加密方式
            Object crdentials = loginInfoVo.getUserPassword();//密码原值
            ByteSource salt = ByteSource.Util.bytes(loginInfoVo.getUserName());//以账号作为盐值
            int hashIterations = 1024;//加密1024次
            Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);



            loginInfoVo.setUserPassword(result.toString());
            logger.info("加密后的密码为:"+result.toString());



            logger.info("rid is",rid);
            userLoginMapper.insert(loginInfoVo);//  存入登录信息表
            userLoginService.register(Integer.parseInt(rid),loginInfoVo.getUserName(),Nickname);  //  存入信息表中

            model.addAttribute("role", rolePermissionMapper.CheckRoles(Integer.parseInt(rid)));
            return ResultFactory.buildSuccessResult("注册成功。");
        }

        String message = String.format("已经存在账号，注册失败");
        return ResultFactory.buildFailResult(message);

    }



    /**
     *@Auther kiwi
     *@Data 2019/6/2
     * 全局获取userid rid
     @param  * @param userLogin
     * @param bindingResult
     * @param model
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value="/user/golabinfo",method = RequestMethod.POST,produces = "application/json")
    public Result GolabInfo(UserLogin userLogin,BindingResult bindingResult, Model model){

        String message = "rid=,userid =";
        return ResultFactory.buildSuccessResult(message);
    }



    /**
     *@Auther kiwi
     *@Data 2019/6/4
     * 退出时添加最后一次登录时间
     @param  * @param UserId
     * @param rid
     *@reutn void
    */
    @RequestMapping(value = "/user/logout",method =RequestMethod.GET,produces = "application/json")
    public Result Logout(String UserId,int rid){

        userInfoService.UpdateLastLoginTime(UserId,rid);
        return ResultFactory.buildSuccessResult("退出成功");
    }
}
