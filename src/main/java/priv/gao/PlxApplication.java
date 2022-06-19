package priv.gao;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("priv.gao")
@SpringBootApplication
public class PlxApplication extends SpringBootServletInitializer {
    private final static Logger log = LoggerFactory.getLogger(PlxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlxApplication.class, args);
        log.debug("==============应用启动完成==============");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PlxApplication.class);
    }
}
