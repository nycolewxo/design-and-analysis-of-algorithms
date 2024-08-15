import java.io.*;
import java.util.*;
public class EightPuzzleAstar{
    static class AstarNode{
        int config[];
        int gStar;
        int hStar;
        int fStar;
        AstarNode next;
        AstarNode parent;
        AstarNode(int c[],int g,int h,int f,AstarNode n,AstarNode p){
            config=c;
            gStar=g;
            hStar=h;
            fStar=f;
            next=n;
            parent=p;
        }
        public void printNode(AstarNode node,BufferedWriter outFile)throws IOException{
            if(node.parent==null){
                outFile.write("< NO PARENT[");
            }
            else{
                outFile.write("< "+node.parent.fStar+" [");
                for(int i=0;i<9;i++){
                    outFile.write(node.parent.config[i]+" ");
                }
            }
            outFile.write("] :: "+node.fStar+" [");
            for(int i=0;i<9;i++){
                outFile.write(node.config[i]+" ");
            }
            outFile.write("] >\n");
        }
    }

    static class Astar{
        AstarNode startNode;
        AstarNode open;
        AstarNode childList;
        int table[][]={{0,1,2,1,2,3,2,3,4},
                       {1,0,1,2,1,2,3,2,3},
                       {2,1,0,3,2,1,4,3,2},
                       {1,2,3,0,1,2,1,2,3},
                       {2,1,2,1,0,1,2,1,2},
                       {3,2,1,2,1,0,3,2,1},
                       {2,3,4,1,2,3,0,1,2},
                       {3,2,3,2,1,2,1,0,1},
                       {4,3,2,3,2,1,2,1,0}};
        int initConfig[]=new int[9];
        int goalConfig[]=new int[9];
        int dummyConfig[]=new int[9];
        Astar(){

        }
        public int computeHstar(int[] nodeConfig,int[] goalConfig,BufferedWriter deBugFile)throws IOException{
            //step 0
            deBugFile.write("Entering computeHstar method\n");
            int sum=0;
            int i=0;
            //step 7
            while(i<9){
                //step 1
                int p1=nodeConfig[1];
                //step 2
                int j=0;
                //step 5
                while(j<9){
                    //step 3
                    if(goalConfig[j]==p1){
                        sum+=table[i][j];
                        break;
                    }
                    j++;
                }
                //step 6
                i++;
            }
            //step 8
            deBugFile.write("Leaving computeHstar method\n");
            deBugFile.write("sum="+sum+"\n");
            //step 9
            return sum;
        }
        public AstarNode expandChildList(AstarNode currentNode,BufferedWriter deBugFile)throws IOException{
            //step 0
            deBugFile.write("Entering expandChildList method\n");
            deBugFile.write("Printing currentNode: ");
            currentNode.printNode(currentNode,deBugFile);
            AstarNode tempList=new AstarNode(dummyConfig,0,0,0,null,null);
            //step 1
            int i=0;
            //step 3
            while(currentNode.config[i]!=0&&i<9){
                //step 2
                if(currentNode.config[i]!=0){
                    i++;
                }
            }
            //step 4
            if(i>=9){
                deBugFile.write("Something is wrong, currentNode does not have a zero in it.\n");
                return null;
            }
            int zeroPosition=i;
            deBugFile.write("Found the zero position in currentNode at position i="+zeroPosition+"\n");
            //step 5
            int j=0;
            //step 8
            while(j<9){
                int[] tempStore=new int[9];
                for(int k=0;k<9;k++){
                    tempStore[k]=currentNode.config[k];
                }
                //step 6
                if(table[zeroPosition][j]==1){
                    AstarNode newNode=new AstarNode(tempStore,999,999,999,null,currentNode);
                    newNode.config[j]=0;
                    newNode.config[zeroPosition]=currentNode.config[j];
                    if(checkAncestors(newNode,currentNode)==false){
                        newNode.next=tempList.next;
                        tempList.next=newNode;
                    }
                }
                //step 7
                j++;
            }
            //step 9
            deBugFile.write("Leaving expandChildList method and printing tempList\n");
            printList(tempList,deBugFile);
            //step 10
            return tempList;
        }
        public void openInsert(AstarNode node){
            AstarNode spot=open;
            while(spot.next!=null&&spot.next.fStar<node.fStar){
                spot=spot.next;
            }
            node.next=spot.next;
            spot.next=node;
        }
        public AstarNode remove(AstarNode list){
            AstarNode temp=list.next;
            list.next=temp.next;
            temp.next=null;
            return temp;
        }
        public boolean match(int[] config1,int[] config2){
            for(int i=0;i<9;i++){
                if(config1[i]!=config2[i]){
                    return false;
                }
            }
            return true;
        }
        public boolean checkAncestors(AstarNode child,AstarNode currentNode){
            AstarNode spot=currentNode.parent;
            while(spot!=null){
                if(match(spot.config,child.config)){
                    return true;
                }
                spot=spot.parent;
            }
            return false;
        }
        public void printList(AstarNode list,BufferedWriter outFile)throws IOException{
            AstarNode spot=list;
            while(spot.next!=null){
                spot.printNode(spot,outFile);
                spot=spot.next;
            }
        }
        public void printSolution(AstarNode currentNode,BufferedWriter outFile)throws IOException{
            AstarNode spot=currentNode;
            int j=0;
            while(spot!=null){
                for(int i=0;i<9;i++){
                    outFile.write(spot.config[i]+" ");
                    j++;
                    if(j==3){
                        outFile.write("\n");
                        j=0;
                    }
                }
                spot=spot.parent;
                outFile.write("\n");
            }
        }
    }
    public static void main(String args[])throws FileNotFoundException, IOException{
        //step 0
        Scanner inFile1=new Scanner(new File(args[0]));
        Scanner inFile2=new Scanner(new File(args[1]));
        BufferedWriter outFile1=new BufferedWriter(new FileWriter(new File(args[2])));
        BufferedWriter outFile2=new BufferedWriter(new FileWriter(new File(args[3])));
        BufferedWriter deBugFile=new BufferedWriter(new FileWriter(new File(args[4])));
        Astar a=new Astar();
        for(int i=0;i<9;i++){
            a.initConfig[i]=inFile1.nextInt();
            a.goalConfig[i]=inFile2.nextInt();
            a.dummyConfig[i]=-1;
        }
        a.startNode=new AstarNode(a.initConfig,0,9999,9999,null,null);
        a.open=new AstarNode(a.dummyConfig,0,0,0,null,null);
        a.childList=new AstarNode(a.dummyConfig,0,0,0,null,null);

        //step 1
        a.startNode.gStar=0;
        a.startNode.hStar=a.computeHstar(a.startNode.config,a.goalConfig,deBugFile);
        a.startNode.fStar=a.startNode.gStar+a.startNode.hStar;
        a.openInsert(a.startNode);

        int i=0;
        AstarNode currentNode=new AstarNode(a.dummyConfig,0,0,0,null,null);
        //step 10
        while(!a.match(currentNode.config,a.goalConfig)&&a.open.next!=null){
            //step 2
            currentNode=a.remove(a.open);
            deBugFile.write("this is currentNode: ");
            currentNode.printNode(currentNode,deBugFile);

            //step 3
            if(currentNode!=null&&a.match(currentNode.config,a.goalConfig)){
                outFile2.write("A solution is found!!\n");
                a.printSolution(currentNode,outFile2);
                inFile1.close();
                inFile2.close();
                outFile1.close();
                outFile2.close();
                deBugFile.close();
                System.exit(0);
            }

            //step 4
            a.childList=a.expandChildList(currentNode,deBugFile);

            //step 8
            while(a.childList.next!=null){
                //step 5
                AstarNode child=a.remove(a.childList);
                deBugFile.write("In main(), remove node from childList, and printing: ");
                child.printNode(child,deBugFile);

                //step 6
                child.gStar=currentNode.gStar+1;
                child.hStar=a.computeHstar(child.config,a.goalConfig,deBugFile);
                child.fStar=child.gStar+child.hStar;
                child.parent=currentNode;

                //step 7
                a.openInsert(child);
        
                //step 9
                if(i<30){
                    outFile1.write("Below is open list: ");
                    a.printList(a.open,outFile1);
                    i++;
                }
            }
        }
        //step 11
        if(a.open.next==null&&!(a.match(currentNode.config,a.goalConfig))){
            outFile1.write("No solution can be found in the search!\n");
        }
        //step 12
        inFile1.close();
        inFile2.close();
        outFile1.close();
        outFile2.close();
        deBugFile.close();
    }
}
