package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.PhoneFare;
import com.weirdo.easycode.service.PhoneFareService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 手机充值记录(PhoneFare)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:09:12
 */
@RestController
@RequestMapping("phoneFare")
public class PhoneFareController {
    /**
     * 服务对象
     */
    @Resource
    private PhoneFareService phoneFareService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public PhoneFare selectOne(Integer id) {
        return this.phoneFareService.queryById(id);
    }

}