package risk.action;

import risk.entities.Continent;
import risk.entities.Graph;
import risk.entities.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ExportFile {

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

        Map<String, Integer> nodeInContNum = new HashMap<>();
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

            if (nodeInContNum.containsKey(continentName)){
                nodeInContNum.put(continentName, nodeInContNum.get(continentName) + 1);
            }
            else{
                nodeInContNum.put(continentName, 1);
            }
        }

        Set<String> keySet = nodeInContNum.keySet();
        for (String key : keySet) {
            String continentDetail = key + "=" + nodeInContNum.get(key) + "\r\n";
            continentsInfo.append(continentDetail);
        }
        mapInfo.append(continentsInfo.append("\r\n"));
        mapInfo.append(nodesInfo.append("\r\n"));
        return mapInfo;
    }

  /*  public static void main(String arg[]){
        Graph graph = Graph.getGraphInstance();
        ExportFile exportFile = new ExportFile();
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
    }*/
}