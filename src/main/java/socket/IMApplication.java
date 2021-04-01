package socket;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("socket.dao")
@SpringBootApplication
public class IMApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class,args);
    }
}
