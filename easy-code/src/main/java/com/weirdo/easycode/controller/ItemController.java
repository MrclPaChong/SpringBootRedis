package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.Item;
import com.weirdo.easycode.service.ItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品信息表(Item)表控制层
 *
 * @author makejava
 * @since 2020-03-16 16:59:28
 */
@RestController
@RequestMapping("item")
public class ItemController {
    /**
     * 服务对象
     */
    @Resource
    private ItemService itemService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Item selectOne(Integer id) {
        return this.itemService.queryById(id);
    }

}