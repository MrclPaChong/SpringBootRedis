package com.weirdo.easycode.controller;

import com.weirdo.easycode.entity.Problem;
import com.weirdo.easycode.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 问题库列表(Problem)表控制层
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@RestController
@RequestMapping("problem")
public class ProblemController {
    /**
     * 服务对象
     */
    @Resource
    private ProblemService problemService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Problem selectOne(Integer id) {
        return this.problemService.queryById(id);
    }

}