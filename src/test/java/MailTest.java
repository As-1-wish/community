import com.lesson.community.CommunityApplication;
import com.lesson.community.util.MailClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Classname MailTest
 * @Description
 * @Date 2023/3/22 14:43
 * @Created by hwj
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClientUtil mailClient;

    @Autowired
    private TemplateEngine templateEngine;  // 注入模板引擎

    @Test // 发送普通文件
    public void TextMail(){
        mailClient.senMail("2356461336@qq.com", "Test", "这是一个小测试");
    }

    @Test // 利用 thymeleaf 发送模板文件
    public void HtmlMail(){
        Context context = new Context();
        context.setVariable("username", "Today");

        String content = templateEngine.process("/mail/demo",context);

        mailClient.senMail("2356461336@qq.com", "Test", content);
    }
}
