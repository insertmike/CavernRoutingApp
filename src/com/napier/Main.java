package com.napier;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        String inputFile = args[0] + ".cav";
        CavernGraph graph = new CavernGraph(inputFile);
        Cavern startCavern = graph.getCaverns().get(0);
        Cavern targetCavern = graph.getCaverns().get(graph.getCaverns().size() - 1);
        String route = graph.getRouteStringBetweenCaverns(startCavern, targetCavern);
        String outputFile = args[0] + ".csn";
        try{
            Files.writeString(Path.of(outputFile), route);
        } catch (Exception exception){
            System.out.println("Failed creating a file with Exception:");
            System.out.println(exception);
            System.out.println("DEBUG Mode. Route found: " + route);
        }
    }

}
