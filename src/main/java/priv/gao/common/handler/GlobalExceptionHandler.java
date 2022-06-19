package priv.gao.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gao.common.bean.MyException;
import priv.gao.common.helper.Constant;
import priv.gao.common.helper.RetMsgUtils;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 全局异常捕获
 * @date 2021/8/12 17:35
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局自定义异常捕获处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public String myExceptionHandler(MyException ex) {
        ex.printStackTrace();
        String level = ex.getExceptionLevel();
        switch (level){
            case Constant.RETMSG_INFO:
                return RetMsgUtils.info(ex.getMessage());
            case Constant.RETMSG_WARNING:
                return RetMsgUtils.warning(ex.getMessage());
            case Constant.RETMSG_FAIL:
                return RetMsgUtils.fail(ex.getMessage());
            default:
                return RetMsgUtils.error(ex.getMessage());
        }
    }

    /**
     * 全局异常捕获处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception ex) {
        ex.printStackTrace();
        return RetMsgUtils.error("系统异常，请联系管理员！");
    }
}
