package esportapplication.code.services;


import esportapplication.code.models.Group;
import esportapplication.code.models.User;
import esportapplication.code.repositories.GroupRepository;
import esportapplication.code.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class GroupService {

    @Autowired
    GroupRepository repo;

    @Autowired
    UserRepository userRepo;

    public Group findGroupByName(String username){
        return repo.findByName(username);
    }

    public Page<Group> getGroups(Pageable pageable){
        return repo.findAll(pageable);
    }

    public List<Group> getGroups(){
        return repo.findAll();
    }

    public Group getGroup(Long id){
       return repo.findById(id).get();
    }

    public Group save(Group g, UserDetails creator){
        Group group = new Group();
        if(g.getId() != null){
            group = repo.getOne(g.getId());
        }
        group.setName(g.getName());
        return repo.save(group);
    }

    public Group save(Group g){
        Group group = new Group();
        if(g.getId() != null){
            group = repo.getOne(g.getId());
        }
        group.setName(g.getName());
        return repo.save(group);
    }

    public Boolean remove(Long id, UserDetails creator){
        Group group = repo.getOne(id);
        repo.save(group);
        repo.deleteById(id);
        return Boolean.TRUE;
    }

    public Page<User> getGroupMembers(Long id, PageRequest pageRequest){
        return userRepo.findByGroupsId(id, pageRequest);
    }

    @Transactional
    public Boolean addUserToGroup(Long groupId, Long userId){
        Group group = repo.getOne(groupId);
        User user1 = userRepo.getOne(userId);
        user1.getGroups().add(group);
        userRepo.save(user1);
        return Boolean.TRUE;
    }

    public Boolean removeUserFromGroup(Long groupId, Long userId){
        Group group = repo.getOne(groupId);
        User user = userRepo.getOne(userId);
        user.getGroups().remove(group);
        userRepo.save(user);
        return Boolean.TRUE;
    }

}
