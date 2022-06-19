package priv.gao.common.helper;

import java.util.UUID;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/16 17:51
 */
public class SysIdCreator {
    //后续优化......


    /**
     * 获取uuid
     * @param len 长度
     * @return
     */
    public static String getUUID(int len){
        return UUID.randomUUID().toString().replace("-", "").substring(32-len);
    }

    /**
     * 获取业务主键，非连续
     */
    public static String getPrimaryKeyByCode(String code) {
        return null;
    }
}
