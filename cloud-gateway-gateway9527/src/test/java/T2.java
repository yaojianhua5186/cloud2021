import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import java.time.ZonedDateTime;

@Slf4j
public class T2 {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now();//默认时区
        log.info("默认时区 ： "+ zbj);
    }
}
