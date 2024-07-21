package com.stackroute.bookrecommendationservice;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.driver.MetricsAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.neo4j.ConfigBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.neo4j.cypherdsl.core.renderer.Configuration;

import java.util.Arrays;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.web.bind.annotation.*;


@EnableRabbit
@SpringBootApplication
//@EnableEurekaClient
public class BookRecommendationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRecommendationServiceApplication.class, args);
	}
	/* To Be Investigated For The Unnecessary Logs
	@Bean
	Configuration cypherDslConfiguration() {
		return Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
	} */

	/**
	 * Makes the Neo4j driver use Micrometer for metrics reporting.
	 */
	/*	@Bean
	ConfigBuilderCustomizer configBuilderCustomizer() {
		return configBuilder -> configBuilder.withMetricsAdapter(MetricsAdapter.MICROMETER);
	}*/

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				if (beanName.toLowerCase().contains("neo4j")) {
					//System.out.println(beanName);
				}
			}
		};
	}
}
