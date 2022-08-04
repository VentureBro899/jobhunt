package venture.dev.venturejobhunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import venture.dev.venturejobhunt.domain.Notice;
import venture.dev.venturejobhunt.domain.R;
import venture.dev.venturejobhunt.service.NoticeService;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping
    public R get(){
        Notice notice = noticeService.get();
        return new R(notice);
    }

    @PutMapping
    public R upd(@RequestBody Notice notice){
        boolean flag = noticeService.update(notice);
        return new R(flag);
    }
}
