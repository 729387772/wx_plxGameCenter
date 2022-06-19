package priv.gao.common.handler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import priv.gao.common.bean.MyException;
import priv.gao.common.helper.Constant;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/10/1 16:07
 */
@Component
@ServerEndpoint(value="/websocket/{userCode}")
public class WebSocketHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /** 存放所有在线的客户端 */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 建立连接
     * @param userCode
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userCode") String userCode, Session session){
        log.debug(userCode+"加入websocket连接");
        clients.put(userCode, session);
        printClientUsers();
    }

    /**
     * 关闭连接
     * @param userCode
     */
    @OnClose
    public void onClose(@PathParam("userCode") String userCode){
        log.debug(userCode+"断开websocket连接");
        clients.remove(userCode);
        printClientUsers();
    }

    /**
     * 接收消息
     * @param message
     * @param fromSession
     */
    @OnMessage
    public void onMessage(String message, Session fromSession) {
        if("000".equals(message)){
            fromSession.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 连接发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){

    }

    public void printClientUsers(){
        Iterator<String> iterator = clients.keySet().iterator();
        System.out.print("当前在线人数：" + clients.size());
        System.out.println();
        System.out.print("用户名单：");
        while(iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    /**
     * 发消息给指定人
     * @param message
     * @param toUser
     */
    public void sendMessageToOne(String message, String toUser){
        this.checkUserOnline(toUser);
        clients.get(toUser).getAsyncRemote().sendText(message);
        log.debug("给用户"+toUser+"发送消息："+message);
    }

    /**
     * 发消息给部分人 Array
     * @param message
     * @param toUsers
     */
    public void sendMessageToSomeOne(String message, String[] toUsers){
        this.checkUserOnline(toUsers);
        for (String toUser: toUsers) {
            this.sendMessageToOne(message,toUser);
        }
    }

    /**
     * 发消息给部分人 List
     * @param message
     * @param toUsers
     */
    public void sendMessageToSomeOne(String message, List<String> toUsers){
        this.sendMessageToSomeOne(message,toUsers.toArray(new String[0]));
    }
    /**
     * 发给除了自己的所有人
     * @param message
     * @param fromUser
     */
    public void sendMessageToAllButMe(String message, String fromUser){
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            String toUser = sessionEntry.getKey();
            if (!fromUser.equals(toUser)) {
                this.sendMessageToOne(message,toUser);
            }
        }
    }

    /**
     * 发给所有人
     * @param message
     */
    public void sendMessageToAll(String message){
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            this.sendMessageToOne(message,sessionEntry.getKey());
        }
    }

    /**
     * 检查用户是否在线
     * @param users
     */
    public void checkUserOnline(String... users){
        ArrayList<String> offlineList = new ArrayList();
        for (String user: users) {
            if(!clients.containsKey(user)){
                offlineList.add(user);
                log.debug("用户"+user+"已下线");
            }
        }
        if(offlineList.size() != 0){
            throw new MyException("用户已下线",Constant.RETMSG_FAIL, offlineList);
        }
    }
}
