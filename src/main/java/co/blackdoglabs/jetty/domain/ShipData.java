package co.blackdoglabs.jetty.domain;

import org.json.JSONArray;
import org.json.JSONException;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jcollins on 22/04/15.
 */
public class ShipData {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String xpos;

    @Column(nullable = false)
    String ypos;

    @Column(nullable = false)
    String type;

    @Column(nullable = false)
    String heading;

    @Column(nullable = false)
    String speed;

    @Column(nullable = false)
    String flag;

    @Column(nullable = false)
    String mmsi;

    @Column(nullable = false)
    String length;

    @Column(nullable = false)
    String elapsed;

    @Column(nullable = false)
    String width;

    @Column(nullable = false)
    String lfore;

    @Column(nullable = false)
    String wleft;

    @Column(nullable = false)
    String course;

    @Column(nullable = false)
    String rotation;

    @Column(nullable = false)
    String destination;

    @Column(nullable = false)
    String shipId;


    public ShipData(JSONArray array) {
        try {

            this.shipId = array.get(0).toString();
            this.xpos = array.get(1).toString();
            this.ypos = array.get(2).toString();
            this.name = array.get(3).toString();
            this.type = array.get(4).toString();
            this.heading = array.get(5).toString();
            this.speed = array.get(6).toString();
            this.flag = array.get(7).toString();
            this.mmsi = array.get(8).toString();
            this.length = array.get(9).toString();
            this.elapsed = array.get(10).toString();
            this.width = array.get(11).toString();
            this.lfore = array.get(12).toString();
            this.wleft= array.get(13).toString();
            this.course = array.get(14).toString();
            this.rotation = array.get(15).toString();
            this.destination = array.get(16).toString();


        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXpos() {
        return xpos;
    }

    public void setXpos(String xpos) {
        this.xpos = xpos;
    }

    public String getYpos() {
        return ypos;
    }

    public void setYpos(String ypos) {
        this.ypos = ypos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLfore() {
        return lfore;
    }

    public void setLfore(String lfore) {
        this.lfore = lfore;
    }

    public String getWleft() {
        return wleft;
    }

    public void setWleft(String wleft) {
        this.wleft = wleft;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ShipData{" +
                "name='" + name + '\'' +
                ", xpos='" + xpos + '\'' +
                ", ypos='" + ypos + '\'' +
                ", type='" + type + '\'' +
                ", heading='" + heading + '\'' +
                ", speed='" + speed + '\'' +
                ", flag='" + flag + '\'' +
                ", mmsi='" + mmsi + '\'' +
                ", length='" + length + '\'' +
                ", elapsed='" + elapsed + '\'' +
                ", width='" + width + '\'' +
                ", lfore='" + lfore + '\'' +
                ", wleft='" + wleft + '\'' +
                ", course='" + course + '\'' +
                ", rotation='" + rotation + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
