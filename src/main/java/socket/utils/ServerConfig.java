package socket.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Configuration
public class ServerConfig {

	@Value("${root}")
	private String root;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}
