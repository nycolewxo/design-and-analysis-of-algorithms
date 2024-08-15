import java.io.*;
import java.util.*;
public class Dijkstra{
    static class DijkstraSSS{
        int numNodes;
        int sourceNode;
        int minNode;
        int currentNode;
        int newCost;
        int costMatrix[][];
        int fatherAry[];
        int ToDoAry[];
        int BestAry[];

        DijkstraSSS(){

        }

        public void loadCostMatrix(Scanner inFile){
            for(int i=1;i<costMatrix.length;i++){
                for(int j=1;j<costMatrix[i].length;j++){
                    if(i==j){
                        costMatrix[i][j]=0;
                    }
                    else{
                        costMatrix[i][j]=9999;
                    }
                }
            }

            while(inFile.hasNextLine()){
                int fromNode=inFile.nextInt();
                int toNode=inFile.nextInt();
                int cost=inFile.nextInt();
                costMatrix[fromNode][toNode]=cost;
            }
        }

        public void setBestAry(int sourceNode){
            for(int i=1;i<BestAry.length;i++){
                BestAry[i]=costMatrix[sourceNode][i];
            }
        }

        public void setFatherAry(int sourceNode){
            for(int i=1;i<fatherAry.length;i++){
                fatherAry[i]=sourceNode;
            }
        }

        public void setToDoAry(int sourceNode){
            for(int i=1;i<ToDoAry.length;i++){
                if(i==sourceNode){
                    ToDoAry[i]=0;
                }
                else{
                    ToDoAry[i]=1;
                }
            }
        }

        public int findMinNode(int minNode,int node){
            //step 0
            int minCost=99999;
            minNode=0;

            //step 1
            int index=1;

            //step 4
            while(index<=numNodes){
                //step 2
                if(ToDoAry[index]==1&&BestAry[index]<minCost){
                    minCost=BestAry[index];
                    minNode=index;
                }
                //step 3
                index++;
            }
            return minNode;
        }
        public int computeCost(int minNode, int node){
            int cost=BestAry[minNode]+costMatrix[minNode][node];
            return cost;
        }

        public boolean checkToDoAry(){
            for(int i=0;i<ToDoAry.length; i++){
                if(ToDoAry[i]==1){
                    return false;
                }
            }
            return true;
        }

        public void deBugPrint(BufferedWriter deBugFile) throws IOException{
            //sourceNode with heading
            deBugFile.write("the sourceNode is: "+sourceNode);

            //fatherAry with heading
            deBugFile.write("\nthe fatherAry is: ");
            for(int i=1;i<numNodes+1;i++){
                deBugFile.write(fatherAry[i]+" ");
            }

            //BestAry with heading
            deBugFile.write("\nthe BestAry is: ");
            for(int i=1;i<numNodes+1;i++){
                deBugFile.write(BestAry[i]+" ");
            }

            //ToDoAry with heading
            deBugFile.write("\nthe ToDoAry is: ");
            for(int i=1;i<numNodes+1;i++){
                deBugFile.write(ToDoAry[i]+" ");
            }
            deBugFile.write("\n\n");
        }
        public void printShortestPath(int currentNode,int sourceNode,BufferedWriter outFile) throws IOException{
            outFile.write("The path from "+sourceNode+" to "+currentNode+" : "+currentNode+" <- ");
            int path=currentNode;
            while(fatherAry[path]!=sourceNode){
                outFile.write(fatherAry[path]+" <- ");
                path=fatherAry[path];
            }
            outFile.write(sourceNode+" : cost="+BestAry[currentNode]+"\n");
        }
    }
    public static void main(String args[]){
        //step 0
        File inFile=new File(args[0]);
        File outFile=new File(args[1]);
        File deBugFile=new File(args[2]);

        Scanner readFile;
        BufferedWriter writeOut;
        BufferedWriter writeDebug;
        try{
            readFile=new Scanner(new FileReader(inFile));
            try{
                writeOut=new BufferedWriter(new FileWriter(outFile));
                try{
                    writeDebug=new BufferedWriter(new FileWriter(deBugFile));
                    DijkstraSSS dijkstraSSS=new DijkstraSSS();
                    dijkstraSSS.numNodes=readFile.nextInt();
                    dijkstraSSS.costMatrix=new int[dijkstraSSS.numNodes+1][dijkstraSSS.numNodes+1];
                    dijkstraSSS.BestAry=new int[dijkstraSSS.numNodes+1];
                    dijkstraSSS.fatherAry=new int[dijkstraSSS.numNodes+1];
                    dijkstraSSS.ToDoAry=new int[dijkstraSSS.numNodes+1];

                    writeOut.write("==================================================\n");
                    writeOut.write("There are "+dijkstraSSS.numNodes+" nodes in the input graph. Below are all pairs of shortest path: \n");

                    //step 1
                    dijkstraSSS.loadCostMatrix(readFile);
                    dijkstraSSS.sourceNode=1;

                    //step 14
                    while(dijkstraSSS.sourceNode<=dijkstraSSS.numNodes){
                        //step 2
                        dijkstraSSS.setBestAry(dijkstraSSS.sourceNode);
                        dijkstraSSS.setFatherAry(dijkstraSSS.sourceNode);
                        dijkstraSSS.setToDoAry(dijkstraSSS.sourceNode);

                        //step 8
                        while(!dijkstraSSS.checkToDoAry()){
                            //step 3
                            dijkstraSSS.minNode=dijkstraSSS.findMinNode(0,0);
                            dijkstraSSS.ToDoAry[dijkstraSSS.minNode]=0;
                            dijkstraSSS.deBugPrint(writeDebug);

                            //step 4
                            int childNode=1;

                            //step 7
                            while(childNode<=dijkstraSSS.numNodes){
                                //step 5
                                if(dijkstraSSS.ToDoAry[childNode]==1){
                                    int newCost=dijkstraSSS.computeCost(dijkstraSSS.minNode,childNode);
                                    if(newCost<dijkstraSSS.BestAry[childNode]){
                                        dijkstraSSS.BestAry[childNode]=newCost;
                                        dijkstraSSS.fatherAry[childNode]=dijkstraSSS.minNode;
                                        dijkstraSSS.deBugPrint(writeDebug);
                                    }
                                }
                                //step 6
                                childNode++;
                            }
                        }
                        //step 9
                        dijkstraSSS.currentNode=1;
                        
                        writeOut.write("==================================================\n");
                        writeOut.write("source node: "+dijkstraSSS.sourceNode+"\n\n");
                        //step 12
                        while(dijkstraSSS.currentNode<=dijkstraSSS.numNodes){
                            //step 10
                            dijkstraSSS.printShortestPath(dijkstraSSS.currentNode, dijkstraSSS.sourceNode, writeOut);

                            //step 11
                            dijkstraSSS.currentNode++;
                        }
                        //step 13
                        dijkstraSSS.sourceNode++;
                    }
                    readFile.close();
                    writeOut.close();
                    writeDebug.close();
                }
                catch(IOException e){
                    System.out.println("Error while writing in the File: outFile");
                    e.printStackTrace();
                }
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                e.printStackTrace();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Error while trying to read File: inFile");
            e.printStackTrace();
        }
    }
}
