package com.sayone.obr.ui.controller;

import com.sayone.obr.dto.UserDto;
import com.sayone.obr.exception.AdminErrorMessages;
import com.sayone.obr.exception.PublisherErrorMessages;
import com.sayone.obr.model.request.AdminDetailsRequestModel;
import com.sayone.obr.model.request.PublisherDetailsRequestModel;
import com.sayone.obr.model.response.AdminRestModel;
import com.sayone.obr.model.response.PublisherRestModel;
import com.sayone.obr.model.response.UserRestModel;
import com.sayone.obr.repository.BookRepository;
import com.sayone.obr.service.BookService;
import com.sayone.obr.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/admin/get-publishers")
    public PublisherRestModel getAllPublishers(String role) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        if (!Objects.equals(user.getRole(), "admin")) throw new Exception(AdminErrorMessages.NO_ADMIN_FOUND.getAdminErrorMessages());

        PublisherRestModel returnValue = new PublisherRestModel();

        UserDto userDto = userService.getAllPublishersByRole();
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @GetMapping("/admin/get-users")
    public UserRestModel getAllUsers() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        if (!Objects.equals(user.getRole(), "admin")) throw new Exception(AdminErrorMessages.NO_ADMIN_FOUND.getAdminErrorMessages());

        UserRestModel returnValue = new UserRestModel();

        UserDto userDto = userService.getAllUsersByRole();
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping("admin/signup")
    public AdminRestModel createAdmin(@RequestBody AdminDetailsRequestModel adminDetails) throws Exception {

        AdminRestModel returnValue = new AdminRestModel();

        if (adminDetails.getEmail().isEmpty() || adminDetails.getPassword().isEmpty()) throw new Exception(PublisherErrorMessages.MISSING_REQUIRED_FIELD.getPublisherErrorMessages());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(adminDetails, userDto);

        UserDto createdAdmin = userService.createUser(userDto);
        BeanUtils.copyProperties(createdAdmin, returnValue);

        return returnValue;
    }

//    @PutMapping("/admin/update")
//    public PublisherRestModel updatePublisher(@RequestBody PublisherDetailsRequestModel publisherDetails) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//
//        PublisherRestModel returnValue = new PublisherRestModel();
//
//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(publisherDetails, userDto);
//
//        UserDto updatedPublisher = userService.updatePublisher(user.getUserId(), userDto);
//        BeanUtils.copyProperties(updatedPublisher, returnValue);
//
//        return returnValue;
//    }

    @DeleteMapping("/admin/del/publisher/{id}")
    public void deletePublisher(@PathVariable String id) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        if (!Objects.equals(user.getRole(), "admin")) throw new Exception(AdminErrorMessages.NO_ADMIN_FOUND.getAdminErrorMessages());

        userService.deletePublisher(id);
    }

    @DeleteMapping("/admin/del/user/{id}")
    public void deleteUser(@PathVariable String id) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        if (!Objects.equals(user.getRole(), "admin")) throw new Exception(AdminErrorMessages.NO_ADMIN_FOUND.getAdminErrorMessages());

        userService.deleteUser(id);
    }

    @DeleteMapping("/admin/del/book/{id}")
    public void deletePostedBook(@PathVariable Long id) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        if (!Objects.equals(user.getRole(), "admin")) throw new Exception(AdminErrorMessages.NO_ADMIN_FOUND.getAdminErrorMessages());

        bookService.deletePostedBookByAdmin(id);
    }
}
