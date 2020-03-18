package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.RedRobRecord;
import com.weirdo.easycode.service.RedRobRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 抢红包记录(RedRobRecord)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@RestController
@RequestMapping("redRobRecord")
public class RedRobRecordController {
    /**
     * 服务对象
     */
    @Resource
    private RedRobRecordService redRobRecordService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public RedRobRecord selectOne(Integer id) {
        return this.redRobRecordService.queryById(id);
    }

}