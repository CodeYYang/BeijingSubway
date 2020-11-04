# BeijingSubway
# 北京地铁最短路径 
  ### 一、项目背景和要求       
  北京的地铁交通网络已经基本成型，建成的地铁线十多条，站点上百个，现需建立一个换乘指南打印系统，通过输入起点和终点站，打印出地铁换乘指南，指南内容包括起点站、换乘站、终点站。 
  ### 二、功能介绍       
  * 地铁指定线路的查看       
  * 换乘指南查询 ### 三、开发平台       
  * 开发语言 Java1.8       
  * 集成开发环境：IDEA 
  ### 四、实现算法       
  * BFS深度优先算法 
  ### 五、类职责划分       
  ***Station 类 存放站点信息**       
  `public class Station {             
  private String StationName;/*记录站点名*/             
  private boolean Visited;/*是否被遍历过*/             
  private String PreStation;/*便于搜索是返回和记录*/             
  List&lt;String> StationLine = new ArrayList&lt;>();/*该站点存在的所有线路信息*/             
  List&lt;Station> NearStation = new ArrayList&lt;>();/*该站点存在的所有邻近站点信息*/
  }`        
  ***Line 类 存放线路信息**       
  `public class Line {
  String LineName;/*记录当前线路名*/
  ArrayList&lt;String> Station = new ArrayList&lt;>();/*记录该线路中所有的站点名*/
  }`        
  ***Main** 类 实现程序运转的主要功能
  `public class Main {
  static ArrayList&lt;Line> LineList = new ArrayList&lt;>();//存放所有线路的列表
  static ArrayList&lt;Station> StationList = new ArrayList&lt;>();//存放线路站点的列表
  static HashMap&lt;String, Station> stationHashMap = new HashMap&lt;>();//存放对应站点的Hash 
  /*对文件进行读入和存储操作*/
  public static void SubwayMessage(String pathname){};
  /*输出线路信息*/
  public static void PrintMessage(String StartStation, String EndStation){};
  }`
