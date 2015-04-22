package co.blackdoglabs.jetty.domain;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.stream.IntStream;

/**
 * Created by jcollins on 22/04/15.
 */
public class ShipData {
    String name;
    String x;
    String y;
    String speed;
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;

    public ShipData(JSONArray array) {
        try {
            this.speed = (String) array.get(0);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }
}
