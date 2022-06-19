package priv.gao.game.poker.p1_doudizhu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.gao.room.service.RoomService;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/24 17:43
 */
@Controller
@RequestMapping(value = "/ddz1")
public class DoudizhuController {

    @Autowired
    RoomService roomServiceImpl;

    @Autowired
    DouDiZhuServiceImpl douDiZhuService;
}
