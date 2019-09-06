package com.example.demo.mapper;

import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/9/5.
 */
public interface UserMapper {
    User loadUserByUsername(String username);


    List<Role> getRolesById(Integer id);
}
