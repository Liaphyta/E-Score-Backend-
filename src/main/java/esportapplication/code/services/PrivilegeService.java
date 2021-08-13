package esportapplication.code.services;

import esportapplication.code.models.Group;
import esportapplication.code.models.Privilege;
import esportapplication.code.repositories.GroupRepository;
import esportapplication.code.repositories.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    PrivilegeRepository repo;

    @Autowired
    GroupRepository groupRepository;

    public Privilege findByName(String username){
        return repo.findByName(username);
    }

    public List<Privilege> getPrivileges(){
        return repo.findAll();
    }

    public Privilege getPrivilege(Long id){
        return repo.findById(id).get();
    }

    @Transactional
    public Privilege addPrivilegeToGroup(Long privilegeId, Long groupId){
        Privilege privilege = getPrivilege(privilegeId);
        Group group = groupRepository.getOne(groupId);
        privilege.getGroups().add(group);

        return repo.save(privilege);
    }

    public Privilege save(Privilege privilege){
        return repo.save(privilege);
    }


    public Privilege removePrivilegeFromGroup(Long privilegeId, Long groupId){
        Privilege privilege = getPrivilege(privilegeId);
        Group group = groupRepository.getOne(groupId);
        privilege.getGroups().remove(group);

        return repo.save(privilege);
    }

    public Boolean remove(Long id){
        repo.deleteById(id);
        return Boolean.TRUE;
    }
}
