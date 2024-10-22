

import com.PaceUniversity.Capstone.repository.UserRepository;
import com.PaceUniversity.Capstone.repository.User_Repository;
import com.PaceUniversity.Capstone.service.UserService;
import com.PaceUniversity.Capstone.service.User_Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "userService")
    public User_Service getUserService(){
        UserService service = new UserService();
        service.setRepository(getUserRepository());
        return service;
    }

    @Bean(name = "userRepository")
    public User_Repository getUserRepository(){
        return new UserRepository();
    }




}
