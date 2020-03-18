package com.weirdo.easycode.service;

import com.weirdo.easycode.entity.Item;
import java.util.List;

/**
 * 商品信息表(Item)表服务接口
 *
 * @author makejava
 * @since 2020-03-16 16:59:27
 */
public interface ItemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Item queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Item> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param item 实例对象
     * @return 实例对象
     */
    Item insert(Item item);

    /**
     * 修改数据
     *
     * @param item 实例对象
     * @return 实例对象
     */
    Item update(Item item);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}