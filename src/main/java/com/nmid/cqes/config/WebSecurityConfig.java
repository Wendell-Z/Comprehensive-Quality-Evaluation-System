package com.nmid.cqes.config;

/**
 * Created by yStar on 2018/2/23 16:36:16
 * 拦截器配置
 */
//@Configuration
/*public class WebSecurityConfig extends WebMvcConfigurationSupport {

    private String sessionKey;

    @Bean
    public SecurityInterceptor getSecurityInterceptor(){
        return  new SecurityInterceptor();
    }
    @Override
    public  void addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        //排除配置
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/logout");
        addInterceptor.excludePathPatterns("/index.html");
        //拦截配置
        addInterceptor.addPathPatterns("/stuList/**");
        addInterceptor.addPathPatterns("/stuInfo/**");
        addInterceptor.addPathPatterns("/stuPrizes/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws IOException {
            HttpSession session = request.getSession();

            //判断是否已有该用户登录的session
            if(session.getAttribute("stuId") !=null){
                return  true;
            }
            //跳转到登录页
            String url = "/cqes/index.html";
            response.sendRedirect(url);
            return false;
        }
    }

}

*/