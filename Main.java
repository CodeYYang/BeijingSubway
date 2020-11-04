package SubwayResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<Line> LineList = new ArrayList<>();//存放所有线路的列表
    static ArrayList<Station> StationList = new ArrayList<>();//存放线路站点的列表
    static HashMap<String, Station> stationHashMap = new HashMap<>();//存放对应站点的Hash
    /*对文件进行读入和存储操作*/
    public static void SubwayMessage(String pathname){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(pathname)));
            String NowLine = null;
            while ((NowLine=bufferedReader.readLine()) != null){
                Line line = new Line();
                String[] StationInformation = NowLine.split(" ");
                line.setLineName(StationInformation[0]);
                for (int i = 1; i < StationInformation.length-1; i++){
                    Station NowStation = new Station();
                    Station NextStation = new Station();
                    if(stationHashMap.containsKey(StationInformation[i])){
                        /*如果hashmap中已经存在该站点信息，因为需要修改所以将它放入NowStation中*/
                       NowStation = stationHashMap.get(StationInformation[i]);
                       stationHashMap.remove(StationInformation[i]);
                    }
                    else{
                        NowStation.setStationName(StationInformation[i]);
                        NowStation.setVisited(false);
                    }
                    if(stationHashMap.containsKey(StationInformation[i+1])){
                        /*如果hashmap中已经存在该站点信息，因为需要修改所以将它放入NowStation中*/
                        NextStation = stationHashMap.get(StationInformation[i+1]);
                        stationHashMap.remove(StationInformation[i+1]);
                    }
                    else{
                        NextStation.setStationName(StationInformation[i+1]);
                        NextStation.setVisited(false);
                    }
                    /*如果站点不包含当前线路，将线路添加至站点*/
                    if(!NowStation.getStationLine().contains(line.LineName)){
                        NowStation.getStationLine().add(line.LineName);
                    }
                    if(!NextStation.getStationLine().contains(line.LineName)){
                        NextStation.getStationLine().add(line.LineName);
                    }
                    /*如果站点不含下一站，将相邻站添加至NextStation*/
                    if(!NowStation.getNearStation().contains(NextStation)){
                        NowStation.getNearStation().add(NextStation);
                    }
                    if(!NextStation.getNearStation().contains(NowStation)){
                        NextStation.getNearStation().add(NowStation);
                    }
                    NowStation.setPreStation(NowStation.getStationName());
                    NextStation.setPreStation(NextStation.getStationName());

                    stationHashMap.put(StationInformation[i], NowStation);
                    stationHashMap.put(StationInformation[i+1], NextStation);
                    /*将当前站点添加至线路中*/
                    if(!line.getStation().contains(NowStation.getStationName())){
                        line.getStation().add(NowStation.getStationName());
                    }
                    if(!line.getStation().contains(NextStation.getStationName())){
                        line.getStation().add(NextStation.getStationName());
                    }
                }
                LineList.add(line);
            }
            /*释放资源*/
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Read file error, Please try again!");
        }
    }
    /*输出线路信息*/
    public static void PrintMessage(String StartStation, String EndStation){
        List<String> list = new ArrayList<>();
        String NowStation = EndStation;
        String PreLine = "";
        while (!NowStation.equals(StartStation)){
            list.add(NowStation);
            NowStation = stationHashMap.get(NowStation).getPreStation();
        }
        Collections.reverse(list);
        System.out.println("当前路程共经过："+(list.size())+"站");
        System.out.print(StartStation);
        for (int i = 0; i < list.size(); i++){
            if(stationHashMap.get(list.get(i)).getStationLine().size()==1){
                /*如果当前站点只存在一条线路，显然不可能在此站换乘*/
                System.out.print("-->"+stationHashMap.get(list.get(i)).getStationName());
                PreLine = stationHashMap.get(list.get(i)).getStationLine().get(0);
            }
            else {
                /*如果该站点存在多条线路，并且下一站只有一条线路*/
                if (stationHashMap.get(list.get(i+1)).getStationLine().size()==1){
                    /*如果该站和前一站线路相同，直接输出
                    * 如果不同，则表明为换乘线，输出该站名，然后显示换乘线路信息*/
                    if(stationHashMap.get(list.get(i+1)).getStationLine().get(0).equals(PreLine)){
                        System.out.print("-->"+stationHashMap.get(list.get(i)).getStationName());
                    }
                    else{
                        System.out.println("-->"+stationHashMap.get(list.get(i)).getStationName());
                        System.out.println("换乘"+stationHashMap.get(list.get(i+1)).getStationLine().get(0));
                        PreLine = stationHashMap.get(list.get(i+1)).getStationLine().get(0);
                    }
                }
                else{
                    /*对于多线路站点，如果包含前一站的线路信息说明不需要换乘
                    * 如果不包含，遍历前后两站的线路信息，换乘线路一定存在于两站都共有的线路节点中*/
                    if (stationHashMap.get(list.get(i+1)).getStationLine().contains(PreLine)){
                        System.out.print("-->"+stationHashMap.get(list.get(i)).getStationName());
                    }
                    else{
                        boolean IsSame = false;
                        for (int t1 = 0; t1 < stationHashMap.get(list.get(i)).getStationLine().size(); t1++){
                            if (stationHashMap.get(list.get(i+1)).getStationLine().contains(stationHashMap.get(list.get(i)).getStationLine().get(t1))){
                                System.out.println("-->"+stationHashMap.get(list.get(i)).getStationName());
                                System.out.println("换乘"+stationHashMap.get(list.get(i)).getStationLine().get(t1));
                                PreLine = stationHashMap.get(list.get(i)).getStationLine().get(t1);
                                IsSame = true;
                                break;
                            }
                        }
                        if(IsSame){
                            System.out.print("-->"+stationHashMap.get(list.get(i)).getStationName());
                        }
                    }
                }
            }
        }
    }
    /*利用BFS算法进行最短路径遍历*/
    public static void SearchShortPath(String StartStation, String EndStation){
        /*初始化所有站点的遍历信息*/
        for (Map.Entry<String, Station> entry: stationHashMap.entrySet()){
            entry.getValue().setVisited(false);
        }
        Queue<String> queue = new LinkedList<>();
        queue.add(StartStation);
        while (!queue.isEmpty()){
            /*如果队列不为空，移除队列头部，将该站设置为遍历过*/
            String NowStation = queue.poll();
            stationHashMap.get(NowStation).setVisited(true);
            if (NowStation.equals(EndStation)){
                break;
            }
            /*对于该站点的所有相邻节点进行遍历，如果未被遍历，则PreStation设置为当前站，将该相邻站添加至队列中*/
            for (Station station:stationHashMap.get(NowStation).NearStation){
                if (stationHashMap.get(station.getStationName()).isVisited()==false){
                    stationHashMap.get(station.getStationName()).setPreStation(NowStation);
                    queue.add(station.getStationName());
                }
            }
        }
    }
    /*查找线路信息*/
    public static void SearchLine(String LineName){
        List<String> Line = new ArrayList<>();
        boolean IsSearch =false;
        for(Line line: LineList){
            if (line.getLineName().equals(LineName)){
                IsSearch = true;
                System.out.println("当前线路为"+LineName);
                for (int i = 0; i < line.getStation().size(); i++){
                    if (i == 0){
                        System.out.print(line.getStation().get(i));
                    }else{
                        System.out.print("-->"+line.getStation().get(i));
                    }
                }
                break;
            }
        }
        if(IsSearch == false){
            System.out.println("未找到该线路");
        }
    }
    public static void main(String[] args) {
        SubwayMessage("E:\\StudyofProject\\IDEAProjects\\Introduction to Software Engineering\\src\\SubwayResult\\Subway.txt");
        Scanner input = new Scanner(System.in);
        System.out.println("地铁线路菜单" );
        System.out.println("一、如需查询地铁线路信息，请按 1");
        System.out.println("二、如需查询出行最短路径，请按 2");
        String op = input.nextLine();
        if(op.equals("1")){
            System.out.println("请输入线路名");
            String LineName = input.nextLine();
            SearchLine(LineName);
        }
        if(op.equals("2")) {
            System.out.println("请输入起始站：");
            String start = input.nextLine();
            System.out.println("请输入终点站");
            String end = input.nextLine();
            boolean IsExisted = true;
            if (!stationHashMap.containsKey(start)) {
                IsExisted = false;
                System.out.println("不存在该起始站");

            }
            if (!stationHashMap.containsKey(end)) {
                IsExisted = false;
                System.out.println("不存在该终点站");
            }
            if (IsExisted) {
                SearchShortPath(start, end);
                PrintMessage(start, end);
            }
        }
    }

}
