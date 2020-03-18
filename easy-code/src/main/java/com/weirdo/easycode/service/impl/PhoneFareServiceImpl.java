package com.weirdo.easycode.service.impl;

import com.weirdo.easycode.entity.PhoneFare;
import com.weirdo.easycode.dao.PhoneFareDao;
import com.weirdo.easycode.service.PhoneFareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 手机充值记录(PhoneFare)表服务实现类
 *
 * @author makejava
 * @since 2020-03-16 17:09:12
 */
@Service("phoneFareService")
public class PhoneFareServiceImpl implements PhoneFareService {
    @Resource
    private PhoneFareDao phoneFareDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PhoneFare queryById(Integer id) {
        return this.phoneFareDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<PhoneFare> queryAllByLimit(int offset, int limit) {
        return this.phoneFareDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param phoneFare 实例对象
     * @return 实例对象
     */
    @Override
    public PhoneFare insert(PhoneFare phoneFare) {
        this.phoneFareDao.insert(phoneFare);
        return phoneFare;
    }

    /**
     * 修改数据
     *
     * @param phoneFare 实例对象
     * @return 实例对象
     */
    @Override
    public PhoneFare update(PhoneFare phoneFare) {
        this.phoneFareDao.update(phoneFare);
        return this.queryById(phoneFare.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.phoneFareDao.deleteById(id) > 0;
    }
}