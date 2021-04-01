package socket.ws;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 用户id和channel的关联关系处理
 */
public class UserChannelRel {


	private static ConcurrentHashMap<Integer, Channel> manager = new ConcurrentHashMap<>();

	public static void put(Integer senderId, Channel channel) {
		manager.put(senderId, channel);
	}
	
	public static Channel get(Integer senderId) {
		return manager.get(senderId);
	}
	
	public static void output() {
		for (HashMap.Entry<Integer, Channel> entry : manager.entrySet()) {
		}
	}
}
