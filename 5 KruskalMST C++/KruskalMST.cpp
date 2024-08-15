#include<iostream>
#include<string>
#include<fstream>
#include<vector>

using namespace std;

class edge{
    public:
    int nU;
    int nW;
    int cost;
    edge* next;

    edge(int n1,int n2, int c){
        nU=n1;
        nW=n2;
        cost=c;
        next=NULL;
    }

    void printEdge(edge* edge,ofstream &outFile){
        outFile<<"("<<edge->nU<<","<<edge->nW<<","<<edge->cost<<")->";
    }
};

class KruskalMST{
    public:
    int N;
    vector<int> whichSet;
    int numSets;
    int totalMSTCost;
    edge* edgeList;
    edge* mstList;

    KruskalMST(){ 
    }

    void insertEdge(edge* listHead,edge* newEdge,ofstream &deBugFile){
        deBugFile<<"In insertEdge() method\n";
        edge* spot=findSpot(listHead,newEdge,deBugFile);
        newEdge->next=spot->next;
        spot->next=newEdge;
    }

    edge* findSpot(edge* listHead,edge* newEdge,ofstream &deBugFile){
        deBugFile<<"In findSpot() method\n";
        edge* spot=listHead;
        while(spot->next!=NULL&&spot->next->cost<newEdge->cost){
            spot=spot->next;
        }
        deBugFile<<"Spot is ";
        spot->printEdge(spot,deBugFile);
        deBugFile<<"\n";
        return spot;
    }

    edge* removeEdge(edge* listHead,ofstream &deBugFile){
        deBugFile<<"In removeEdge() method\n";
        if(listHead->next==NULL){
            return listHead;
        }
        else{
            edge* temp=listHead->next;
            listHead->next=temp->next;
            temp->next=NULL;
            return temp;
        }
    }

    void merge2Sets(int Ni,int Nj){
        if(whichSet[Ni]<whichSet[Nj]){
            updateWhichSet(whichSet[Nj],whichSet[Ni]);
        }
        else{
            updateWhichSet(whichSet[Ni],whichSet[Nj]);
        }
    }
    
    void updateWhichSet(int a,int b){
        for(int i=0;i<N+1;i++){
            if(whichSet[i]==a){
                whichSet[i]=b;
            }
        }
    }

    void printAry(vector<int> set,ofstream &deBugFile){
        for(int i=1;i<set.size();i++){
            deBugFile<<set.at(i)<<" ";
        }
        deBugFile<<"\n";
    }

    void printList(edge* listHead,ofstream &outFile){
        edge* spot=listHead;
        outFile<<"listHead-> ";
        while(spot!=NULL){
            outFile<<"<"<<spot->nU<<","<<spot->nW<<","<<spot->cost<<"> -> ";
            spot=spot->next;
        }
        outFile<<"NULL\n";
    }
};

int main(int argc,char**argv){
    //step 0
    ifstream inFile(argv[1]);
    ofstream outFile(argv[2]);
    ofstream deBugFile(argv[3]);
    KruskalMST* kmst=new KruskalMST();
    inFile>>kmst->N;
    kmst->numSets=kmst->N;
    for(int i=0;i<kmst->numSets+1;i++){
        kmst->whichSet.push_back(i);
    }
    kmst->edgeList=new edge(0,0,0);
    kmst->mstList=new edge(0,0,0);
    kmst->totalMSTCost=0;
    deBugFile<<"***Printing the input graph***\n";

    int u;
    int w;
    int cost;
    edge* newEdge;

    //step 4
    while(inFile>>u&&inFile>>w&&inFile>>cost){
        //step 1
        newEdge=new edge(u,w,cost);
        deBugFile<<"newEdge from inFile is: ";
        newEdge->printEdge(newEdge,deBugFile);
        deBugFile<<"\n";

        //step 2
        kmst->insertEdge(kmst->edgeList,newEdge,deBugFile);
        deBugFile<<"Printing edgeList after inserting the new edge: ";

        //step 3
        kmst->printList(kmst->edgeList,deBugFile);
    }
    deBugFile<<"***At the end of printing all edges of the input graph***\n";

    //step 9
    while(kmst->numSets>1){
        //step 5
        edge* nextEdge=kmst->removeEdge(kmst->edgeList,deBugFile);

        //step 6
        while(kmst->whichSet[nextEdge->nU]==kmst->whichSet[nextEdge->nW]){
            nextEdge=kmst->removeEdge(kmst->edgeList->next,deBugFile);
        }

        //step 7
        deBugFile<<"the nextEdge is: ";
        nextEdge->printEdge(nextEdge,deBugFile);
        deBugFile<<"\n";

        kmst->insertEdge(kmst->mstList,nextEdge,deBugFile);
        kmst->totalMSTCost+=nextEdge->cost;
        kmst->merge2Sets(nextEdge->nU,nextEdge->nW);
        kmst->numSets--;
        deBugFile<<"numSets is: "<<kmst->numSets<<"\n";

        //step 8
        deBugFile<<"Printing whichSet array: ";
        kmst->printAry(kmst->whichSet,deBugFile);
        deBugFile<<"Printing the remaining of edgeList: ";
        kmst->printList(kmst->edgeList,deBugFile);
        deBugFile<<"Print the growing MST list ";
        kmst->printList(kmst->mstList,deBugFile);
    }

    //step 10
    kmst->printList(kmst->mstList,outFile);
    outFile<<"Total MST cost: " <<kmst->totalMSTCost<<"\n";

    //step 11
    inFile.close();
    outFile.close();
    deBugFile.close();
    return 0;
}
