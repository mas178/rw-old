package works.rational;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Bean
  public FilterRegistrationBean getFilterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new CharacterEncodingFilter());
    return filterRegistrationBean;
  }

  @Override
  public Validator getValidator() {
    return localValidatorFactoryBean();
  }

  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
    bean.setBasename("classpath:/ValidationMessages");
    bean.setDefaultEncoding("UTF-8");
    return bean;
  }

  private static class CharacterEncodingFilter implements Filter {
    protected String encoding;

    public void init(FilterConfig filterConfig)
            throws ServletException {
      encoding = StandardCharsets.UTF_8.name();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      request.setCharacterEncoding(encoding);
      filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
      encoding = null;
    }
  }
}
