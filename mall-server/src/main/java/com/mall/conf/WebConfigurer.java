package com.mall.conf;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableCaching 
public class WebConfigurer extends WebMvcConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    @Override  
    public void addViewControllers(ViewControllerRegistry registry) {  
        registry.addViewController("/").setViewName("redirect:/mall");  
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);  
        super.addViewControllers(registry);  
    }  
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addInterceptor(new InitializeConnection())
//        .addPathPatterns("/**");
        registry.addInterceptor(new InterceptorConfig())
                .addPathPatterns("/mall/order/**");
        
        super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @Bean
   public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor  threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(10);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(15);
        //队列最大长度
        threadPoolTaskExecutor.setQueueCapacity(20);
        //线程池维护线程所允许的空闲时间
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        //主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return  threadPoolTaskExecutor;
    }
    
    
   
}
