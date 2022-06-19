package priv.gao.room.service;

import priv.gao.room.bean.Room;
import priv.gao.room.bean.RoomRequest;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/16 17:37
 */
public interface RoomService {
    //创建房间
    Room create(RoomRequest roomReq);

    //加入房间
    Room join(RoomRequest roomReq);

    //离开房间
    void exit(RoomRequest roomReq);

    //匹配
    void match(RoomRequest roomReq);

    //取消匹配
    void unmatch(RoomRequest roomReq);

    //获取房间信息
    Room getRoomInfo(int roomCode);

    //更新房间信息
    void updateRoomInfo(Room room);

    //更新房间信息
    void updateRoomInfo4R(String roomKey, Room room);

    //玩家准备
    void prepare(RoomRequest roomReq);


}
