package com.napier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Cavern {
    static AtomicInteger nextId = new AtomicInteger(-1);
    List<Integer> connections = new ArrayList<>();
    public int id;
    public int x;
    public int y;
    public int prevCavern = -1;
    public double gScore = Double.MAX_VALUE;
    public double hScore = Double.MAX_VALUE;
    public boolean visited = false;
    public boolean isUnvisited = false;

    public Cavern(int x, int y)
    {
        this.id  = nextId.incrementAndGet();
        this.x = x;
        this.y = y;
    }

    public void calcG(Cavern prev)
    {
        double currDist = prev.gScore +  Math.sqrt(Math.pow(x - prev.x, 2) + Math.pow(y - prev.y, 2));

        boolean isBetterRoute = currDist < gScore;

        if (isBetterRoute)
        {
            this.gScore = currDist;
            this.prevCavern = prev.id;
        }
    }

    // Using Euclidean Distance Squared
    // Ref: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#euclidean-distance-squared
    public void calcH(int targetX, int targetY)
    {
        this.hScore = Math.sqrt(Math.pow(targetX - this.x, 2) + Math.pow(targetY - this.y, 2));
    }
}
