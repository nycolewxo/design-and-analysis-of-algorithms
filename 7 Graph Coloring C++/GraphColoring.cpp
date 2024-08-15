#include<iostream>
#include<string>
#include<fstream>
using namespace std;

class node{
    public:
    int ID;
    node* next;
    node(int id){
        ID=id;
        next=NULL;
    }
};

class colorGraph{
    public:
    int numNodes;
    int numUncolor;
    node** hashTable;
    int* colorAry;
    colorGraph(ifstream &inFile){
        inFile>>numNodes;
        numUncolor=numNodes;
        hashTable=new node*[numNodes+1];
        colorAry=new int[numNodes+1];
        for(int i=0;i<numNodes+1;i++){
            hashTable[i]=NULL;
        }
        for(int i=0;i<=numNodes;i++){
            colorAry[i]=0;
        }
    }

    void loadGraph(ifstream &inFile){
        int i;
        int j;
        while(inFile>>i&&inFile>>j){
            hashInsert(i,j);
            hashInsert(j,i);
        }
    }

    void hashInsert(int id1,int id2){
        node* newNode=new node(id2);
        if(hashTable[id1]==NULL){
            hashTable[id1]=newNode;
        }
        else{
            newNode->next=hashTable[id1];
            hashTable[id1]=newNode;
        }
    }

    void printHashTable(ofstream &outFile){ 
        node* spot;
        for(int i=1;i<=numNodes;i++){
            outFile<<"hashTable["<<i<<"]->";
            spot=hashTable[i];
            while(spot!=NULL){
                outFile<<spot->ID<<"->";
                spot=spot->next;
            }
            outFile<<"NULL\n";
        }
    }

    void method1(ofstream &outFile,ofstream &deBugFile){
        //step 0
        deBugFile<<"entering method 1\n";
        int newColor=64;

        //step 7
        while(numUncolor>0){
            //step 1
            newColor++;

            //step 2
            int nodeID=1;

            //step 5
            while(nodeID<=numNodes){
                //step 3
                if(colorAry[nodeID]==0){
                    if(checkNeighbors(nodeID,newColor)==true){
                        colorAry[nodeID]=newColor;
                        numUncolor--;
                    }
                }
                //step 4
                nodeID++;
            }
            //step 6
            printAry(deBugFile);
        }
        //step 8
        printAry(outFile);
        deBugFile<<"leaving method 1\n";
    }

    void method2(ofstream &outFile,ofstream &deBugFile){
        //step 0
        deBugFile<<"entering method 2\n";
        int lastUsedColor=64;
        int nextNodeID=0;

        //step 7
        while(nextNodeID<numNodes){
            //step 1
            nextNodeID++;

            //step 2
            int nextUsedColor=64+1;
            bool coloredFlag=false;

            //step 4
            while(coloredFlag==false&&nextUsedColor<=lastUsedColor){
                //step 3
                if(lastUsedColor>64&&checkNeighbors(nextNodeID,nextUsedColor)==true){
                    colorAry[nextNodeID]=nextUsedColor;
                    coloredFlag=true;
                }
                else{
                    nextUsedColor++;
                }
            }
            //step 5
            if(coloredFlag==false){
                lastUsedColor++;
                colorAry[nextNodeID]=lastUsedColor;
                deBugFile<<"lastUsedColor: "<<lastUsedColor<<"\n";
            }
            //step 6
            printAry(deBugFile);
        }
        //step 8
        printAry(outFile);
        deBugFile<<"leaving method 2\n";
    }

    bool checkNeighbors(int nodeID,int color){
        node* nextNode=hashTable[nodeID];
        //step 4
        while(nextNode!=NULL){
            //step 1
            if(nextNode==NULL){
                return true;
            }
            //step 2
            if(colorAry[nextNode->ID]==color){
                return false;
            }
            //step 3
            nextNode=nextNode->next;
        }
        //step 5
        return true;
    }

    void printAry(ofstream &outFile){
        outFile<<"colorAry[]: \n";
        for(int i=1;i<=numNodes;i++){
            outFile<<i<<" ";
            if(colorAry[i]==0){
                outFile<<"0\n";
            }
            else{
                outFile<<char(colorAry[i])<<"\n";
            }
        }
    }
};

int main(int argc,char** argv){
    //step 0
    ifstream inFile(argv[1]);
    ofstream outFile(argv[3]);
    ofstream deBugFile(argv[4]);

    colorGraph* cG=new colorGraph(inFile);

    //step 1
    cG->loadGraph(inFile);

    //step 2
    cG->printHashTable(deBugFile);

    //step 3
    int whichMethod=stoi(argv[2]);

    outFile<<"Method "<<whichMethod<<" was used to color the graph\n";
    outFile<<"Below is the result of the color assignments\n";
    outFile<<"numNodes: "<<cG->numNodes<<"\n";
    //step 4
    if(whichMethod==1){
        cG->method1(outFile,deBugFile);
    }
    else if(whichMethod==2){
        cG->method2(outFile,deBugFile);
    }
    else{
        cout<<"argv[2] only accepts 1 or 2";
    }
    //step 5
    inFile.close();
    outFile.close();
    deBugFile.close();
}
