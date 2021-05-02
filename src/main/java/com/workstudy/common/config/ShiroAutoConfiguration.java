package com.workstudy.common.config;

import com.workstudy.common.realm.UserRealm;
import com.workstudy.common.shiro.ShiroLoginFilter;
import com.workstudy.common.shiro.ShiroProperties;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author 刘其悦
 */
@Data
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {

    @Autowired
    private ShiroProperties shiroProperties;

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 声明凭证匹配器
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密方式
        credentialsMatcher.setHashAlgorithmName(shiroProperties.getHashAlgorithmName());
        //设置散列次数
        credentialsMatcher.setHashIterations(shiroProperties.getHashIterations());
        return credentialsMatcher;
    }

    /**
     * 声明userRealm
     *
     * @param hashedCredentialsMatcher
     * @return
     */
    @Bean
    public UserRealm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        //设置凭证匹配器
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    /**
     * 安全管理器
     *
     * @param defaultWebSessionManager
     * @param redisSession
     * @param userRealm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(DefaultWebSessionManager defaultWebSessionManager, SessionDAO redisSession, UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入realm
        securityManager.setRealm(userRealm);
        //设置sessionDao
        defaultWebSessionManager.setSessionDAO(redisSession);
        //设置defaultWebSessionManager
        securityManager.setSessionManager(defaultWebSessionManager);
        return securityManager;
    }

    @Bean
    public IRedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 连接池的最量 20，并发特别大时，连接池的数据可以最大增加20个
        jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        // 连接池的最大剩余量15个 ：并发不大，池里面的对象用不上，里面对象太多了。浪费空间
        jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        // 连接池初始就有10 个
        jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), 2000, redisProperties.getPassword());
        redisManager.setJedisPool(jedisPool);
        return redisManager;
    }

    /**
     * 使用Redis   来存储登录的信息
     * sessionDao 还需要设置给sessionManager
     */
    @Bean
    public SessionDAO redisSessionDAO(IRedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //操作那个redis
        redisSessionDAO.setRedisManager(redisManager);
        // 用户的登录信息保存多久  30分钟
        redisSessionDAO.setExpire(1800);
        return redisSessionDAO;
    }

    /**
     * 配置shiro的代理过滤器
     *
     * @return 注册器
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxyFilterRegistrationBean() {
        //创建注册器
        FilterRegistrationBean<DelegatingFilterProxy> registrationBean = new FilterRegistrationBean<>();
        //创建过滤器
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        //设置过滤器参数
        delegatingFilterProxy.setTargetBeanName(shiroProperties.getTargetBeanName());
        delegatingFilterProxy.setTargetFilterLifecycle(shiroProperties.getTargetFilterLifecycle());
        //将过滤器设置进注册器中
        registrationBean.setFilter(delegatingFilterProxy);
        //经过前端控制器就要经过这个过滤器
        Collection<String> servletNames = new ArrayList<>();
        servletNames.add(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        registrationBean.setServletNames(servletNames);
        return registrationBean;
    }

    /**
     * 创建ShiroFilterFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean(value = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注入安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //创建自定义filter
        Map<String, Filter> map = new HashMap<>(16);
        map.put("authc", new ShiroLoginFilter());
        shiroFilterFactoryBean.setFilters(map);

        //声明过滤器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>(16);
        //设置放行的路径
        String[] anonUrls = shiroProperties.getAnonUrls();
        if (anonUrls != null && anonUrls.length > 0) {
            for (String anonUrl : anonUrls) {
                filterChainDefinitionMap.put(anonUrl, "anon");
            }
        }

        //设置拦截的路径 --- 其它的都需要经过认证
        String[] authcUrls = shiroProperties.getAuthcUrls();
        if (authcUrls != null && authcUrls.length > 0) {
            for (String authcUrl : authcUrls) {
                filterChainDefinitionMap.put(authcUrl, "authc");
            }
        }

        //注入过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

}
