import java.io.*;
import java.util.*;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class LLMiddleNode {
    listNode middleNode;
    static class listNode{
        String data;
        listNode next;
        listNode (String d){
            data=d;
            next=null;
        }
    }

    static void constructLL(listNode listHead,File inFile,File deBugFile){
        try{
            //step 0
            FileWriter fileWriter=new FileWriter(deBugFile,true);
            BufferedWriter writeDebug=new BufferedWriter(fileWriter);
            writeDebug.write("In constructLL method\n");
            writeDebug.close();
            try{
                String line;
                String data;
                Scanner readFile=new Scanner(new FileReader(inFile));
                while(readFile.hasNextLine()){
                    line=readFile.nextLine();
                    StringTokenizer myTokens=new StringTokenizer(line," ");
                    //step 5
                    while(myTokens.hasMoreTokens()){
                        //step 1
                        data=myTokens.nextToken();

                        //step 2
                        listNode newNode=new listNode(data);

                        //step 3
                        listInsert(listHead,newNode,deBugFile);

                        //step 4
                        printList(listHead,deBugFile);
                    }
                }
            }
            catch(FileNotFoundException e){
                System.out.println("Error: File not found.");
                e.printStackTrace();
            }
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in file.");
            e.printStackTrace();
        }     
    }

    static void listInsert(listNode listHead,listNode newNode, File deBugFile){
        try{
            //step 0
            FileWriter fileWriter=new FileWriter(deBugFile,true);
            BufferedWriter writeDebug=new BufferedWriter(fileWriter);
            writeDebug.write("In listInsert method\n");

            //step 1
            listNode spot=findSpot(listHead,newNode);

            writeDebug.write("Returns from findSpot where findSpot is at \n");
            writeDebug.write("findSpot is at "+spot.data+"\n");
            writeDebug.close();
            //step 2
            newNode.next=spot.next;
            spot.next=newNode;
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in file.");
        }
    }

    public static listNode findSpot(listNode listHead,listNode newNode){
        //from lecture notes
        listNode spot=listHead;

        //use compareToIgnoreCase to get comparison of words
        while(spot.next!=null&&spot.next.data.compareToIgnoreCase(newNode.data)<0){
            spot=spot.next;
        }
        return spot;
    }

    public static void printList(listNode listHead,File outFile){
        listNode spot=listHead;
        int count=0;
        try{
            FileWriter fileWriter=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(fileWriter);
            writeOut.write("listHead->");
            while(spot.next!=null){
                writeOut.write("("+spot.data+","+spot.next.data+")->");
                count++;
                if(count==5){
                    writeOut.write("\n");
                    count=0;
                }
                spot=spot.next;
            }
            writeOut.write("NULL\n");
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in File");
        }
    }

    static listNode findMiddleNode(listNode listHead, File deBugFile){
        try{
            //step 0
            FileWriter fileWriter=new FileWriter(deBugFile,true);
            BufferedWriter writeDebug=new BufferedWriter(fileWriter);
            writeDebug.write("In findMiddleNode method\n");
            
            //step 1
            listNode walker1=listHead.next;
            listNode walker2=listHead.next;

            //step 2 & step 4
            while(walker2!=null&&walker2.next!=null){
                walker1=walker1.next;
                walker2=walker2.next.next;

                //step 3
                writeDebug.write("walker1's data is "+walker1.data+"\n");
            }
            writeDebug.close();
            return walker1;
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in file.");
            e.printStackTrace();
            return listHead;
        }
    }
    public static void main(String[] args){
        //step 1
        File inFile=new File(args[0]);
        File outFile=new File(args[1]);
        File deBugFile=new File(args[2]);

        //step 2
        listNode listHead=new listNode("dummy");

        //step 3
        constructLL(listHead,inFile,deBugFile);

        //step 4
        printList(listHead,outFile);
        

        //step 5
        listNode middleNode=findMiddleNode(listHead,deBugFile);   

        //step 6
        try{
            FileWriter fileWriter=new FileWriter(outFile,true);
            BufferedWriter writeOut=new BufferedWriter(fileWriter);
            if(middleNode!=null){
                writeOut.write("the word in the middle of list is "+middleNode.data);
            }
            writeOut.close();
        }
        catch(IOException e){
            System.out.println("Error: Problem has occurred while writing in file.");
            e.printStackTrace();
        }
    }
}
