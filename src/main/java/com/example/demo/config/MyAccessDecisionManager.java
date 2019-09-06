package com.example.demo.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 根据你需要的角色，然后和已有的角色比较
 * 如果你需要的角色我有，那么请求继续往下走，否则就是非法请求
 * Created by Administrator on 2019/9/6.
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     *
     * @param authentication 保存了当前登录用户的信息（已有哪些角色）
     * @param o
     * @param collection  需要哪些角色，和authentication对比判断
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute attribute : collection) {//遍历collection，就是你需要的角色
            if("ROLE_login".equals(attribute.getAttribute())){ //登录之后就可以访问
                if(authentication instanceof AnonymousAuthenticationToken){ //如果是匿名用户，说明没登录，抛异常
                    throw  new AccessDeniedException("非法请求！");
                }else{
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//已经存在的角色/
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(attribute.getAttribute())){ //如果存在你需要的角色，直接return
                    return;
                }
            }
        }
        throw  new AccessDeniedException("非法请求！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
