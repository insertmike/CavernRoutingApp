package com.napier;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CavernGraph {
    final int connectedCavern = 1;
    private ArrayList<Cavern> caverns = new ArrayList<>();

    CavernGraph(String cavernFilePath){
        this.processCavernFile(cavernFilePath);
    }

    public ArrayList<Cavern> getCaverns() {
        return caverns;
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        return Files.readString(Paths.get(path), encoding);
    }

    private int getClosestCavernToTarget(List<Cavern> unvisited){
        double minDistance = Double.MAX_VALUE;
        int unvisitedIndex = -1;
        int i = 0;
        for (Cavern c:
                unvisited) {
            double res = c.gScore + c.hScore;
            if (res < minDistance){
                minDistance = res;
                unvisitedIndex = i;
            }
            i++;
        }
        return unvisitedIndex;
    }

    public String getRouteStringBetweenCaverns(Cavern cav1, Cavern cav2){
        List<Cavern> unvisitedCaverns = new ArrayList<>();
        unvisitedCaverns.add(cav1);
        unvisitedCaverns.get(0).gScore = 0;
        cav1.calcH(cav2.x, cav2.y);
        // Starting exploration from first cavern
        int curr = cav1.id;
        // Iterate until target is reached or no route is found
        while (unvisitedCaverns.size() > 0 && curr != cav2.id)
        {
            curr = getClosestCavernToTarget(unvisitedCaverns);
            for (int connectedID :
                    caverns.get(unvisitedCaverns.get(curr).id).connections) {
                if (!caverns.get(connectedID).visited) {
                    caverns.get(connectedID).calcG(caverns.get(unvisitedCaverns.get(curr).id));
                    caverns.get(connectedID).calcH(caverns.get(caverns.size()-1).x, caverns.get(caverns.size()-1).y);
                    if (!caverns.get(connectedID).isUnvisited) {
                        unvisitedCaverns.add(caverns.get(connectedID));
                        unvisitedCaverns.get(unvisitedCaverns.size() - 1).isUnvisited = true;
                    }
                }
            }
            unvisitedCaverns.get(curr).visited = true;
            unvisitedCaverns.get(curr).isUnvisited = false;
            unvisitedCaverns.remove(curr);
        }

        String res = "";

        if (cav2.prevCavern != -1)
        {
            int cavIndex  = cav2.id;
            while (cavIndex != -1) {
                res = " " + res;
                res = Integer.toString(cavIndex + 1) + res;
                cavIndex = caverns.get(cavIndex).prevCavern;
            }
        }
        else
            res = "0";
        //System.out.println(cav2.gScore);
    return res;
    }

    private void loadCaverns(String[] caverns){

        for (int i = 1; i <= Integer.parseInt(caverns[0]) * 2; i += 2) {
            this.caverns.add(new Cavern(Integer.parseInt(caverns[i]), Integer.parseInt(caverns[i + 1])));
        }
    }

    private void processCavernFile(String filePath) {

        String cavernsFile = null;

        try {
            cavernsFile = readFile(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] caverns = cavernsFile.split(",");

        // Load caverns
        this.loadCaverns(caverns);

        // Add connections
        int cavernsLen = Integer.parseInt(caverns[0]);

        // Add connection between caverns
        int matrix = cavernsLen * 2 + 1; //Keeps track of the extracted values in extractedList

            for (int i = 0; i < cavernsLen; i++)
            {
                for (int j = 0; j < cavernsLen; j++)
                {
                    if (Integer.parseInt(caverns[matrix]) == connectedCavern)
                        this.caverns.get(j).connections.add(i);
                    matrix++;
                }
            }

    }
}
