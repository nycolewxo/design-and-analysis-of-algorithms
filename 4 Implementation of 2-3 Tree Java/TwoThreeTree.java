import java.io.*;
import java.util.*;

public class TwoThreeTree{
    static class treeNode{
        int key1;
        int key2;
        int rank;
        treeNode child1;
        treeNode child2;
        treeNode child3;
        int data1;
        int data2;
        int data3;
        treeNode father;

        treeNode(int k1,int k2,int r,treeNode c1,treeNode c2,treeNode c3,treeNode f){
            key1=k1;
            key2=k2;
            rank=r;
            child1=c1;
            child2=c2;
            child3=c3;
            father=f;
        }

        public void printNode(treeNode tNode,BufferedWriter outFile){
            try{
                if(tNode.father==null){
                    if(tNode.child1==null){
                        outFile.write("("+tNode.key1+", "+tNode.key2+", "+tNode.rank+", null, null, null, null)\n");
                    }
                    else if(tNode.child3==null){
                        outFile.write("("+tNode.key1+", "+tNode.key2+", "+tNode.rank+", "+tNode.child1.key1+", "+tNode.child2.key1+", null, null)\n");
                    }
                    else if(tNode.child3!=null){
                        outFile.write("("+tNode.key1+", "+tNode.key2+", "+tNode.rank+", "+tNode.child1.key1+", "+tNode.child2.key1+", "+tNode.child3.key1+", null)\n");
                    }
                }
                else{
                    if(tNode.child1==null){
                        outFile.write("("+tNode.key1+", "+tNode.key2+", "+tNode.rank+", null, null, null, "+tNode.father.key1+")\n");
                    }
                    else if(tNode.child3==null){
                        outFile.write("("+tNode.key1+", "+tNode.key2+", "+tNode.rank+", "+tNode.child1.key1+", "+tNode.child2.key1+", null, "+tNode.father.key1+")\n");
                    }
                    else if(tNode.child3!=null){
                        outFile.write("("+tNode.key1+", "+tNode.key2+", "+tNode.rank+", "+tNode.child1.key1+", "+tNode.child2.key1+", "+tNode.child3.key1+", "+tNode.father.key1+")\n");
                    }
                }
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: outFile");
                System.out.println("Occurred in the printNode() method");
                e.printStackTrace();
            }
        }
    }

    static class Tree{
        treeNode Root;

        public treeNode initialTree(Scanner inFile,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering InitialTree() method\n");

                //step 1
                Root=new treeNode(-1,-1,-1,null,null,null,null);

                //step 2
                int data1=inFile.nextInt();
                int data2=inFile.nextInt();
                deBugFile.write("before swap data1 and data2 are: ("+data1+" ,"+data2+")\n");
                if(data2<data1){
                    int temp=data1;
                    data1=data2;
                    data2=temp;
                    swap(data1,data2);
                }
                deBugFile.write("after swap data1 and data2 are: ("+data1+", "+data2+")\n");
                //step 3
                treeNode newNode1=new treeNode(data1,-1,1,null,null,null,Root);

                //step 4
                treeNode newNode2=new treeNode(data2,-1,2,null,null,null,Root);

                //step 5
                Root.child1=newNode1;
                Root.child2=newNode2;
                Root.key1=data2;

                //step 6
                deBugFile.write("root node:");
                Root.printNode(Root,deBugFile);

                //step 7
                deBugFile.write("Exiting initialTree() method\n");

                //step 8
                return Root;
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the initialTree() method");
                e.printStackTrace();
                return null;
            }
        }

