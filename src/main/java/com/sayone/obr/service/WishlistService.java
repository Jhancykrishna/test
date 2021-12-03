package com.sayone.obr.service;


import com.sayone.obr.entity.BookEntity;
import com.sayone.obr.entity.WishlistEntity;
import com.sayone.obr.exception.UserServiceException;
import com.sayone.obr.exception.WishlistErrors;
import com.sayone.obr.repository.BookRepository;
import com.sayone.obr.repository.UserRepository;
import com.sayone.obr.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    public void removeProductFromWishlist(Long Id, Long bookId) {
        Optional<WishlistEntity> wishlistEntity =wishlistRepository.findByUserId(Id);
        WishlistEntity wishlistEntity1 = wishlistEntity.get();
        Long wishlistId =wishlistEntity1.getWishlistId();
        wishlistRepository.deleteProduct(wishlistId,bookId);
    }

    public WishlistEntity addProductToWishlist(Long Id, Long bookId) {
        Optional<WishlistEntity> wishlistEntity = wishlistRepository.findByUserId(Id);
        WishlistEntity wishlistEntity1 = new WishlistEntity();
        if (wishlistEntity.isEmpty()) {
            wishlistEntity1.setUserEntity(userRepository.getById(Id));
            BookEntity bookEntity = bookRepository.findById(bookId).get();
            wishlistEntity1.setBookEntityList(List.of(bookEntity));
        }
        else {
            wishlistEntity1 = wishlistEntity.get();
            BookEntity bookEntity = bookRepository.findById(bookId).get();
            for (int i = 0; i < wishlistEntity1.getBookEntityList().size(); i++) {
                if (bookEntity == wishlistEntity1.getBookEntityList().get(i)) {
                    throw new UserServiceException(WishlistErrors.WISH_PRODUCT_EXISTS.getErrorMessage());
                }
            }
            wishlistEntity1.getBookEntityList().add(bookEntity);
        }
        return wishlistRepository.save(wishlistEntity1);
    }



    public List<BookEntity> getWishlistItems(Long Id) {
        WishlistEntity wishlistEntity = wishlistRepository.getByUserId(Id);
        return wishlistEntity.getBookEntityList();
    }
}
