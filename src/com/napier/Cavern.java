package com.napier;

import java.util.ArrayList;
import java.util.List;

public class Cavern {
    public int id;
    public int x;
    public int y;
    public int prevCavern;
    List<Integer> connections = new ArrayList<Integer>();

    public double G;
    public double H;
    public boolean Visited;
    public boolean IsInUnvisited;
}
