package net.wash8.classbean;

import java.util.ArrayList;

/**
 * Created by ncb-user on 2014/12/31.
 */
public class SearchResult {
    private ArrayList<Stationaries> Stationaries;
    private String[] AvailableDistricts;

    public ArrayList<Stationaries> getStationaries() {
        return Stationaries;
    }

    public void setStationaries(ArrayList<Stationaries> stationaries) {
        Stationaries = stationaries;
    }

    public String[] getAvailableDistricts() {
        return AvailableDistricts;
    }

    public void setAvailableDistricts(String[] availableDistricts) {
        AvailableDistricts = availableDistricts;
    }
}
