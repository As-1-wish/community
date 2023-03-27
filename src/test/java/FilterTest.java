import com.lesson.community.CommunityApplication;
import com.lesson.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname FilterTest
 * @Description
 * @Date 2023/3/27 14:09
 * @Created by hwj
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class FilterTest {

    @Autowired
    SensitiveFilter sensitiveFilter;

    @Test
    public void test(){
        String str = "这是嫖娼的敏感词测试,嫖（%……娼&&";
        System.out.println(sensitiveFilter.filter(str));
    }
}
