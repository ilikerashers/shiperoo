package co.blackdoglabs.jetty.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by jcollins on 22/04/15.
 */

public class TrackingData {

    @JsonProperty
    ArrayList<ArrayList<String>> shipData;

    @JsonProperty
    String unknown;

    @JsonProperty
    String getUnknown2;

    public ArrayList<ArrayList<String>> getShipData() {
        return shipData;
    }

    public void setShipData(ArrayList<ArrayList<String>> shipData) {
        this.shipData = shipData;
    }

    public String getUnknown() {
        return unknown;
    }

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

    public String getGetUnknown2() {
        return getUnknown2;
    }

    public void setGetUnknown2(String getUnknown2) {
        this.getUnknown2 = getUnknown2;
    }
}
