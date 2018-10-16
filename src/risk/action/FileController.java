package risk.action;

import risk.entities.Continent;
import risk.entities.Graph;
import risk.entities.Node;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileController {

    /**
     * @Description: write and save as a txt file
     * @param filePath filepath of this txt file
     * @param content information of the map and user
     * @return: flag     whether write and save a file successfully
     * @Author: Yiying Liu
     * @Date: 2018-10-16
     */
    public static boolean writeFile(String filePath, String content) {
        boolean flag = false;
        File file = new File(filePath);
        try{
            if (!file.exists()){
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
            bw.close();
            flag = true;
        }catch (IOException e){
            System.out.print("fail to write a file !");
        }
        return flag;
    }

    /**
     * @Description: convert map information to StringBuffer type
     * @param graph     the map wanted to save
     * @return: mapInfo     the result of conversion
     * @Author: Yiying Liu
     * @Date: 2018-10-16
     */
    public StringBuffer getMapInfo(Graph graph){
        StringBuffer mapInfo = new StringBuffer();
        List<Node> nodeList = graph.getGraphNodes();
        List<Continent> continentList = graph.getContinents();

        StringBuffer continentsInfo = new StringBuffer();
        continentsInfo.append("[Continents]" + "\r\n");
        StringBuffer nodesInfo = new StringBuffer();
        nodesInfo.append("[Territories]" + "\r\n");

        for (Node node : nodeList){

            String adjacencyList = node.getAdjacencyList().toString();
            adjacencyList = adjacencyList.substring(1,adjacencyList.length() - 1).replace(" ","");
            String continentName = node.getContinent().getName();
            String nodeDetail = node.getName() + "," + node.getX() + "," + node.getY() + ","
                                    + continentName + "," + adjacencyList + "\r\n";
            nodesInfo.append(nodeDetail);

        }

        for (int i = 0; i < continentList.size(); i++){
            Continent continent = continentList.get(i);
            String continentDetail = continent.getName() + "=" + continent.getAwardUnits() + "\r\n";
            continentsInfo.append(continentDetail);
        }
        mapInfo.append(continentsInfo.append("\r\n"));
        mapInfo.append(nodesInfo.append("\r\n"));
        return mapInfo;
    }
    /**
     * @Description: to verify wheather the map is correct
     * @param filePath the Path of map file
     * @return:  true : map is correct      false : map is incorrect
     * @Author: Yiying Liu
     * @Date: 2018-10-16
     */
    public static boolean verifyMapFile(String filePath){

        try{
            Map<String, Integer> continentList = new HashMap<>();
            Map<String, String> nodeList = new HashMap<>();
            InputStream is = new FileInputStream(filePath);
            String line;
            boolean contFlag = false;
            boolean nodeFlag = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
            while (line != null){
                if (line.equals("\r\n") || line.equals("")){
                    line = reader.readLine();
                    continue;
                }
                if (line.equals("[Continents]")){
                    contFlag = true;
                    line = reader.readLine();
                }
                if (line.equals("[Territories]")){
                    contFlag = false;
                    nodeFlag = true;
                    line = reader.readLine();
                }
                if (contFlag){
                    String continent[] = line.split("=");
                    continentList.put(continent[0].replace(" ",""), 0);
                }

                if (nodeFlag){
                    String node[] = line.split(",");
                    int index = line.indexOf(',');
                    nodeList.put(node[0], line.substring(index + 1));
                }
                line = reader.readLine();
            }
            reader.close();
            is.close();

            Set<String> keys = nodeList.keySet();
            for (String key : keys) {
                String[] nodeDetail = nodeList.get(key).split(",");
                String continentName = nodeDetail[2];
                // make sure x and y are Integer
                Integer x = Integer.parseInt(nodeDetail[0].replace(" ",""));
                Integer y = Integer.parseInt(nodeDetail[1].replace(" ",""));

                if (!continentList.containsKey(continentName.replace(" ",""))){
                    return false;
                } else{
                   continentList.put(continentName.replace(" ",""), continentList.get(continentName.replace(" ","")) + 1);
                }

            }
            int sum = 0;
            Set<String> keySetList = continentList.keySet();
            for (String keySet: keySetList) {
                sum += continentList.get(keySet);
            }
            if (sum != nodeList.size()){
                return false;
            }

        }catch (Exception e){
            return false;
        }

        return true;
    }
/*    public static void main(String arg[]){
        Graph graph = Graph.getGraphInstance();
        FileController exportFile = new FileController();
        List<Continent> continentList = new ArrayList<>();
        Continent c1 = new Continent("aaa", 2);
        Continent c2 = new Continent("bbb", 1);
        continentList.add(c1);
        continentList.add(c2);
        List<Node> nodeList = new ArrayList<>();
        List<String> str1 = new ArrayList<>();
        str1.add("a1");
        str1.add("b1");
        List<String> str2 = new ArrayList<>();
        str2.add("b1");
        str2.add("a");
        List<String>str3 = new ArrayList<>();
        str3.add("a");
        str3.add("a1");
        Node node1 = new Node("a", 300, 400,c1, str1);
        Node node2 = new Node("a1", 304,405,c1,str2);
        Node node3 = new Node("b1", 400, 450, c2, str3);
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        graph.setContinents(continentList);
        graph.setGraphNodes(nodeList);
        String map = exportFile.getMapInfo(graph).toString();
        writeFile("D:/node.map", map);
        System.out.print("finish");
    }
    */
    public static void main(String arg[]){
        System.out.print(verifyMapFile("D:/Montreal.map"));
    }
}