# Largest Island Detection

This project is an experiment of mine to show how generative testing can be used to solve the algorithmic problem of detecting all coordinates on an island of black points in an m x n grid.

Grid.java contains the main logic for initializing a grid with black pairs and detecting an island.  This is Algorithms 101 and not very exciting.  

ProbabilisticIslandGenerator is the main driver for generative testing in this project.  It operates in a reverse fashion of island detection in that it selects a random starting x,y then probabilistically determines if a neighbor should be black or not.  ProbabilisticIslandGenerator takes 3 parameters:

- height of the grid
- width of the grid
- probability that a neighbor is black or not

Here's an example grid generated with height=20, width=20, and probability=0.5:

```
--------------------
--------------------
--------------------
--------------------
--------------------
--------------------
--------------------
--------------------
--------------------
------------------BB
------------------BB
----------BB-----BBB
--------BBBB--BBBBBB
------BBBBBBB---BBBB
------BBBBBBB-BBBBBB
------BBBBBBBBBBBBBB
----BBBBB--BBBBBBBBB
BBBBBB-BB----BBBBBB-
BBBBBBBBBB--BBBBBBBB
BBBBBBBB-B----BBBBBB
```