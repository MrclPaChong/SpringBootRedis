package com.weirdo.easycode.service.impl;

import com.weirdo.easycode.entity.RedDetail;
import com.weirdo.easycode.dao.RedDetailDao;
import com.weirdo.easycode.service.RedDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 红包明细金额(RedDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Service("redDetailService")
public class RedDetailServiceImpl implements RedDetailService {
    @Resource
    private RedDetailDao redDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RedDetail queryById(Integer id) {
        return this.redDetailDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<RedDetail> queryAllByLimit(int offset, int limit) {
        return this.redDetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param redDetail 实例对象
     * @return 实例对象
     */
    @Override
    public RedDetail insert(RedDetail redDetail) {
        this.redDetailDao.insert(redDetail);
        return redDetail;
    }

    /**
     * 修改数据
     *
     * @param redDetail 实例对象
     * @return 实例对象
     */
    @Override
    public RedDetail update(RedDetail redDetail) {
        this.redDetailDao.update(redDetail);
        return this.queryById(redDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.redDetailDao.deleteById(id) > 0;
    }
}