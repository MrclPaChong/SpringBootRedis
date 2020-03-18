package com.weirdo.easycode.service;

import com.weirdo.easycode.entity.SysConfig;
import java.util.List;

/**
 * 字典配置表(SysConfig)表服务接口
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public interface SysConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysConfig queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysConfig 实例对象
     * @return 实例对象
     */
    SysConfig insert(SysConfig sysConfig);

    /**
     * 修改数据
     *
     * @param sysConfig 实例对象
     * @return 实例对象
     */
    SysConfig update(SysConfig sysConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}