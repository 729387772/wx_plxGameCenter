package priv.gao.common.helper;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class RedisUtils {

    public static Jedis getJedis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("8.131.93.135");
        // 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
         jedis.auth("663799");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        return jedis;
    }

    public static RedisTemplate getRedisTemplate(){
        JedisConnectionFactory conn = new JedisConnectionFactory();
        conn.setDatabase(0);
        conn.setHostName("8.131.93.135");
        conn.setPort(6379);
        conn.setPassword("663799");
        conn.afterPropertiesSet();
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(conn);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    public static boolean tryLockWithSet(RedisTemplate<String,String> redisTemplate,String key, String UniqueId, int seconds) {
        redisTemplate.opsForValue().set(key, UniqueId,  seconds, TimeUnit.SECONDS);
        return true;
    }
}
