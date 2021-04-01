package socket.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import socket.service.FriendMessageService;

@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private FriendMessageService friendMessageService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				friendMessageService.selectAll();
				WSServer.getInstance(friendMessageService).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
