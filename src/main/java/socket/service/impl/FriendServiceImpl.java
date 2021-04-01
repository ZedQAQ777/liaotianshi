package socket.service.impl;

import java.util.List;
import socket.dao.FriendMapper;
import socket.model.Friend;
import socket.model.User;
import socket.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 好友Service
 *
 */
@Service
public class FriendServiceImpl extends BaseServiceImpl<Friend> implements FriendService {

	@Autowired
	private FriendMapper friendMapper;

	@Override
	public Mapper<Friend> getMapper() {
		return friendMapper;
	}

	@Override
	public List<User> searchFriendByFriendId(int friendId) {
		return null;
	}

	@Override
	public List<User> searchFriendByRemarkName(String remarkName) {
		return null;
	}

	@Override
	public boolean isFriend(int userId, int friendId) {
		return false;
	}

	@Override
	public int delFriend(int userId, int friendId) {
		return 0;
	}

	@Override
	public int addFriend(int fromId, int toId, int typeId) {
		return 0;
	}

	@Override
	public int getFriendCounts(int userId) {
		return 0;
	}

	@Override
	public List<String> getFriendsId(int userId) {
		return null;
	}

}
