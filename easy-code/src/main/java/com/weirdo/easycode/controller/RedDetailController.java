package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.RedDetail;
import com.weirdo.easycode.service.RedDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 红包明细金额(RedDetail)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@RestController
@RequestMapping("redDetail")
public class RedDetailController {
    /**
     * 服务对象
     */
    @Resource
    private RedDetailService redDetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public RedDetail selectOne(Integer id) {
        return this.redDetailService.queryById(id);
    }

}