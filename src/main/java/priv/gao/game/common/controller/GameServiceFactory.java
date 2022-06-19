package priv.gao.game.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import priv.gao.game.common.AllGameInfo;
import priv.gao.game.common.service.GameCommandService;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/18 15:05
 */
@Component
public class GameServiceFactory {

    private HashMap<Integer,GameCommandService> serviceMap = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 根据房间游戏类型获取对应的service
     * @param gameType
     * @return
     */
    public GameCommandService getGameService(int gameType, boolean cache) {
        if(cache){
            return this.getGameService(gameType);
        }
        AllGameInfo allGameInfo = Arrays.stream(AllGameInfo.values())
                .filter(gameInfoEnum -> gameInfoEnum.getGameType() == gameType)
                .findFirst().orElse(null);
        return this.getGameService(allGameInfo,false);
    }

    /**
     * 根据游戏枚举获取对应的service
     * @param allGameInfo
     * @return
     */
    public GameCommandService getGameService(AllGameInfo allGameInfo, boolean cache){
        if(cache){
            int gameType = allGameInfo.getGameType();
            return this.getGameService(gameType);
        }
        String reference = allGameInfo.getReference();
        String referenceName = allGameInfo.getReferenceName();
        Class<?> aClass = null;
        try {
            aClass = Class.forName(reference);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        GameCommandService gameCommandService = (GameCommandService) applicationContext.getBean(referenceName, aClass);
        serviceMap.put(allGameInfo.getGameType(),gameCommandService);
        return gameCommandService;
    }


    /**
     * 从本地缓存中获取对应的service
     * @param gameType
     * @return
     */
    public GameCommandService getGameService(int gameType){
        GameCommandService gameCommandService = serviceMap.get(gameType);
        if(gameCommandService == null){
            return this.getGameService(gameType,false);
        }
        return gameCommandService;
    }
}
