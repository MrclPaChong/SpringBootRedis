package com.weirdo.model.dao;


import com.weirdo.model.entity.RedRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发红包记录(RedRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public interface RedRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RedRecord queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RedRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param redRecord 实例对象
     * @return 对象列表
     */
    List<RedRecord> queryAll(RedRecord redRecord);

    /**
     * 新增数据
     *
     * @param redRecord 实例对象
     * @return 影响行数
     */
    int insert(RedRecord redRecord);

    /**
     * 修改数据
     *
     * @param redRecord 实例对象
     * @return 影响行数
     */
    int update(RedRecord redRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}