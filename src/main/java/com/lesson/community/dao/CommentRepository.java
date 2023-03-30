package com.lesson.community.dao;

import com.lesson.community.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query(value = "select * from comment where entity_id = :id and entity_type = :type " +
            "order by create_time asc limit :offset,:limit", nativeQuery = true)
    List<CommentEntity> getCommentsByType(@Param("type") int EntityType, @Param("id") int EntityID,
                                          @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(*) from CommentEntity where entityType=:type and entityId=:id")
    int getCountByType(@Param("type") int EntityType, @Param("id") int EntityID);


}
