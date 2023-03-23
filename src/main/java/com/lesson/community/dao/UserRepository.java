package com.lesson.community.dao;

import com.lesson.community.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository //同@Service、@Controller 和 @Component 将类标识为Bean,只能用在Dao层
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    //nativeQuery = true 表明是可以执行原生语句(将此句复制到mysql,给一个参数就可以运行)
    @Query(value = "SELECT u FROM UserEntity u WHERE u.id = ?1")
    UserEntity getUserEntityByID(Integer id); //通过ID查询用户

    @Query(value = "SELECT u FROM UserEntity u WHERE u.username = ?1")
    UserEntity getUserEntityByName(String name); //通过姓名查询用户

    @Query(value = "SELECT u FROM UserEntity u WHERE u.email = ?1")
    UserEntity getUserEntityByEmail(String email); //通过邮箱查询用户

    @Modifying //通知 SpringData 这是一个 DELETE 或 UPDATE 操作,必须添加
    @Transactional //代码执行出错的时候能够进行事务的回滚
    @Query(value = "UPDATE UserEntity SET status = :status WHERE id = :id")
    int UpdateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserEntity SET headerUrl = :headerUrl WHERE id = :id")
    int UpdateHeader(@Param("id") Integer id, @Param("headerUrl") String headerUrl);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserEntity SET password = :password WHERE id = :id")
    int UpdatePassword(@Param("id") Integer id, @Param("password") String password);
}
