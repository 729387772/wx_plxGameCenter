package priv.gao.room.dao;

import org.springframework.stereotype.Repository;
import priv.gao.room.bean.Room;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/13 17:44
 */
@Repository
public interface RoomDao {

    void saveRoomInfo(Room room);

    void deleteRoomByRoomCode(int roomCode);

    void updateRoomInfo(Room room);

    Room queryRoomInfo(int roomCode);
}
