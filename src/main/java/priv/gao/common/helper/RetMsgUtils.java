package priv.gao.common.helper;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;

public class RetMsgUtils {

    /**
     * 消息
     * @return
     */
    public static String info(String retContent){
        HashMap retMap = new HashMap();
        retMap.put("retCode", Constant.RETMSG_INFO);
        retMap.put("retText",retContent);
        retMap.put("status",true);
        return JSONObject.toJSONString(retMap);
    }

    /**
     * 警告
     * @return
     */
    public static String warning(String retContent){
        HashMap retMap = new HashMap();
        retMap.put("retCode",Constant.RETMSG_WARNING);
        retMap.put("retText",retContent);
        retMap.put("status",false);
        return JSONObject.toJSONString(retMap);
    }

    /**
     * 成功(操作成功)
     * @param obj
     * @return
     */
    public static String success(Object... obj){
        HashMap retMap = new HashMap();
        retMap.put("retCode",Constant.RETMSG_SUCCESS);
        retMap.put("status",true);
        if (obj != null && obj.length != 0){
            retMap.put("retData",obj);
        }
        return JSONObject.toJSONString(retMap);
    }

    /**
     * 失败（操作失败）
     * @param retContent
     * @return
     */
    public static String fail(String retContent){
        HashMap retMap = new HashMap();
        retMap.put("retCode",Constant.RETMSG_FAIL);
        retMap.put("retText",retContent);
        retMap.put("status",false);
        return JSONObject.toJSONString(retMap);
    }

    /**
     * 异常（操作异常）
     * @return
     */
    public static String error(Object retContent){
        HashMap retMap = new HashMap();
        retMap.put("retCode",Constant.RETMSG_ERROR);
        retMap.put("retText",retContent);
        retMap.put("status",false);
        return JSONObject.toJSONString(retMap);
    }

}
