#include<iostream>
#include<string>
#include<fstream>
using namespace std;

class listNode{
    public:
    int data;
    listNode *next;
    listNode(int d){
        data=d;
        next=NULL;
    }
};

class LLQueue{
    public:
    listNode *head;
    listNode *tail;
    LLQueue(){
        head=tail=NULL;
    }

    void insertQ(LLQueue **table, int index,listNode *newNode){
        newNode->next=NULL;
        if(table[index]->isEmpty()){
            table[index]->head=newNode;
            table[index]->tail=newNode;
        }
        else{
            table[index]->tail->next=newNode;
            table[index]->tail=newNode;
        }
    }

    listNode *deleteQ(){
        if(isEmpty()){
            return head;
        }
        listNode *temp=head;
        head=head->next;
        if(head==NULL){
            tail=NULL;
        }
        return temp;
    }

    bool isEmpty(){
        return(head==NULL&&tail==NULL);
    }

    void printQueue(LLQueue **whichTable,int index, ofstream &outFile){
        outFile<<"\nqueueHead ("<<index<<")->";
        listNode *spot=whichTable[index]->head;
        while(spot->next!=NULL){
            outFile<<"("<<spot->data<<","<<spot->next->data<<")->";
            spot=spot->next;
        }
        outFile<<"NULL";
    }
};

class RadixSort{
    public:
    int tableSize=10;
    LLQueue *hashTable[2][10];
    int data;
    int currentTable;
    int previousTable;
    int maxDigits;
    int offSet;
    int digitPosition;
    int currentDigit;
    RadixSort(){
        for(int i=0;i<2;i++){
            for(int j=0;j<10;j++){
                LLQueue *queue=new LLQueue();
                hashTable[i][j]=queue;
            }
        }
    }

    void preProcessing(ifstream &inFile,ofstream &deBugFile){
        //step 0
        deBugFile<<"\n***Performing firstReading";
        int negativeNum=0;
        int positiveNum=0;
        
        //step 1
        while(inFile>>data){
            if(data<negativeNum){
                negativeNum=data;
            }
            else if(data>positiveNum){
                positiveNum=data;
            }
        }

        //step 3
        if(negativeNum<0){
            offSet=abs(negativeNum);
        }
        else{
            offSet=0;
        }

        //step 4
        positiveNum+=offSet;
        maxDigits=getLength(positiveNum);
        deBugFile<<"\nnegativeNum: "<<negativeNum<<"\npositiveNum: "<<positiveNum<<"\nmaxDigits: "<<maxDigits<<endl;
    }

    void RSort(ifstream &inFile,ofstream &outFile,ofstream &deBugFile){
        //step 0
        deBugFile<<"\n***Performing RSort";

        //step 1
        digitPosition=0;
        currentTable=0;

        //step 2 and 3
        while(inFile>>data){
            data+=offSet;
            listNode* newNode=new listNode(data);
            int hashIndex=getDigit(data,digitPosition);
            hashTable[currentTable][hashIndex]->insertQ(hashTable[currentTable],hashIndex,newNode);
        }

        //step 12
        while(digitPosition<maxDigits){
            //step 4
            digitPosition++;
            previousTable=currentTable;
            currentTable=(currentTable+1)%2;

            //step 5
            deBugFile<<"\ndigitPosition: "<<digitPosition<<"\ncurrentTable: "<<currentTable<<"\npreviousTable: "<<previousTable<<endl;
            outFile<<"\n***Printing hashTable["<<previousTable<<"]";

            //step 6
            printTable(hashTable[previousTable],outFile);

            //step 7
            int tableIndex=0;
            //step 11
            while(tableIndex<tableSize){
                //step 9
                while(!hashTable[previousTable][tableIndex]->isEmpty()){
                    //step 8
                    listNode *newNode=hashTable[previousTable][tableIndex]->deleteQ();
                    int hashIndex=getDigit(newNode->data,digitPosition);
                    hashTable[currentTable][hashIndex]->insertQ(hashTable[currentTable],hashIndex,newNode);
                }
                //step 10
                tableIndex++;
            }
        }
        //step 13
        printSortedData(hashTable[currentTable],outFile);
    }

    int getLength(int data){
        string str=to_string(data);
        int size=str.size();
        return size;
    }

    int getDigit(int data,int position){
        if(position==0){
            return data%10; 
        }

        else{
            data=data/10;
            position=position-1;
            return getDigit(data,position);
        }
        return 0;
    }

    void printTable(LLQueue **whichTable,ofstream &outFile){
        for(int i=0;i<10;i++){
            if(whichTable[i]->head!=NULL){
                whichTable[i]->printQueue(whichTable,i,outFile);
            }
        }
        outFile<<endl;
    }

    void printSortedData(LLQueue **whichTable,ofstream &outFile){
        outFile<<"Sorted Data: ";
        for(int i=0;i<10;i++){
            while(whichTable[i]->head!=NULL){
                if(whichTable[i]->head->next!=NULL){
                    outFile<<whichTable[i]->head->data-offSet<<", ";
                }
                else{
                    outFile<<whichTable[i]->head->data-offSet;
                }
                whichTable[i]->head=whichTable[i]->head->next;
            }
        }
    }
};

int main(int argc,char**argv){
    //step 0
    ifstream inFile(argv[1]);
    ofstream outFile(argv[2]);
    ofstream deBugFile(argv[3]);
    RadixSort *hashTable=new RadixSort();

    //step 1
    hashTable->preProcessing(inFile,deBugFile);

    //step 2
    inFile.close();
    inFile.open(argv[1]);

    //step 3
    hashTable->RSort(inFile,outFile,deBugFile);

    //step 4
    inFile.close();
    outFile.close();
    deBugFile.close();

    return 0;
}
