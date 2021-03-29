//package com.echo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnection;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisHash;
//
//import java.lang.reflect.Method;
//
//
//@Configuration
//@EnableCaching  //注解驱动的缓存管理功能。自spring版本3.1起加入了该注解。如果你使用了这个注解，那么你就不需要在XML文件中配置cache manager了。
//                //当你在配置类(@Configuration)上使用@EnableCaching注解时，会触发一个post processor，这会扫描每一个spring bean，查看是否已经存在注解对应的缓存。如果找到了，就会自动创建一个代理拦截方法调用，使用缓存的bean执行处理。
//@RefreshScope
//public class RedisConfig extends CachingConfigurerSupport {
////    @Value("${spring.redis.host}")
////    private String host;
////    @Value("${spring.redis.port}")
////    private Integer port;
////    @Value("${spring.redis.timeout}")
////    private Integer timeout;
////    @Value("${spring.redis.pool.max-active}")
////    private Integer maxActive;
////    @Value("${spring.redis.pool.max-wait}")
////    private Integer maxWait;
////    @Value("${spring.redis.pool.max-idle}")
////    private Integer maxIdle;
////    @Value("${spring.redis.pool.min-idle}")
////    private Integer minIdle;
////
////    @RefreshScope
////    @Bean
////    public KeyGenerator wiselyKeyGenerator(){
////        return new KeyGenerator(){
////            @Override
////            public Object generate(Object target, Method method, Object... objects) {
////                StringBuilder stringBuilder = new StringBuilder();
////                stringBuilder.append(target.getClass().getName());
////                stringBuilder.append(method.getName());
////                for(Object obj : objects){
////                    stringBuilder.append(obj.toString());
////                }
////                return stringBuilder.toString();
////            }
////        };
////    }
////    @Bean
////    @RefreshScope
////    public JedisConnectionFactory redisConnectionFactory(){
////        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
////        jedisConnectionFactory.setHostName(host);
////        jedisConnectionFactory.setPort(port);
////        jedisConnectionFactory.setTimeout(timeout);
////        return jedisConnectionFactory;
////    }
//}
