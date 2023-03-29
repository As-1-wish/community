package com.lesson.community.dao;

import com.lesson.community.entity.DiscussPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussPostDao extends JpaRepository<DiscussPostEntity, Integer> {

    @Query(value = "SELECT count(*) from discuss_post  where status != 2 and " +
            "if(:userid != 0, user_id=:userid,1=1)", nativeQuery = true)
    int getDiscussPostRows(@Param("userid") int userid);

    @Query(value = "SELECT * from discuss_post where status != 2 and " +
            "if(:userid !=0, user_id = :userid, 1=1) order by type desc, create_time desc " +
            "limit :offset, :limit", nativeQuery = true)
    List<DiscussPostEntity> getDiscussPosts(@Param("userid") int userid,
                                            @Param("offset") int offset,
                                             @Param("limit") int limit);

    @Query(value = "select dis from DiscussPostEntity dis where dis.id = :id")
    DiscussPostEntity getDiscussPostById(@Param("id")int id);
}
