import com.PaceUniversity.Capstone.model.User;
import com.PaceUniversity.Capstone.service.UserService;
import com.PaceUniversity.Capstone.service.User_Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {


    public static void main(String[] args) {


        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        User_Service service = applicationContext.getBean("userService", User_Service.class);
        System.out.println(service.findAllUsers().get(0).getBio());
    }





}
