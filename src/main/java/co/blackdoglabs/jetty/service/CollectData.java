package co.blackdoglabs.jetty.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jcollins on 21/04/15.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class CollectData {

    // curl 'https://www.marinetraffic.com/map/getjson/sw_x:-12.00000/sw_y:47.00000/ne_x:8.00000/ne_y:53.00000/zoom:7/station:0' -H 'Cookie: SERVERID=www5; CAKEPHP=a9inm5takt654c43j4dm3j3kr5; MixPanelPages=1; vTo=1' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36' -H 'Accept: text/plain, */*; q=0.01' -H 'Referer: https://www.marinetraffic.com/' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' --compressed

    @Scheduled(fixedDelay = 5000)
    public void collectData() throws MalformedURLException {

        HttpURLConnection connection = null;

        URL url = new URL("https://www.marinetraffic.com/map/getjson/sw_x:-12.00000/sw_y:47.00000/ne_x:8.00000/ne_y:53.00000/zoom:7/station:0");


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Referer", "https://www.marinetraffic.com/");
        headers.set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
        headers.set("Requested-With", "XMLHttpRequest");
        headers.set("Accept-Encoding", "gzip, deflate, sdch");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        restTemplate.exchange(url, )


    }
}
