package ro.fasttrackit.proiect.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Component
public class EmailConfiguration {

    @Bean
    public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/mail-templates");
        configuration.setTemplateLoader(templateLoader);
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }
}
