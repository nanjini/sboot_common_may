import com.sboot.backend.common.core.encrypt.EncryptConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan("com.sboot.backend.common.**")
@PropertySources({
        @PropertySource("classpath:application-${spring.profiles.active}.properties"),
        @PropertySource("classpath:common-core-application-${spring.profiles.active}.properties")
})
@MapperScan(value = "com.sboot.backend.common.business.mapper")
public class CommonApiApplication {

    private static final Logger logger = LogManager.getLogger(CommonApiApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(CommonApiApplication.class, args);

    }
}