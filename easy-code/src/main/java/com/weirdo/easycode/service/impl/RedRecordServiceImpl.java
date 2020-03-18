package com.weirdo.easycode.service.impl;

import com.weirdo.easycode.entity.RedRecord;
import com.weirdo.easycode.dao.RedRecordDao;
import com.weirdo.easycode.service.RedRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 发红包记录(RedRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Service("redRecordService")
public class RedRecordServiceImpl implements RedRecordService {
    @Resource
    private RedRecordDao redRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RedRecord queryById(Integer id) {
        return this.redRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<RedRecord> queryAllByLimit(int offset, int limit) {
        return this.redRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param redRecord 实例对象
     * @return 实例对象
     */
    @Override
    public RedRecord insert(RedRecord redRecord) {
        this.redRecordDao.insert(redRecord);
        return redRecord;
    }

    /**
     * 修改数据
     *
     * @param redRecord 实例对象
     * @return 实例对象
     */
    @Override
    public RedRecord update(RedRecord redRecord) {
        this.redRecordDao.update(redRecord);
        return this.queryById(redRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.redRecordDao.deleteById(id) > 0;
    }
}