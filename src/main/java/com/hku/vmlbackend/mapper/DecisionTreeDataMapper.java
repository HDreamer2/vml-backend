package com.hku.vmlbackend.mapper;

import com.hku.vmlbackend.annotation.AutoFill;
import com.hku.vmlbackend.entity.DecisionTreeData;
import com.hku.vmlbackend.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * AUTHOR:XYS
 * DESCRIPCTION:NO BUG
 */
@Mapper
public interface DecisionTreeDataMapper {

    @AutoFill(OperationType.INSERT)
    @Insert("insert into decision_tree_data (data, user_id,file_id,create_time, update_time) " +
            "values "+ "(#{data},#{userId},#{fileId},#{createTime},#{updateTime})")
    void insert(DecisionTreeData decisionTreeData);

    @Select("select * from decision_tree_data where user_id = #{userId} and file_id = #{fileId}")
    List<DecisionTreeData> getByUserIdAndFileId(
            @Param("userId") Integer userId,
            @Param("fileId") String fileId);
}
