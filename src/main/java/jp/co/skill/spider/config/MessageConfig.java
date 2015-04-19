package jp.co.skill.spider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Validation Message日本語文字化け対応。
 * ※１、３: MVCで任意のファイルを読み込ませるために必要な設定です。
 * ※２: カンマ区切りで複数ファイルを指定可能です。
 * @author yuuichi
 *
 */
@Configuration
public class MessageConfig extends WebMvcConfigurerAdapter {

	   @Bean
	    ReloadableResourceBundleMessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasenames("classpath:i18n/messages");	//（※２）
	        messageSource.setCacheSeconds(0);
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }

	    @Bean
	    public LocalValidatorFactoryBean validator() {
	        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
	        localValidatorFactoryBean.setValidationMessageSource(messageSource());
	        return localValidatorFactoryBean;
	    }

	    @Override
	    public Validator getValidator() {  //（※３）
	        return validator();
	    }

}
