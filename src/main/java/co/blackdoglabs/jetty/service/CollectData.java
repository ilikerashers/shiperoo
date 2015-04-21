package co.blackdoglabs.jetty.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by jcollins on 21/04/15.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class CollectData {

    @Scheduled(fixedDelay = 5000)
    public void collectData() {
        System.out.println("hi");
    }
}
