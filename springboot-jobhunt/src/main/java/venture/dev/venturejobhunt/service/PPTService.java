package venture.dev.venturejobhunt.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import venture.dev.venturejobhunt.domain.PPT;

public interface PPTService {

    boolean add(PPT ppt);
    PPT getByid(int id);
    boolean del(int id);
    boolean upd(PPT ppt);
    IPage<PPT> findAll(int current, int size,boolean auth);
}
