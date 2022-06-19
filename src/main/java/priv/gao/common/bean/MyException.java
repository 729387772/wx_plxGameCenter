package priv.gao.common.bean;

import java.util.Collections;
import java.util.List;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 自定义异常
 * @date 2021/8/12 17:35
 */
public class MyException extends RuntimeException {

    private String exceptionLevel;

    private String[] exceptionNo = new String[0]; //批量处理时，返回异常的单据号

    public MyException(String message, String exceptionLevel) {
        super(message);
        this.exceptionLevel = exceptionLevel;
    }

    public MyException(String message, String exceptionLevel, String[] exceptionNo) {
        super(message);
        this.exceptionLevel = exceptionLevel;
        this.exceptionNo = exceptionNo;
    }

    public MyException(String message, String exceptionLevel, List<String> exceptionNoList) {
        super(message);
        this.exceptionLevel = exceptionLevel;
        this.exceptionNo = exceptionNoList.toArray(new String[0]);
    }

    public String getExceptionLevel() {
        return exceptionLevel;
    }

    public String[] getExceptionNo() {
        return exceptionNo;
    }
}
