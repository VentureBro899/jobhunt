package venture.dev.venturejobhunt.service;

import venture.dev.venturejobhunt.domain.Notice;

public interface NoticeService {
    boolean update(Notice notice);
    Notice get();
}
