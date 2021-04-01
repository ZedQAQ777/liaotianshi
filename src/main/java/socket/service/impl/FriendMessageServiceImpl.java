package socket.service.impl;

import socket.dao.FriendMessageMapper;
import socket.model.FriendMessage;
import socket.service.FriendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class FriendMessageServiceImpl extends BaseServiceImpl<FriendMessage> implements FriendMessageService {

    @Autowired
    private FriendMessageMapper friendMessageMapper;

    @Override
    public Mapper<FriendMessage> getMapper() {
        return friendMessageMapper;
    }
}
