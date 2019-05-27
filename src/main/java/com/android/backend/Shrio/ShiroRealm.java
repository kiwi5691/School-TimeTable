package com.android.backend.Shrio;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.android.backend.dao.RolePermissionMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.UserLogin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 *@Auther kiwi
 *@Data 2019/5/27
 @param  * @param null
 *@reutn
*/
public class ShiroRealm extends AuthenticatingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserLoginMapper userLoginMapper;

   // @Autowired
   // private RolePermissionMapper rolePermissionMapper;

   /* @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;*/

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        // 查出是否有此用户
        UserLogin user = userLoginMapper.selectAllByName(token.getUsername());
        if (user != null) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);//成功则放入session
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            Object principal = user.getUserName();
            //2)credentials：密码
            Object credentials = user.getUserPassword();
            //3)realmName：当前realm对象的name，调用父类的getName()方法即可
            String realmName = getName();
            //4)credentialsSalt盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(principal);//使用账号作为盐值

            return new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        }
        return null;
    }

    /**
     * 权限认证,因为只对两者操作，不创建权限表
     *
     * @param principalCollection
     * @return
     */
    //   @Override
  /*  protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        // 获取当前登录输入的用户名，等价于(String)
        String loginName = (String) super
                .getAvailablePrincipal(principalCollection);
        // 到数据库查是否有此对象
        Users user = usersMapper.selectAllByName(loginName);
        if (user != null) {
            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 用户的角色集合
            Set<String> set = new HashSet<String>();
            set.add(user.getUserName());
            info.setRoles(set);
            // 用户的权限集合
            List<RolesPermissions> srpList = (List<RolesPermissions>) rolesPermissionsMapper.selectByRid(user.getRid());
            List<String> pNameList = new ArrayList<String>();
            for (RolesPermissions sysRolePermission : srpList) {
                pNameList.add(permissionMapper.selectByPrimaryKey(
                        sysRolePermission.getPid()).getPermission());
            }
            info.addStringPermissions(pNameList);
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }*/

}