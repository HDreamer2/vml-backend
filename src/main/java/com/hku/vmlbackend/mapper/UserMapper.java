package com.hku.vmlbackend.mapper;
import com.hku.vmlbackend.annotation.AutoFill;
import com.hku.vmlbackend.entity.User;
import com.hku.vmlbackend.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @AutoFill(OperationType.INSERT)
    @Insert("insert into user (username, password, create_time, update_time) " +
            "values "+ "(#{username},#{password},#{createTime},#{updateTime})")
    void insert(User user);

    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

}
