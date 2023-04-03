package id.co.mii.serverapp.security.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class EmailConfig {

    @Bean
    public SpringTemplateEngine StemplE() {
        SpringTemplateEngine tmplE = new SpringTemplateEngine();
        tmplE.addTemplateResolver(htmlTmplResolver());
        return tmplE;
    }

    @Bean
    public SpringResourceTemplateResolver htmlTmplResolver() {
        SpringResourceTemplateResolver tmplRslvr = new SpringResourceTemplateResolver();
        tmplRslvr.setPrefix("classpath:/templates/");
        tmplRslvr.setSuffix(".html");
        tmplRslvr.setTemplateMode(TemplateMode.HTML);
        tmplRslvr.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return tmplRslvr;
    }
}
