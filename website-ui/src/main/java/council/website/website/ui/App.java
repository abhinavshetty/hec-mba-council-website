package council.website.website.ui;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import council.website.starter.BaseStarter;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "council.website")
@EnableAutoConfiguration
public class App {
	public static void main(String[] args) {
		BaseStarter starter = new BaseStarter();
		starter.init(App.class, args);
	}
}
