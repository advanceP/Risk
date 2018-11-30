package risk.controller;

import risk.model.Continent;
import risk.model.Node;
import risk.model.Graph;

import java.io.*;
import java.util.*;

/**
 *
 */
public class FileController {

    /**
     * @param filePath filepath of this txt file
     * @param content  information of the map and user
     * @deprecated : write and save as a txt file
     * @return: flag whether write and save a file successfully
     * @author: Yiying Liu
     */

    public static boolean writeFile(String filePath, String content) {
        boolean flag = false;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
            bw.close();
            flag = true;
        } catch (IOException e) {
            System.out.print("fail to write a file !");
        }
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        long length = 0;
        try {
            length = f.length() - 1;
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        byte b = 0;
        do {
            length -= 1;
            try {
                f.seek(length);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                b = f.readByte();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (b != 10);
        try {
            f.setLength(length + 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            f.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * @param graph the map wanted to save
     *  convert map information to StringBuffer type
     * @return: mapInfo     the result of conversion
     * @author: Yiying Liu
     */

    public StringBuffer getMapInfo(Graph graph) {
        StringBuffer mapInfo = new StringBuffer();
        List<Node> nodeList = graph.getGraphNodes();
        List<Continent> continentList = graph.getContinents();

        StringBuffer mapDetail = new StringBuffer();
        mapDetail.append("[Map]" + "\r\n");
        mapDetail.append("author=player" + "\r\n");
        mapDetail.append("image=null\r\n");
        mapDetail.append("wrap=no\r\n");
        mapDetail.append("scroll=horizontal\r\n\r\n");

        StringBuffer continentsInfo = new StringBuffer();
        continentsInfo.append("[Continents]" + "\r\n");
        StringBuffer nodesInfo = new StringBuffer();
        nodesInfo.append("[Territories]" + "\r\n");

        for (Node node : nodeList) {

            String adjacencyList = node.getAdjacencyList().toString();
            adjacencyList = adjacencyList.substring(1, adjacencyList.length() - 1).replace(" ", "");
            String continentName = node.getContinent().getName();
            String nodeDetail = node.getName() + "," + node.getX() + "," + node.getY() + ","
                    + continentName + "," + adjacencyList + "\r\n";
            nodesInfo.append(nodeDetail);

        }

        for (int i = 0; i < continentList.size(); i++) {
            Continent continent = continentList.get(i);
            String continentDetail = continent.getName() + "=" + continent.getAwardUnits() + "\r\n";
            continentsInfo.append(continentDetail);
        }
        mapInfo.append(mapDetail);
        mapInfo.append(continentsInfo.append("\r\n"));
        mapInfo.append(nodesInfo.append("\r\n"));
        return mapInfo;
    }

    /**
     * @param filePath the Path of map file
     *  to verify wheather the map is correct
     * @return: true : map is correct      false : map is incorrect
     * @author: Yiying Liu
     */

    public static boolean verifyMapFile(String filePath) {

        try {
            Map<String, Integer> continentList = new HashMap<>();
            Map<String, String> nodeList = new HashMap<>();
            InputStream is = new FileInputStream(filePath);
            String line;
            boolean contFlag = false;
            boolean nodeFlag = false;
            boolean notNode = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();

            Set<String> continentSet = new HashSet<>();
            Set<String> nodeSet = new HashSet<>();
            while (line != null) {
                if (line.equals("\r\n") || line.equals("")) {
                    line = reader.readLine();
                    continue;
                }
                if (line.equals("[Continents]")) {
                    contFlag = true;
                    notNode = true;
                    line = reader.readLine();
                }
                if (line.equals("[Territories]")) {
                    contFlag = false;
                    nodeFlag = true;
                    line = reader.readLine();
                }
                if (contFlag) {
                    String continent[] = line.split("=");
                    String temp = continent[0].trim().toLowerCase();
                    if (!continentSet.add(temp)){
                        return false;
                    }
                    if (continent.length > 2) {
                        return false;
                    }
                    Integer num = Integer.parseInt(continent[1]);
                    continentList.put(continent[0].replace(" ", ""), 0);
                }

                if (nodeFlag) {
                    String node[] = line.split(",");
                    String temp = node[0].trim().toLowerCase();
                    if (!nodeSet.add(temp)){
                        return false;
                    }
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
                Integer x = Integer.parseInt(nodeDetail[0].replace(" ", ""));
                Integer y = Integer.parseInt(nodeDetail[1].replace(" ", ""));

                if (!continentList.containsKey(continentName.replace(" ", ""))) {
                    return false;
                } else {
                    continentList.put(continentName.replace(" ", ""), continentList.
                                    get(continentName.replace(" ", "")) + 1);
                }

            }
            int sum = 0;
            Set<String> keySetList = continentList.keySet();
            for (String keySet : keySetList) {
                sum += continentList.get(keySet);
            }
            if (sum != nodeList.size()) {
                return false;
            }
            if (notNode == false || nodeFlag == false)
                return false;

        } catch (Exception e) {
            return false;
        }

        return true;
    }

}