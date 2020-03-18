package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.Notice;
import com.weirdo.easycode.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 通告(Notice)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:08:26
 */
@RestController
@RequestMapping("notice")
public class NoticeController {
    /**
     * 服务对象
     */
    @Resource
    private NoticeService noticeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Notice selectOne(Integer id) {
        return this.noticeService.queryById(id);
    }

}