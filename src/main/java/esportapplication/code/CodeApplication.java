package esportapplication.code;

import esportapplication.code.models.Group;
import esportapplication.code.models.Privilege;
import esportapplication.code.models.User;
import esportapplication.code.repositories.UserRepository;
import esportapplication.code.services.GroupService;
import esportapplication.code.services.PrivilegeService;
import esportapplication.code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CodeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CodeApplication.class, args);
        UserService userService = (UserService) context.getBean("userService");
        GroupService groupService = (GroupService) context.getBean("groupService");
        PrivilegeService privilegeService = (PrivilegeService) context.getBean("privilegeService");


        Group administrators = groupService.findGroupByName("ADMINISTRATORS");
        if( administrators == null){
            Group group = new Group();
            group.setName("ADMINISTRATORS");
            administrators = groupService.save(group);
        }

        if(privilegeService.findByName("ADMINISTRATION") == null){
            Privilege privilege = new Privilege();
            privilege.setName("ADMINISTRATION");
            privilegeService.save(privilege);
            privilegeService.addPrivilegeToGroup(privilege.getId(), administrators.getId());
        }
        Group watchers = groupService.findGroupByName("WATCHERS");
        if( watchers == null){
            Group group = new Group();
            group.setName("WATCHERS");
            watchers = groupService.save(group);
        }

        if(privilegeService.findByName("WATCHERS") == null){
            Privilege privilege = new Privilege();
            privilege.setName("WATCHERS");
            privilegeService.save(privilege);
            privilegeService.addPrivilegeToGroup(privilege.getId(), watchers.getId());
        }

        User rootUser = userService.findUserByUsername("root");
        if(rootUser == null){
            rootUser = new User();
            rootUser.setUsername("root");
            rootUser.setPassword("pass123");
            rootUser.setName("Aleksandar");
            rootUser.setSurname("Stojkov");
            rootUser.setEmail("aleksandarstojkov255@yahoo.com");
            rootUser = userService.save(rootUser);
            groupService.addUserToGroup(administrators.getId(), rootUser.getId());
        }
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {

        final UserRepository repository = repo;

        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsService> service = builder.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = repository.getByUsername(username);
                List<Object[]> objects = repository.getPrivilegesByUser(user.getId());
                List<Privilege> privileges = new ArrayList<>();
                for(Object[] obj : objects){
                    Privilege privilege = new Privilege();
                    privilege.setId(  ((BigInteger)(obj[0])).longValue());
                    privilege.setName(  (String)    obj[1]);
                    privileges.add(privilege);
                }
                user.setPrivileges(privileges);
                CustomUserDetails customUserDetails = new CustomUserDetails(user);
                return customUserDetails;
            }
        });

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(service.getUserDetailsService());
        builder.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
