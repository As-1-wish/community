package com.lesson.community.dao;

import com.lesson.community.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<CommentEntity, Integer> {

}
