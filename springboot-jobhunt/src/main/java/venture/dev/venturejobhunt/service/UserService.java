package venture.dev.venturejobhunt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import venture.dev.venturejobhunt.domain.User;

public interface UserService {
    /**
     * 通过手机号或邮箱和密码登录
     *
     * @param
     * @return venture.dev.venturejobhunt.domain.Job
     * @author venture
     * @creed: Nothing Ventured,nothing gained
     * @date 2022/7/12 17:15
     */
    User login(String u, String password);

    boolean register(User user);

    User getInfo(int id);

    boolean update(User user);

    boolean sign(int userid);

    public IPage<User> getAll(int current, int size, String key);

    public boolean delete(int id);

    public String getPassword(int id);


}
