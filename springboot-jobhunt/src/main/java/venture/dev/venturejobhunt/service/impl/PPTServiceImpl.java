package venture.dev.venturejobhunt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import venture.dev.venturejobhunt.dao.PPTDao;
import venture.dev.venturejobhunt.domain.PPT;
import venture.dev.venturejobhunt.service.PPTService;

@Service
public class PPTServiceImpl implements PPTService {
    @Autowired
    PPTDao pptDao;

    @Override
    public boolean add(PPT ppt) {
        return pptDao.insert(ppt) > 0;
    }

    @Override
    public PPT getByid(int id) {
        return pptDao.selectById(id);
    }

    @Override
    public boolean del(int id) {
        return pptDao.deleteById(id) > 0;
    }

    @Override
    public boolean upd(PPT ppt) {
        return pptDao.updateById(ppt) > 0;
    }

    @Override
    public IPage<PPT> findAll(int current, int size,boolean auth) {
        IPage<PPT> page = new Page(current,size);
        LambdaQueryWrapper<PPT> lqw = new LambdaQueryWrapper<>();
        lqw.eq(!auth,PPT::isShowing,true);
        pptDao.selectPage(page,lqw);
        return page;
    }
}
