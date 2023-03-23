import com.lesson.community.CommunityApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname LoggerTest
 * @Description Logger的测试
 * @Date 2023/3/22 9:34
 * @Created by hwj
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class LoggerTest {

    @Test
    public void testLogger() {

        System.out.println(log.getName());

        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
    }
}
