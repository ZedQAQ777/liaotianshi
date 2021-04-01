package socket.ws;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socket.model.FriendMessage;
import socket.service.FriendMessageService;
import socket.utils.JsonUtils;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Description: 处理消息的handler
 * TextWebSocketFrame： 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private FriendMessageService friendMessageService;

	public ChatHandler(FriendMessageService service) {
		this.friendMessageService = service;
	}


	// 用于记录和管理所有客户端的channel
	public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) 
			throws Exception {
		// 获取客户端传输过来的消息
		String content = msg.text();
		Channel currentChannel = ctx.channel();
		// 1. 获取客户端发来的消息
		JSONObject dataContent = JSONObject.fromObject(content);
		String action  = dataContent.getJSONObject("to").getString("type");
		// 2. 判断消息类型，根据不同的类型来处理不同的业务
		if(action.equals("onlineStatus")){
			// 	2.1  当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
			Integer senderId = Integer.valueOf(dataContent.getString("userId"));
			UserChannelRel.put(senderId, currentChannel);
			UserChannelRel.output();
		}else if(action.equals("friend")) {
			//  2.2  聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
			//message:{"mine":{"avatar":"images/boy-01.png","content":"111","id":1,"mine":true,"username":"张三"},
			// "to":{"avatar":"images/boy-02.png","id":2,"type":"friend","username":"李四"}}
			int toId = dataContent.getJSONObject("to").getInt("id");
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date date = new Date();
			String time = df.format(date);
			dataContent.put("time", time);
			JSONObject toMessage = new JSONObject();
			toMessage.put("avatar", dataContent.getJSONObject("mine").getString("avatar"));
			toMessage.put("content", dataContent.getJSONObject("mine").getString("content"));
			toMessage.put("timestamp",date.getTime());
			toMessage.put("time",time);
			toMessage.put("mine",false);
			toMessage.put("type",dataContent.getJSONObject("to").getString("type"));
			toMessage.put("username",dataContent.getJSONObject("mine").getString("username"));
			toMessage.put("id", dataContent.getJSONObject("mine").getInt("id"));


			// 发送消息
			// 从全局用户Channel关系中获取接受方的channel
			Channel receiverChannel = UserChannelRel.get(toId);

			FriendMessage friendMessage = new FriendMessage();
			friendMessage.setContent(toMessage.getString("content"));
			friendMessage.setFromUserId(toMessage.getInt("id"));
			friendMessage.setToUserId(toId);
			friendMessage.setSendTime(time);
			friendMessage.setIsRead(0);
			friendMessage.setIsBack(0);
			friendMessage.setIsDel(0);
			friendMessageService.save(friendMessage);
			if (receiverChannel == null) {
				// 用户离线 放入队列 待用户上线后再发送
			} else {
				// 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
				Channel findChannel = users.find(receiverChannel.id());
				if (findChannel != null) {
					// 用户在线
					receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(toMessage)));
				} else {
					// 用户离线 放入队列 待用户上线后再发送
				}
			}
		}
	}
	
	/**
	 * 当客户端连接服务端之后（打开连接）
	 * 获取客户端的channel，并且放到ChannelGroup中去进行管理
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		String channelId = ctx.channel().id().asShortText();
		users.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		String channelId = ctx.channel().id().asShortText();
		// 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
		users.remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
		ctx.channel().close();
		users.remove(ctx.channel());
	}
}
