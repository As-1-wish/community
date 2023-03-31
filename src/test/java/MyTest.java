import com.lesson.community.CommunityApplication;
import com.lesson.community.dao.LoginTicketRepository;
import com.lesson.community.entity.DiscussPostEntity;
import com.lesson.community.entity.MessageEntity;
import com.lesson.community.service.DiscussPostService;
import com.lesson.community.service.MessageService;
import com.lesson.community.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class MyTest {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Test
    public void test() {

//        UserEntity userEntity1 = new UserEntity("user1", "pw", "sa",
//                "com", 1, 2, "hu", "g",
//                new Timestamp(new java.util.Date().getTime()));
//        UserEntity userEntity2 = new UserEntity("user2", "pw", "sa",
//                "com_pro", 1, 2, "hu", "g",
//                new Timestamp(new java.util.Date().getTime()));

//        try {
//            userService.saveAndFlush(userEntity1);
//        } catch (Exception e) {
//            System.out.println("插入user1失败");
//            if (e instanceof DuplicateKeyException) {
//                e.printStackTrace();
//            }
//            try {
//                userService.saveAndFlush(userEntity2);
//            } catch (Exception error) {
//                System.out.println("插入user2失败");
//                if (error instanceof DuplicateKeyException)
//                    error.printStackTrace();
//            }
//
//        userService.Updatepassword(1001, "ppw");
        System.out.println(userService.getUserEntityByName("user2"));
    }

    @Autowired
    private DiscussPostService discussPostService;

    @Test
    public void testDiscussPost(){
        System.out.println(discussPostService.getDiscussPostRows(149));

        List<DiscussPostEntity> tmp = discussPostService.getDiscussPosts(149, 0, 10);
        for(DiscussPostEntity dis : tmp){
            System.out.println(dis.toString());
        }
    }

    @Autowired
    private LoginTicketRepository loginTicketRepository;

    @Test
    public void testLoginTicket(){
//        LoginTicketEntity loginTicket = new LoginTicketEntity(2, "idfnisad", 1,
//                new Timestamp(new Date().getTime()));
//        loginTicketRepository.saveAndFlush(loginTicket);
        loginTicketRepository.UpdateStatus("idfnisad", 0);
    }

    @Test
    public void MessageTest(){
        System.out.println(messageService.getConversationCount(111));

        List<MessageEntity> lms = messageService.getConversations(111, 0, 20);
        for(MessageEntity messageEntity : lms)
            System.out.println(messageEntity);

        System.out.println("---------------------");
        List<MessageEntity> ll = messageService.getLetters("111_112", 0, 15);
        for(MessageEntity message : ll)
            System.out.println(message);

        System.out.println(messageService.getLettersCount("111_112"));

        System.out.println(messageService.getLetterUnreadCount(111, "111_112"));
    }
}
