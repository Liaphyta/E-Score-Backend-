package esportapplication.code.services;


import esportapplication.code.models.User;
import esportapplication.code.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByUsername(String username){

        return repo.getByUsername(username);
    }

    public Page<User> getUsers(Pageable pageable){
        return repo.findAll(pageable);
    }


    public User getUser(Long id){
       return repo.findById(id).get();
    }


    public User save(User user, UserDetails creator){
        if(user.getId() == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
        User tmp = repo.getOne(user.getId());
        user.setGroups(tmp.getGroups());
        user.setPrivileges(tmp.getPrivileges());
        }
        return repo.save(user);
    }

    public User resetPassword(User user, UserDetails creator){
        User tmp = repo.getByUsername(user.getUsername());
        User tmp1 = user;
        user = tmp;
        user.setPassword(passwordEncoder.encode(tmp1.getPassword()));
        return repo.save(user);
    }

    public User save(User user){
        if(user.getId()==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repo.save(user);
    }

    public Boolean remove(Long id, UserDetails creator){
        User user = repo.getOne(id);
        repo.save(user);
        repo.deleteById(id);
        return Boolean.TRUE;
    }
}
