package co.blackdoglabs.jetty.service;


import co.blackdoglabs.jetty.domain.ShipData;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import scala.Tuple2;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by jcollins on 21/04/15.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class CollectData {

    // curl 'https://www.marinetraffic.com/map/getjson/sw_x:-12.00000/sw_y:47.00000/ne_x:8.00000/ne_y:53.00000/zoom:7/station:0' -H 'Cookie: SERVERID=www5; CAKEPHP=a9inm5takt654c43j4dm3j3kr5; MixPanelPages=1; vTo=1' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36' -H 'Accept: text/plain, */*; q=0.01' -H 'Referer: https://www.marinetraffic.com/' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' --compressed
    //curl 'https://www.marinetraffic.com/en/users/ajax_user_menu?_=1429698365828' -H 'Cookie: SERVERID=www5' -H 'Accept-Encoding: gzip,deflate,sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36' -H 'Accept: */*' -H 'Referer: https://www.marinetraffic.com/' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' -H 'Cache-Control: max-age=0' --compressed
    @Scheduled(fixedDelay = 5000)
    public <R> void collectData() throws IOException, JSONException {


        URLConnection connection = new URL("https://www.marinetraffic.com").openConnection();
        setHeaders(connection);
        List<String> cookies = connection.getHeaderFields().get("Set-Cookie");

        // Get cookie
        String cookie = cookies.stream().filter(p -> p.contains("SERVERID")).findFirst().get();

        Pattern pattern = Pattern.compile("([A-Z])\\w+=([a-zA-Z0-9999])+;");
        Matcher matcher = pattern.matcher(cookie);

        String server = "";
        while (matcher.find()) {
            server = matcher.group(0);
        }


        URLConnection cakePHP = new URL(String.format("https://www.marinetraffic.com/en/users/ajax_user_menu?_=%s", System.currentTimeMillis() / 1000L)).openConnection();
        cakePHP.setRequestProperty("Cookie", server);
        cakePHP.setRequestProperty("Host", "www.marinetraffic.com");
        cakePHP.setRequestProperty("Referer", "https://www.marinetraffic.com");
        setHeaders(cakePHP);
        cakePHP.connect();
        List<String> tokenCookieList = cakePHP.getHeaderFields().get("Set-Cookie");

        String tokenCookie = tokenCookieList.stream().filter(p -> p.contains("CAKE")).findFirst().get();

        Pattern csrfToken = Pattern.compile("([A-Z])\\w+=([a-zA-Z0-9])+;");
        Matcher csrfMatcher = csrfToken.matcher(tokenCookie);

        String token = "";
        while (csrfMatcher.find()) {
            token = csrfMatcher.group(0);
        }

        final String authToken = token;
        final String authServer = server;

        // Coordinate range from -90 to +90
        List<Integer> coordinateRange = IntStream.iterate(-90, n -> n - 20).limit(10).boxed().collect(Collectors.toList());

        // Create an 81 panel grid
        List<Tuple2> grid = coordinateRange.stream()
                .flatMap(x -> coordinateRange.stream().map(y -> new Tuple2(new Point(x, y + 20), new Point(x + 20, y)))
                        .filter(tuple -> ((Point) tuple._1()).getX() <= 90
                                && ((Point) tuple._1()).getY() <= 90
                                && ((Point) tuple._2()).getX() <= 90
                                && ((Point) tuple._2()).getY() <= 90)).collect(Collectors.toList());

        List<ShipData> r = grid.stream().flatMap(tuple -> getShipData((Point) tuple._1(), (Point) tuple._2(), authServer, authToken)).collect(Collectors.toSet());


    }

    // Pull data from grid (southwest to northest)
    private List<ShipData> getShipData(Point sw, Point ne, String server, String token) {

        String url = String.format("https://www.marinetraffic.com/map/getjson/sw_x:%s.00000/sw_y:%s.00000/ne_x:%s.00000/ne_y:%s.00000/zoom:8/station:0",
                sw.getX(), sw.getY(), ne.getX(), ne.getY());


        URLConnection jsonConnection = null;
        try {
            jsonConnection = new URL(url).openConnection();
            setHeaders(jsonConnection);
            jsonConnection.setRequestProperty("Cookie", String.format("%s %s  MixPanelPages=1; vTo=1", server, token));
            jsonConnection.setRequestProperty("Referer", "https://www.marinetraffic.com/");
            jsonConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
            jsonConnection.setRequestProperty("Requested-With", "XMLHttpRequest");

            GZIPInputStream gzipInputStream = new GZIPInputStream(jsonConnection.getInputStream());

            String json = IOUtils.toString(gzipInputStream);

            JSONArray jsonArray = new JSONArray(json);

            // Build from array
            List<ShipData> shipData = IntStream.range(0, jsonArray.length())
                    .filter(i -> getFromArray(jsonArray, i) instanceof JSONArray)
                    .mapToObj(i -> new ShipData(getFromArray(jsonArray, i)))
                    .filter(ship -> ship.getType().equals(8)) // Just get the LPG
                    .collect(Collectors.toList());

            return shipData;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Helper to avoid thrown exception in stream
    private JSONArray getFromArray(JSONArray jsonArray, int i) {
        try {
            return (JSONArray) jsonArray.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void setHeaders(URLConnection connection) {
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        connection.setRequestProperty("Accept-Language", "en-GB,en-US;q=0.8,en;q=0.6");
    }
}
