package com.sayone.obr.ui.controller;

import com.sayone.obr.dto.UserDto;
import com.sayone.obr.entity.BookEntity;
import com.sayone.obr.entity.WishlistEntity;
import com.sayone.obr.exception.UserServiceException;
import com.sayone.obr.exception.WishlistErrors;
import com.sayone.obr.model.response.ReviewResponseModel;
import com.sayone.obr.service.UserService;
import com.sayone.obr.service.WishlistService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

//wishlist
@RestController
@RequestMapping("wishlist")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    UserService userService;


    //http://localhost:8080/wishlist/add/2
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping("add/{bid}")
    public WishlistEntity addToWishlist(@PathVariable(value = "bid") Long bookId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        WishlistEntity wishlistEntity = wishlistService.addProductToWishlist(user.getId(), bookId);
        return wishlistEntity;

    }


//    //http://localhost:8080/wishlist/delete/{bid}
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})

    @DeleteMapping("delete/{bid}")
    public String deleteFromWishlist(@PathVariable (value = "bid") Long bookId){

        if (bookId == null ){
            throw new UserServiceException(WishlistErrors.WISH_BID_NOTFOUND.getErrorMessage());
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            wishlistService.removeProductFromWishlist(user.getId(), bookId);
            return" book deleted from wishlist";
        }
    }

    //http://localhost:8080/wishlist/get
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}", paramType = "header")})

    @GetMapping("get")
    public List<BookEntity> getWishlistItems(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = userService.getUser(auth.getName());
        return wishlistService.getWishlistItems(user.getId());
    }
}
