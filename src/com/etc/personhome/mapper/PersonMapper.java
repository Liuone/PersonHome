package com.etc.personhome.mapper;

import com.etc.personhome.entity.Tuser;
import com.etc.personhome.entity.Facekey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonMapper {
    Tuser login(String uname, String pwd);
    //实现查询用户的功能
    List<Tuser> getTuser(String username, int status);
     //回显某个用户信息
    Tuser getOne(int id);
     //修改用户信息
    void updagteUser(String username, String loginname, int statis, int id);
      //删除用户信息
    void deleUser(int uid);
    //添加用户信息
    void addUser(Tuser tuser);
    //人脸识别
    void upDataFaceUrlByName(@Param("loginname") String loginname, @Param("faceUrl") String faceUrl, @Param("facepath") String facepath);

    List<Tuser> selectUser();

    Facekey selectFaceKye();
}