        public void build23Tree(Scanner inFile,treeNode root,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering build23Tree() method\n");

                //step 8
                while(inFile.hasNextInt()){
                    //step 1
                    int data=inFile.nextInt();
                    treeNode spot=findSpot(Root,data,deBugFile);


                    while(spot==null&&inFile.hasNextInt()){
                        data=inFile.nextInt();
                        spot=findSpot(Root,data,deBugFile);
                        deBugFile.write("In build23Tree(): printing spot info\n");
                        spot.printNode(spot,deBugFile);
                    }
    
                    //step 4
                    deBugFile.write("In build23Tree(): printing spot info\n");
                    //spot.printNode(spot,deBugFile);
                        
                    //step 5
                    treeNode leafNode=new treeNode(data,-1,5,null,null,null,null);

                    //step 6
                    treeInsert(spot,leafNode,deBugFile);

                    //step 7
                    deBugFile.write("In build23Tree: printing preOrder() after one treeInsert()\n");
                    preOrder(Root,deBugFile);
                }
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile\n");
                System.out.println("Occurred in the build23Tree() method\n");
                e.printStackTrace();
            }    
        }
        
        public static void swap(int data1,int data2){
            int temp;
            temp=data1;
            data1=data2;
            data2=temp;
        }

        public static treeNode findSpot(treeNode spot,int data,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering findSpot() method\n");

                deBugFile.write("spot's key 1 and key2 and data are: ("+spot.key1+", "+spot.key2+", "+data+")\n");
                spot.printNode(spot,deBugFile);

                //step 1
                if(spot.child1==null){
                    deBugFile.write("In findSpot(): You are at leaf level, you are too far down the tree!!\n");
                }

                //step 2
                if(data==spot.key1||data==spot.key2){
                    deBugFile.write("In findSpot(): Data is already in spot's keys, no need to search further!\n");
                    return null;
                }

                //step 3
                if(isLeaf(spot.child1)){
                    if(data==spot.child1.key1||data==spot.child2.key1){
                        deBugFile.write("In findSpot(): Data is already in a leaf node\n");
                        return null;
                    }
                    else{
                        return spot;
                    }
                }
                else{
                    if(data<spot.key1){
                        return findSpot(spot.child1,data,deBugFile);
                    }
                    else if(spot.key2==-1||data<spot.key2){
                        return findSpot(spot.child2,data,deBugFile);
                    }
                    else if(spot.key2!=-1&&data>=spot.key2){
                        return findSpot(spot.child3,data,deBugFile);
                    }
                    else{
                        deBugFile.write("In findSpot(): Something is wrong about data: "+data+"\n");
                        return null;
                    }
                }
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the findSpot() method");
                e.printStackTrace();
                return null;
            }
        }

        public void treeInsert(treeNode spot,treeNode newNode,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering treeInsert() method\n");
                if(spot==null){
                    deBugFile.write("In treeInsert(): Spot is null, something is wrong\n");
                    return;
                }
                else{
                    deBugFile.write("In treeInsert(): Printing spot and newNode info\n");
                    spot.printNode(spot,deBugFile);
                    newNode.printNode(newNode,deBugFile);
                }
                //step 1
                int count;
                if(spot.key2==-1){
                    count=2;
                }
                else{
                    count=3;
                }

                //step 2
                deBugFile.write("In treeInsert() method: Spot's kids count is "+count+"\n");

                //step 3
                if(count==2){
                    spotHas2kidsCase(spot,newNode,deBugFile);
                }
                else if(count==3){
                    spotHas3kidsCase(spot,newNode,deBugFile);
                }
                deBugFile.write("Leaving treeInsert() method\n");
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the treeInsert() method");
                e.printStackTrace();
            }
        }

        public static int countKids(treeNode tNode){
            if(tNode.child3==null){
                return 2;
            }
            return 3;
        }

        public void spotHas2kidsCase(treeNode spot,treeNode newNode,BufferedWriter deBugFile){
            try{
                //step 1
                deBugFile.write("Entering spotHas2kidsCase() method\n");
                deBugFile.write("In spotHas2kidsCase() method: spot's rank is "+spot.rank+"\n");

                //step 2
                if(newNode.key1<spot.child2.key1){
                    spot.child3=spot.child2;
                    spot.child2=newNode;
                }
                else{
                    spot.child3=newNode;
                }

                //step 3
                if(spot.child2.key1<spot.child1.key1){
                    treeNode tempNode=spot.child1;
                    spot.child1=spot.child2;
                    spot.child2=tempNode;
                }

                //step 4
                spot.child1.father=spot;
                spot.child1.rank=1;
                spot.child2.father=spot;
                spot.child2.rank=2;
                spot.child3.father=spot;
                spot.child3.rank=3;

                //step 5
                updateKeys(spot,deBugFile);

                //step 6
                if(spot.rank>1){
                    updateKeys(spot.father,deBugFile);
                }

                //step 7
                deBugFile.write("Leaving spotHas2kidsCase() method\n");
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the spotHas2KidsCase() method");
                e.printStackTrace();
            }
        }

        public void spotHas3kidsCase(treeNode spot,treeNode newNode,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering spotHas3kidsCase() method\n");
                deBugFile.write("In spotHas3kidsCase() method: spot's rank is "+ spot.rank+"\n");

                //step 1
                treeNode sibling=new treeNode(-1,-1,5,null,null,null,null);

                //step 2
                if(newNode.key1>spot.child3.key1){
                    sibling.child2=newNode;
                    sibling.child1=spot.child3;
                    spot.child3=null;
                }
                else if(newNode.key1<spot.child3.key1){
                    sibling.child2=spot.child3;
                    spot.child3=newNode;
                }

                //step 3
                if(spot.child3!=null){
                    if(spot.child3.key1>spot.child2.key1){
                        sibling.child1=spot.child3;
                        spot.child3=null;
                    }
                    else{
                        sibling.child1=spot.child2;
                        spot.child2=newNode;
                    }
                }
                else if(spot.child2.key1<spot.child1.key1){
                    treeNode tempNode=spot.child1;
                    spot.child1=spot.child2;
                    spot.child2=tempNode;
                }

                //step 4
                spot.child1.father=spot;
                spot.child1.rank=1;
                spot.child2.father=spot;
                spot.child2.rank=2;
                spot.child3=null;

                //step 5
                sibling.child1.father=sibling;
                sibling.child1.rank=1;
                sibling.child2.father=sibling;
                sibling.child2.rank=2;
                sibling.child3=null;

                //step 6
                updateKeys(spot,deBugFile);
                updateKeys(sibling,deBugFile);

                //step 7
                if(spot.rank==-1&&spot.father==null){
                    Root=makeNewRoot(spot,sibling,deBugFile);
                }
                else{
                    treeInsert(spot.father,sibling,deBugFile);
                }

                //step 8
                if(spot.rank>1){
                    updateKeys(spot.father,deBugFile);
                }

                //step 9
                deBugFile.write("Leaving spotHas3kidsCase() method\n");
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the spotHas3KidsCase() method");
                e.printStackTrace();
            }
        }

        public static boolean isLeaf(treeNode tNode){
            if(tNode.child1==null){
                return true;
            }
            return false;
        }

        public treeNode makeNewRoot(treeNode spot,treeNode sibling,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering makeNewRoot() method\n");
                //step 1
                treeNode newRoot=new treeNode(-1,-1,-1,null,null,null,null);
                newRoot.child1=spot;
                newRoot.child2=sibling;
                newRoot.child3=null;
                newRoot.key1=findMinLeaf(sibling);
                newRoot.key2=-1;
                spot.father=newRoot;
                spot.rank=1;
                sibling.father=newRoot;
                sibling.rank=2;

                //step 2
                deBugFile.write("Leaving makeNewRoot() method\n");
                return newRoot;
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the makeNewRoot() method");
                e.printStackTrace();
                return null;
            }
        }

        public static void updateKeys(treeNode tNode,BufferedWriter deBugFile){
            try{
                //step 0
                deBugFile.write("Entering updateKeys() methods\n");

                //step 1
                if(tNode==null){
                    return;
                }
                //step 2
                tNode.key1=findMinLeaf(tNode.child2);

                //step 3
                tNode.key2=findMinLeaf(tNode.child3);

                //step 4
                if(tNode.rank>1){
                    updateKeys(tNode.father,deBugFile);
                }

                //step 5
                deBugFile.write("Leaving updateKeys() methods\n");
            }
            catch(IOException e){
                System.out.println("Error while writing in the File: deBugFile");
                System.out.println("Occurred in the updateKeys() method");
                e.printStackTrace();
            }
        }

        public static int findMinLeaf(treeNode tNode){
            //step 1
            if(tNode==null){
                return -1;
            }
            if(tNode.child1==null){
                return tNode.key1;
            }
            else{
                return findMinLeaf(tNode.child1);
            }
        }

        public void preOrder(treeNode tNode,BufferedWriter outFile){
            if(tNode==null){
                return;
            }
            else{
                tNode.printNode(tNode,outFile);
                preOrder(tNode.child1,outFile);
                preOrder(tNode.child2,outFile);
                preOrder(tNode.child3,outFile);
            }
        }
        public int treeDepth(treeNode root){
            return 6;
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
                    Tree ttTree=new Tree();

                    //step 1
                    treeNode root=ttTree.initialTree(readFile,writeDebug);

                    //step 2
                    ttTree.build23Tree(readFile,root,writeDebug);

                    //step 3
                    ttTree.preOrder(ttTree.Root,writeOut);

                    //step 4
                    writeOut.close();
                    writeDebug.close();
                    readFile.close();
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
