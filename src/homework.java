import java.io.*;
import java.util.*;

public class homework {

    public static void main(String[] args) throws IOException {
        //declare essential utilities
        File output = new File("output.txt");
        File input = new File("input.txt");
        Scanner sc = new Scanner(input);
        FileWriter myWriter = new FileWriter("output.txt");
        int target_x = 0;
        int target_y = 0;
        //read the first line
        String type = sc.nextLine();
        //get the size info
        int width = sc.nextInt();
        int height = sc.nextInt();
        node[][] map = new node[width][height];
        //get the starting position
        int x = sc.nextInt();
        int y = sc.nextInt();
        //System.out.println(x_pos);
        //get the number of maximum height, set to negative
        int max_h = 0 - sc.nextInt();
        //get the number of targets
        int targetNum = sc.nextInt();
        int[][] target = new int[targetNum][2];
        for (int i = 0; i < targetNum; i++) {
            target_x = sc.nextInt();
            target_y = sc.nextInt();
            target[i][0] = target_x;
            target[i][1] = target_y;
        }
        //generate the array of map
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[j][i] = new node(null, j, i, sc.nextInt(), 0);
            }
        }
        sc.close();
        //break into different searching methods for several targets
        for (int q = 0; q < targetNum; q++) {
            int s_cost;
            target_x = target[q][0];
            target_y = target[q][1];

            if (type.equals("A*")) {
                List<node> open = new ArrayList<>();
                List<node> closed = new ArrayList<>();
                List<node> path = new ArrayList<>();
                int h_value = 0;
                //add the first node to the list
                open.add(map[x][y]);
                while (!open.isEmpty()) {
                    int pos = 0;
                    node minNode = open.get(0);
                    //get the node with the smallest f value
                    for (int i = 0; i < open.size(); i++) {
                        if (minNode.f > open.get(i).f) {
                            minNode = open.get(i);
                            pos = i;
                        }
                    }
                    //remove the smallest one from the list
                    open.remove(pos);
                    node current;
                    //check the border: RIGHT
                    if (minNode.x_pos < width - 1) {
                        //update the information of the current node: value and parent
                        current = map[minNode.x_pos + 1][minNode.y_pos];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (!closed.contains(current)) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: DOWNRIGHT
                    if (minNode.x_pos < width - 1 && minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos + 1][minNode.y_pos + 1];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (closed.contains(current) == false) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: UPRIGHT
                    if (minNode.x_pos < width - 1 && minNode.y_pos > 0) {
                        current = map[minNode.x_pos + 1][minNode.y_pos - 1];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (closed.contains(current) == false) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: LEFT
                    if (minNode.x_pos > 0) {
                        current = map[minNode.x_pos - 1][minNode.y_pos];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (closed.contains(current) == false) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: DOWNLEFT
                    if (minNode.x_pos > 0 && minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos - 1][minNode.y_pos + 1];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (!closed.contains(current)) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: UPLEFT
                    if (minNode.x_pos > 0 && minNode.y_pos > 0) {
                        current = map[minNode.x_pos - 1][minNode.y_pos - 1];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (!closed.contains(current)) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: DOWN
                    if (minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos][minNode.y_pos + 1];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (!closed.contains(current)) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    //check the border: UP
                    if (minNode.y_pos > 0) {
                        current = map[minNode.x_pos][minNode.y_pos - 1];
                        h_value = generateH(current.x_pos, current.y_pos, target_x, target_y);
                        if (!closed.contains(current)) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    current.parent = minNode;
                                    current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                    current.f = current.cost + h_value;
                                    //check if it's the destination
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        while (current.parent != null) {
                                            path.add(current);
                                            current = current.parent;
                                        }
                                        path.add(current);
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost + current.value + getHeightDiff(current.value, minNode.value) < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost + current.value + getHeightDiff(current.value, minNode.value);
                                current.f = current.cost + h_value;
                            }
                        }
                    }
                    if (!path.isEmpty()) {
                        closed.clear();
                        open.clear();
                        for (int j = path.size() - 1; j >= 0; j--) {
                            if (j == 0) {
                                myWriter.write(path.get(j).x_pos + "," + path.get(j).y_pos);
                            } else {
                                myWriter.write(path.get(j).x_pos + "," + path.get(j).y_pos + " ");
                            }
                        }
                        if (q < targetNum - 1) {
                            target_x = target[q + 1][0];
                            target_y = target[q + 1][1];
                            map[x][y].cost = 0;
                            open.add(map[x][y]);
                            path.clear();
                            myWriter.write("\n");
                        }
                        q++;
                    } else {
                        closed.add(minNode);
                    }
                }
                //write the output
                if (path.isEmpty()) {
                    myWriter.write("FAIL");
                    if (q < targetNum - 1) {
                        myWriter.write("\n");
                    }
                }
            } else if (type.equals("BFS")) {
                //declare data structures needed
                List<node> open = new ArrayList<>();
                List<node> closed = new ArrayList<>();
                List<node> path = new ArrayList<>();
                //add the first node to the list
                open.add(map[x][y]);
                s_cost = Integer.MAX_VALUE;
                while (open.isEmpty() == false) {
                    node minNode = open.get(0);
                    open.remove(minNode);
                    node current;
                    //check the border: RIGHT
                    if (minNode.x_pos < width - 1) {
                        //update the information of the current node: value and parent
                        current = map[minNode.x_pos + 1][minNode.y_pos];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: DOWNRIGHT
                    if (minNode.x_pos < width - 1 && minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos + 1][minNode.y_pos + 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: UPRIGHT
                    if (minNode.x_pos < width - 1 && minNode.y_pos > 0) {
                        current = map[minNode.x_pos + 1][minNode.y_pos - 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: LEFT
                    if (minNode.x_pos > 0) {
                        current = map[minNode.x_pos - 1][minNode.y_pos];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: DOWNLEFT
                    if (minNode.x_pos > 0 && minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos - 1][minNode.y_pos + 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: UPLEFT
                    if (minNode.x_pos > 0 && minNode.y_pos > 0) {
                        current = map[minNode.x_pos - 1][minNode.y_pos - 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: DOWN
                    if (minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos][minNode.y_pos + 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    //check the border: UP
                    if (minNode.y_pos > 0) {
                        current = map[minNode.x_pos][minNode.y_pos - 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 1 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (1 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 1 + minNode.cost;
                            }
                        }
                    }
                    closed.add(minNode);
                }
                //write the output
                if (path.isEmpty() == false) {
                    for (int j = path.size() - 1; j >= 0; j--) {
                        if (j == 0) {
                            myWriter.write(path.get(j).x_pos + "," + path.get(j).y_pos);
                        } else {
                            myWriter.write(path.get(j).x_pos + "," + path.get(j).y_pos + " ");
                        }
                    }
                    if (q != targetNum - 1) {
                        myWriter.write("\n");
                    }
                } else {
                    myWriter.write("FAIL");
                    if (q != targetNum - 1) {
                        myWriter.write("\n");
                    }
                }
            } else if (type.equals("UCS")) {
                s_cost = Integer.MAX_VALUE;
                List<node> open = new ArrayList<>();
                List<node> closed = new ArrayList<>();
                List<node> path = new ArrayList<>();
                //add the first node to the list
                open.add(map[x][y]);
                while (!open.isEmpty()) {
                    int pos = 0;
                    node minNode = open.get(0);
                    //get the node with the smallest f value
                    for (int i = 0; i < open.size(); i++) {
                        if (minNode.cost > open.get(i).cost) {
                            minNode = open.get(i);
                            pos = i;
                        }
                    }
                    //remove the smallest one from the list
                    open.remove(pos);
                    node current;
                    //check the border: RIGHT
                    if (minNode.x_pos < width - 1) {
                        //update the information of the current node: value and parent
                        current = map[minNode.x_pos + 1][minNode.y_pos];
                        if (!closed.contains(current)) {
                            //check reachability: RIGHT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: DOWNRIGHT
                    if (minNode.x_pos < width - 1 && minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos + 1][minNode.y_pos + 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: UPRIGHT
                    if (minNode.x_pos < width - 1 && minNode.y_pos > 0) {
                        current = map[minNode.x_pos + 1][minNode.y_pos - 1];
                        if (!closed.contains(current)) {
                            //check reachability: DOWNRIGHT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: LEFT
                    if (minNode.x_pos > 0) {
                        current = map[minNode.x_pos - 1][minNode.y_pos];
                        if (!closed.contains(current)) {
                            //check reachability: LEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: DOWNLEFT
                    if (minNode.x_pos > 0 && minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos - 1][minNode.y_pos + 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPRIGHT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: UPLEFT
                    if (minNode.x_pos > 0 && minNode.y_pos > 0) {
                        current = map[minNode.x_pos - 1][minNode.y_pos - 1];
                        if (!closed.contains(current)) {
                            //check reachability: UPLEFT
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (14 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 14 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: DOWN
                    if (minNode.y_pos < height - 1) {
                        current = map[minNode.x_pos][minNode.y_pos + 1];
                        if (!closed.contains(current)) {
                            //check reachability: DOWN
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                            }
                        }
                    }
                    //check the border: UP
                    if (minNode.y_pos > 0) {
                        current = map[minNode.x_pos][minNode.y_pos - 1];
                        if (!closed.contains(current)) {
                            //check reachability: UP
                            if (!open.contains(current)) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                                //check if it's the destination
                                if (checkReachable(current.value, minNode.value, max_h)) {
                                    if (current.x_pos == target_x && current.y_pos == target_y) {
                                        if (current.cost < s_cost) {
                                            path.clear();
                                            s_cost = current.cost;
                                            while (current.parent != null) {
                                                path.add(current);
                                                current = current.parent;
                                            }
                                            path.add(current);
                                        }
                                    } else {
                                        open.add(current);
                                    }
                                }
                            } else if (10 + minNode.cost < current.cost) {
                                current.parent = minNode;
                                current.cost = 10 + current.parent.cost;
                            }
                        }
                    }
                    closed.add(minNode);
                }
                //write the output
                if (!path.isEmpty()) {
                    for (int j = path.size() - 1; j >= 0; j--) {
                        if (j == 0) {
                            myWriter.write(path.get(j).x_pos + "," + path.get(j).y_pos);
                        } else {
                            myWriter.write(path.get(j).x_pos + "," + path.get(j).y_pos + " ");
                        }
                    }
                    if (q != targetNum - 1) {
                        myWriter.write("\n");
                    }
                } else {
                    myWriter.write("FAIL");
                    if (q != targetNum - 1) {
                        myWriter.write("\n");
                    }
                }
            } else {
                myWriter.write("FAIL");
            }
        }
        myWriter.close();
    }

    static boolean checkReachable(int current, int minNode, int max_h) {
        if (current >= 0 && minNode >= 0) {
            return true;
        } else if (current < 0 && minNode >= 0 && current >= max_h) {
            return true;
        } else if (current >= 0 && minNode < 0 && minNode >= max_h) {
            return true;
        } else if (current < 0 && minNode < 0 && Math.abs(current - minNode) <= Math.abs(max_h)) {
            return true;
        }
        return false;
    }

    static int generateH(int x, int y, int targetx, int targety) {
        return Math.abs(x - targetx) * 10 + Math.abs(y - targety) * 10;
    }

    static int getHeightDiff(int current, int minNode) {
        if (current >= 0 && minNode >= 0) {
            return 0;
        } else if (current < 0 && minNode >= 0) {
            return Math.abs(current);
        } else if (current >= 0 && minNode < 0) {
            return Math.abs(minNode);
        } else {
            return Math.abs(current - minNode);
        }
    }

    static class node {
        public node parent;
        int x_pos;
        int y_pos;
        int value;
        int cost;
        int f;

        public node(node parent, int x_pos, int y_pos, int value, int cost) {
            this.parent = parent;
            this.x_pos = x_pos;
            this.y_pos = y_pos;
            this.value = value;
            this.cost = cost;
        }

    }

}