package socket.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import socket.model.FriendMessage;
import socket.service.FriendMessageService;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private FriendMessageService friendMessageService;

    @RequestMapping("/applyAddFriend")
    public void applyAddFriend(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
    }

    @RequestMapping("/friend/{toId}/getMessage")
    @ResponseBody
    public List<FriendMessage> getMessage(@PathVariable("toId") Integer toId){
        FriendMessage friendMessage = new FriendMessage();
        friendMessage.setToUserId(toId);
        return friendMessageService.select(friendMessage);
    }
    
}