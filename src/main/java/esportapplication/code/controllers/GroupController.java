package esportapplication.code.controllers;


import esportapplication.code.CustomUserDetails;
import esportapplication.code.models.Group;
import esportapplication.code.models.User;
import esportapplication.code.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/groups")
@Component
public class GroupController {

    @Autowired
    private GroupService groupService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Page<Group> getAllGroups(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size) {

        return groupService.getGroups(new PageRequest(page,size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable(value = "id") Long id){
        return groupService.getGroup(id);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Group createGroup(@RequestBody Group group, OAuth2Authentication authentication) {
        if(authentication != null) {
            CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
            return groupService.save(group, details);
        }
        return null;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Group updateGroup(@RequestBody Group group, OAuth2Authentication authentication) {
        if(authentication != null) {
            CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
            return groupService.save(group, details);
        }
        return null;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Boolean deleteRole(@PathVariable(value = "id") Long id, OAuth2Authentication authentication) {
        if(authentication != null) {
            CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
            return groupService.remove(id, details);
        }
        return null;
    }

    @RequestMapping(value = "/{id}/members", method = RequestMethod.GET)
    public Page<User> getMembers(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size ) {
        return groupService.getGroupMembers(id, new PageRequest(page, size));
    }

    @RequestMapping(value = "/{id}/add_member", method = RequestMethod.POST)
    public Boolean getMembers(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "userId") Long userId ) {
        return groupService.addUserToGroup(id, userId);
    }

    @RequestMapping(value = "/{id}/remove_member", method = RequestMethod.DELETE)
    public Boolean removeMember(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "userId") Long userId ) {
        return groupService.removeUserFromGroup(id, userId);
    }

}
