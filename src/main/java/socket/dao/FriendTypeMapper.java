package socket.dao;

import java.util.List;

import socket.model.FriendType;
import tk.mybatis.mapper.common.Mapper;

public interface FriendTypeMapper extends Mapper<FriendType> {
	// 获取好友分组列表
	List<FriendType> getFriendTypeByUserId(int userId);
}