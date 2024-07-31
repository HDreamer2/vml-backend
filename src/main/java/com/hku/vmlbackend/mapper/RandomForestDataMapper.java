package com.hku.vmlbackend.mapper;

import com.hku.vmlbackend.annotation.AutoFill;
import com.hku.vmlbackend.entity.RandomForestData;
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
public interface RandomForestDataMapper {

    @AutoFill(OperationType.INSERT)
    @Insert("insert into random_forest_data (tree_index, features, tree, user_id,file_id,create_time, update_time) " +
            "values "+ "(#{treeIndex},#{features},#{tree},#{userId},#{fileId},#{createTime},#{updateTime})")
    void insert(RandomForestData randomForestData);

    @Select("select * from random_forest_data where user_id = #{userId} and file_id = #{fileId}")
    List<RandomForestData> getByUserIdAndFileId(
            @Param("userId") Integer userId,
            @Param("fileId") String fileId);
}
