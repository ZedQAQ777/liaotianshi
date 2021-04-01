package socket.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import socket.service.FriendMessageService;

public class WSServer {

	private static FriendMessageService service;

	private static class SingletionWSServer {
		static final WSServer instance = new WSServer(service);
	}
	
	public static WSServer getInstance(FriendMessageService friendMessageService) {
		service = friendMessageService;
		return SingletionWSServer.instance;
	}
	
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	private ServerBootstrap server;
	private ChannelFuture future;
	
	public WSServer(FriendMessageService service) {
		boss = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(boss, worker).channel(NioServerSocketChannel.class)
			.childHandler(new WSServerInitialzer(service));
	}

	public void start() {
		int port = 8888;
		future = server.bind(port);
	}
}
