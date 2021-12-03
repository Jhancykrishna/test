package com.sayone.obr.repository;

import com.sayone.obr.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    @Query(value = "select * from wishlist w where w.user_id = ?1", nativeQuery = true)
    Optional<WishlistEntity> findByUserId(Long Id);

    @Query(value = "select * from wishlist w where w.user_id = ?1", nativeQuery = true)
    WishlistEntity getByUserId(Long Id);

    @Transactional
    @Modifying
    @Query(value = "delete from wishlist_products w where w.wishlist_id=?1 and w.book_id=?2", nativeQuery = true)
    void deleteProduct(Long wishlistId, Long bookId);
}


