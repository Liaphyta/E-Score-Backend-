package esportapplication.code.controllers;


import esportapplication.code.models.Group;
import esportapplication.code.models.Privilege;
import esportapplication.code.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/privileges")
@Component
public class PrivilegeController {


    @Autowired
    private PrivilegeService privilegeService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Privilege> getAllPrivileges() {
        return privilegeService.getPrivileges();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Privilege getPrivilege(@PathVariable(value = "id") Long id){
        return privilegeService.getPrivilege(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Boolean deletePrivilege(@PathVariable(value = "id") Long id) {
        return privilegeService.remove(id);
    }


    @RequestMapping(value = "/{id}/groups", method = RequestMethod.GET)
    public List<Group> getGroupsByPrivilege(@PathVariable(value = "id") Long id){
        return privilegeService.getPrivilege(id).getGroups();
    }

    @RequestMapping(value = "/{id}/groups/add", method = RequestMethod.POST)
    public Privilege addPrivilegeToGroup(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "groupId") Long groupId
            ){
        return privilegeService.addPrivilegeToGroup(id, groupId);
    }

    @RequestMapping(value = "/{id}/groups/{groupId}", method = RequestMethod.DELETE)
    public Privilege removePrivilegeFromGroup(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "groupId") Long groupId
    ){
        return privilegeService.removePrivilegeFromGroup(id, groupId);
    }
}
