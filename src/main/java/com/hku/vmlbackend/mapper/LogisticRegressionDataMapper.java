package com.hku.vmlbackend.mapper;

import com.hku.vmlbackend.annotation.AutoFill;
import com.hku.vmlbackend.entity.DecisionTreeData;
import com.hku.vmlbackend.entity.LogisticRegressionData;
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
public interface LogisticRegressionDataMapper {

    @AutoFill(OperationType.INSERT)
    @Insert("insert into logistic_regression_data (user_id,file_id,epoch,weights,bias,loss,feature_weights,create_time, update_time) " +
            "values "+ "(#{userId},#{fileId},#{epoch},#{weights},#{bias},#{loss},#{featureWeights},#{createTime},#{updateTime})")
    void insert(LogisticRegressionData logisticRegressionData);

    @Select("select * from logistic_regression_data where user_id = #{userId} and file_id = #{fileId}")
    List<LogisticRegressionData> getByUserIdAndFileId(
            @Param("userId") Integer userId,
            @Param("fileId") String fileId);
}
