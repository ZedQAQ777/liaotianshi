package socket.service.impl;

import java.util.List;
import socket.dao.GroupUserMapper;
import socket.model.Group;
import socket.model.GroupUser;
import socket.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 群成员
 *
 */
@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUser> implements GroupUserService {

	@Autowired
	GroupUserMapper groupUserMapper;

	@Override
	public Mapper<GroupUser> getMapper() {
		return groupUserMapper;
	}

	@Override
	public List<String> getSimpleMemberByGroupId(int groupId) {
		return null;
	}

	@Override
	public List<Group> getByUserId(int userId) {
		return null;
	}

	@Override
	public int batchSave(List<GroupUser> guserList) {
		return 0;
	}

	@Override
	public int deleteByGroupId(int groupId) {
		return 0;
	}

	@Override
	public boolean hasUser(int groupId) {
		return false;
	}

	@Override
	public List<GroupUser> getByGroupId(int groupId) {
		return null;
	}

	@Override
	public List<GroupUser> getNewMemberByGroupId(int groupId, int userId) {
		return null;
	}


}
