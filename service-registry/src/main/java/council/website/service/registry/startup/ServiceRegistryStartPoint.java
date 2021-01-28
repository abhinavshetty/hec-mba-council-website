package council.website.service.registry.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryStartPoint 
{
    public static void main( String[] args )
    {
        SpringApplication.run(ServiceRegistryStartPoint.class);
    }
}
