package SubwayResult;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private String StationName;/*记录站点名*/
    private boolean Visited;/*是否被遍历过*/
    private String PreStation;/*便于搜索是返回和记录*/
    List<String> StationLine = new ArrayList<>();/*该站点存在的所有线路信息*/
    List<Station> NearStation = new ArrayList<>();/*该站点存在的所有邻近站点信息*/

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setVisited(boolean visited) {
        Visited = visited;
    }

    public String getPreStation() {
        return PreStation;
    }

    public void setPreStation(String preStation) {
        PreStation = preStation;
    }

    public List<String> getStationLine() {
        return StationLine;
    }

    public void setStationLine(List<String> stationLine) {
        StationLine = stationLine;
    }

    public List<Station> getNearStation() {
        return NearStation;
    }

    public void setNearStation(List<Station> nearStation) {
        NearStation = nearStation;
    }
}
