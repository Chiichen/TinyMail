package edu.scut.tinymail.config;

import edu.scut.tinymail.filter.LoginFilter;
import edu.scut.tinymail.handler.AuthenticationHandler;
import edu.scut.tinymail.service.RememberMeServices;
import edu.scut.tinymail.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author ChiChen
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 接口文档放行
     */
    public static final List<String> DOC_WHITE_LIST = List.of("/doc.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/index.html");
    /**
     * 测试接口放行
     */
    public static final List<String> TEST_WHITE_LIST = List.of("/test/**", "/api/mail/**");
    /**
     * 验证码放行
     */
    public static final List<String> VERIFY_CODE_WHITE_LIST = List.of("/api/verifyCode/**");

    /**
     * 注册放行
     */
    public static final List<String> REGISTER_WHITE_LIST = List.of("/api/user/register");

    /**
     * swagger文档放行
     */


    public static final List<String> SWAGGER_UI_LIST = List.of("/swagger-ui/index.html", "swagger-ui.html");

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 允许抛出用户不存在的异常
     *
     * @param myUserDetailsService myUserDetailsService
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsServiceImpl myUserDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setUserDetailsPasswordService(myUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    /**
     * 自定义RememberMe服务token持久化仓库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource datasource) {
        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(datasource);
        //第一次启动的时候建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   LoginFilter loginFilter,
                                                   AuthenticationHandler authenticationHandler,
                                                   RememberMeServices rememberMeServices
    ) throws Exception {
        //路径配置
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, DOC_WHITE_LIST.toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.GET, VERIFY_CODE_WHITE_LIST.toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.POST, TEST_WHITE_LIST.toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.POST, REGISTER_WHITE_LIST.toArray(new String[0])).permitAll()
                //.anyRequest().authenticated()
                .anyRequest().permitAll()


        ;
        //登陆
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        //配置自定义登陆流程后需要关闭 否则可以使用原有登陆方式
        //登出
        http.logout().logoutUrl("/api/user/logout").logoutSuccessHandler(authenticationHandler);
        //禁用 csrf和允许跨域
        http.csrf().disable();
        http.cors().disable();
        //csrf验证 存储到Cookie中
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//        ;
        //会话管理
        http.sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(authenticationHandler)
        //引入redis-session依赖后已不再需要手动配置 sessionRegistry
//                .sessionRegistry(new SpringSessionBackedSessionRegistry<>(new RedisIndexedSessionRepository(RedisConfig.createRedisTemplate())))
        //禁止后登陆挤下线
//               .maxSessionsPreventsLogin(true)
        ;
        //rememberMe
        http.rememberMe().rememberMeServices(rememberMeServices);
        // 权限不足时的处理
        http.exceptionHandling()
                .accessDeniedHandler(authenticationHandler)
                .authenticationEntryPoint(authenticationHandler)
        ;
        return http.build();
    }
}


