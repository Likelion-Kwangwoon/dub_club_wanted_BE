package com.likelion.dub.repository;


import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
    Optional<Post> findByClubName(String clubName);
    Optional<Post> findById(Long id);
    ;

    // Optional<Member> findByEmail(String email);
}
