package socket.service.impl;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socket.dao.UserMapper;
import socket.model.User;
import socket.service.UserService;
import tk.mybatis.mapper.common.Mapper;

@Service
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService{
	 @Autowired
     private UserMapper userMapper;

     @Override
     public Mapper<User> getMapper() {
        return userMapper;
     }

     public User getUserById(int userId) {  
        return userMapper.selectByPrimaryKey(userId);  
     }  
     
     public User matchUser(String userName,String password) {
    	 User record=new User();
    	 record.setUserName(userName);
    	 record.setPassword(SecureUtil.md5(password));
         return userMapper.selectOne(record);
     }

}
