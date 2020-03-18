package com.weirdo.easycode.service;

import com.weirdo.easycode.entity.RedRobRecord;
import java.util.List;

/**
 * 抢红包记录(RedRobRecord)表服务接口
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public interface RedRobRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RedRobRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RedRobRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param redRobRecord 实例对象
     * @return 实例对象
     */
    RedRobRecord insert(RedRobRecord redRobRecord);

    /**
     * 修改数据
     *
     * @param redRobRecord 实例对象
     * @return 实例对象
     */
    RedRobRecord update(RedRobRecord redRobRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}