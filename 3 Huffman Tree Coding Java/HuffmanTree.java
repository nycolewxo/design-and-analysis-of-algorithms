import java.io.*;
import java.util.*;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class HuffmanTree{
    static class HNode{
        String chStr;
        int prob;
        String code;
        int numBits;
        int entropy;
        HNode left;
        HNode right;
        HNode next;
        HNode(String ch, int p, String c, int bits, int entr, HNode l, HNode r,HNode n){
            chStr=ch;
            prob=p;
            code=c;
            numBits=bits;
            entropy=entr;
            left=l;
            right=r;
            next=n;
        }

        public void printNode(HNode T,File outFile){
            try{
                FileWriter writeFile=new FileWriter(outFile,true);
                BufferedWriter writeOut=new BufferedWriter(writeFile);
                if(T.left!=null&&T.right!=null){
                    writeOut.write("("+T.chStr+", "+T.prob+", "+T.code+", "+T.numBits+", "+T.entropy+", "+T.left.chStr+", "+T.right.chStr+")\n");
                }
                else if(T.left!=null){
                    writeOut.write("("+T.chStr+", "+T.prob+", "+T.code+", "+T.numBits+", "+T.entropy+", "+T.left.chStr+", "+T.right+")\n");
                }
                else if(T.right!=null){
                    writeOut.write("("+T.chStr+", "+T.prob+", "+T.code+", "+T.numBits+", "+T.entropy+", "+T.left+", "+T.right.chStr+")\n");
                }
                else{
                    writeOut.write("("+T.chStr+", "+T.prob+", "+T.code+", "+T.numBits+", "+T.entropy+", "+T.left+", "+T.right+")\n");
                }
                writeOut.close();

            }
            catch(IOException e){
                System.out.println("Error: Problem occurred while in printNode.");
                e.printStackTrace();
            }
        }
    }

    static class Huffman{
        HNode listHead;
        HNode root;
        int totalEntropy;
        Huffman(){
            root=null;
        }
        public static HNode findSpot(HNode listHead,HNode newNode){
            //from lecture notes
            HNode spot=listHead;

            //sort by increasing probability
            while(spot.next!=null&&spot.next.prob<newNode.prob){
                spot=spot.next;
            }
            return spot;
        }

        static void listInsert(HNode listHead,HNode newNode,File deBugFile){
            try{
                //step 0
                FileWriter fileWriter=new FileWriter(deBugFile,true);
                BufferedWriter writeDebug=new BufferedWriter(fileWriter);
                writeDebug.write("In listInsert method\n");
    
                //step 1
                HNode spot=findSpot(listHead,newNode);
    
                writeDebug.write("Returns from findSpot where findSpot is at \n");
                writeDebug.close();
                //step 2
                newNode.next=spot.next;
                spot.next=newNode;
            }
            catch(IOException e){
                System.out.println("Error: Problem has occurred in listInsert.");
            }
        }

        public void constructHuffmanLList(File inFile,File deBugFile,HNode listHead){
            try{
                //step 1
                FileWriter fileWriter=new FileWriter(deBugFile,true);
                BufferedWriter writeDebug=new BufferedWriter(fileWriter);
                writeDebug.write("Entering constructHuffmanLList method\n");
                try{
                    String chr;
                    int prob;
                    Scanner readFile=new Scanner(new FileReader(inFile));

                    //step 6
                    while(readFile.hasNext()){
                        //step 2
                        chr=readFile.next();
                        prob=readFile.nextInt();

                        //step 3
                        HNode newNode=new HNode(chr,prob,"",0,0,null,null,null);

                        //step 4
                        listInsert(listHead,newNode,deBugFile);

                        //step 5
                        printList(listHead,deBugFile);
                    }

                    //step 7
                    writeDebug.write("Exit constructHuffmanLList method\n");
                    writeDebug.close();
                }
                catch(FileNotFoundException e){
                    System.out.println("Error: File not found.");
                    e.printStackTrace();
                }
            }
            catch(IOException e){
                System.out.println("Error: Problem has occurred while in constructHuffmanList.");
                e.printStackTrace();
            }
        }

        public HNode constructHuffmanBinTree(HNode listHead,File deBugFile){
            try{
                //step 0
                FileWriter writeFile=new FileWriter(deBugFile,true);
                BufferedWriter writeDebug=new BufferedWriter(writeFile);
                writeDebug.write("Entering constructHuffmanBinTree method\n");
                writeDebug.close();

                //step 5
                while(listHead.next.next!=null){
                    //step 1
                    HNode newNode=new HNode("",0,"",0,0,null,null,null);
                    newNode.prob=listHead.next.prob+listHead.next.next.prob;
                    newNode.chStr=listHead.next.chStr+listHead.next.next.chStr;
                    newNode.left=listHead.next;
                    newNode.right=listHead.next.next;

                    //step 2
                    listInsert(listHead,newNode,deBugFile);

                    //step 3
                    listHead.next=listHead.next.next.next;

                    //step 4
                    printList(listHead,deBugFile);
                }
                //step 6
                return listHead.next;
            }
            catch(IOException e){
                System.out.println("Error: Problem has occurred while in constructHuffmanBinTree.");
                e.printStackTrace();
                return null;
            }
        }

        public void printEntropyTable(HNode T,String code,File outFile,int totalEntr){
            //from project notes
            if(isLeaf(T)){
                T.code=code;
                T.numBits=code.length();
                T.entropy=T.prob*T.numBits;
                //totalEntropy+=T.entropy;
                totalEntropy+=T.entropy;
                try{
                    FileWriter writeFile=new FileWriter(outFile,true);
                    BufferedWriter buffOut=new BufferedWriter(writeFile);
                    PrintWriter writeOut=new PrintWriter(buffOut);
                    writeOut.printf("%-10s%-10d%-10s%-10d%-10d\n",T.chStr,T.prob,T.code,T.numBits,T.entropy);
                    writeOut.close();
                }
                catch(IOException e){
                    System.out.println("Error: Problem occurred while in printEntropyTree.");
                }
            }
            else{
                printEntropyTable(T.left,code+"0",outFile,totalEntropy);
                printEntropyTable(T.right,code+"1",outFile,totalEntropy);
            }
        }

        public void preOrder(HNode T,File outFile){
            //from lecture notes
            if(isLeaf(T)){
                T.printNode(T,outFile);
            }
            else{
                T.printNode(T,outFile);
                preOrder(T.left,outFile);
                preOrder(T.right,outFile);
            }
        }

        public void inOrder(HNode T,File outFile){
            //from lecture notes
            if(isLeaf(T)){
                T.printNode(T,outFile);
            }
            else{
                inOrder(T.left,outFile);
                T.printNode(T,outFile);
                inOrder(T.right,outFile);
            }
        }

        public void postOrder(HNode T,File outFile){
            //from lecture notes
            if(isLeaf(T)){
                T.printNode(T,outFile);
            }
            else{
                postOrder(T.left,outFile);
                postOrder(T.right,outFile);
                T.printNode(T,outFile);
            }
        }

        public boolean isLeaf(HNode T){
            if(T.left==null&&T.right==null){
                return true;
            }
            return false;
        }

        public void printList(HNode listHead,File outFile){
            HNode spot=listHead;
            try{
                FileWriter writeFile=new FileWriter(outFile,true);
                BufferedWriter writeOut=new BufferedWriter(writeFile);
                writeOut.write("listHead->");
                writeOut.close();

                while(spot.next!=null){
                    spot.printNode(spot,outFile);
                    try{
                        writeFile=new FileWriter(outFile,true);
                        writeOut=new BufferedWriter(writeFile);
                        writeOut.write("->");
                        writeOut.close();
                    }
                    catch(IOException e){
                        System.out.println("Error: Problem has occurred while in printList.");
                    }
                    spot=spot.next;
                }
                try{
                    writeFile=new FileWriter(outFile,true);
                    writeOut=new BufferedWriter(writeFile);
                    writeOut.write("NULL\n");
                    writeOut.close();
                }
                catch(IOException e){
                    System.out.println("Error: Problem has occurred while in printList.");
                }
            }
            catch(IOException e){
                System.out.println("Error: Problem has occurred while in printList.");
            }
        }
    }

    public static void main(String[] args){
        //step 0
        File inFile=new File(args[0]);
        File outFile=new File(args[1]);
        File deBugFile=new File(args[2]);

        //step 1
        HNode listHead=new HNode("dummy",0,"",0,0,null,null,null);

        //step 2
        Huffman huffman=new Huffman();
        huffman.constructHuffmanLList(inFile,deBugFile,listHead);
        huffman.printList(listHead,outFile);

        //step 3
        huffman.root=huffman.constructHuffmanBinTree(listHead,deBugFile);
        
        //step 4
        try{
            FileWriter writeFile=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(writeFile);
            writeOut.write("\nprinting the entropy table: \n");
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in the file");
        }

        //step 5
        int totalEntropy=0;

        //step 6
        huffman.printEntropyTable(huffman.root,"",outFile,totalEntropy);
        totalEntropy=huffman.totalEntropy;

        //step 7
        try{
            FileWriter writeFile=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(writeFile);
            writeOut.write("The Huffman Coding scheme's entropy = "+(double)totalEntropy/100+"\n");
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in the file");
        }

        //step 8
        try{
            FileWriter writeFile=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(writeFile);
            writeOut.write("\npreOrder: \n");
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in the file");
        }
        huffman.preOrder(huffman.root,outFile);

        //step 9
        try{
            FileWriter writeFile=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(writeFile);
            writeOut.write("\ninOrder: \n");
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in the file");
        }
        huffman.inOrder(huffman.root,outFile);

        //step 10
        try{
            FileWriter writeFile=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(writeFile);
            writeOut.write("\npostOrder: \n");
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in the file");
        }
        huffman.postOrder(huffman.root,outFile);
    }
}
