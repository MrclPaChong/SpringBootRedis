package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.SysConfig;
import com.weirdo.easycode.service.SysConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 字典配置表(SysConfig)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@RestController
@RequestMapping("sysConfig")
public class SysConfigController {
    /**
     * 服务对象
     */
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysConfig selectOne(Integer id) {
        return this.sysConfigService.queryById(id);
    }

}