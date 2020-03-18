package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.RedRecord;
import com.weirdo.easycode.service.RedRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 发红包记录(RedRecord)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@RestController
@RequestMapping("redRecord")
public class RedRecordController {
    /**
     * 服务对象
     */
    @Resource
    private RedRecordService redRecordService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public RedRecord selectOne(Integer id) {
        return this.redRecordService.queryById(id);
    }

}