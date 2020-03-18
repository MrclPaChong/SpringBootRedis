package com.weirdo.easycode.service.impl;

import com.weirdo.easycode.entity.Problem;
import com.weirdo.easycode.dao.ProblemDao;
import com.weirdo.easycode.service.ProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 问题库列表(Problem)表服务实现类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Service("problemService")
public class ProblemServiceImpl implements ProblemService {
    @Resource
    private ProblemDao problemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Problem queryById(Integer id) {
        return this.problemDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Problem> queryAllByLimit(int offset, int limit) {
        return this.problemDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param problem 实例对象
     * @return 实例对象
     */
    @Override
    public Problem insert(Problem problem) {
        this.problemDao.insert(problem);
        return problem;
    }

    /**
     * 修改数据
     *
     * @param problem 实例对象
     * @return 实例对象
     */
    @Override
    public Problem update(Problem problem) {
        this.problemDao.update(problem);
        return this.queryById(problem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.problemDao.deleteById(id) > 0;
    }
}