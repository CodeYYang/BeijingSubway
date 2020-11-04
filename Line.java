package SubwayResult;

import java.util.ArrayList;

public class Line {
    String LineName;/*记录当前线路名*/
    ArrayList<String> Station = new ArrayList<>();/*记录该线路中所有的站点名*/

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public ArrayList<String> getStation() {
        return Station;
    }

    public void setStation(ArrayList<String> station) {
        Station = station;
    }
}
