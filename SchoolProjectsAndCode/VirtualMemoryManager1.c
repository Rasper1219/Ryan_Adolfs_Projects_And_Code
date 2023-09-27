/*
    Ryan Adolfs
    CWID: 12104169
	Xiaoyan Hong
	CS300
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
/*------TODO---------
Structure:
  Read file containing logical addresses
  Use TLB and Page Table
    Translate logical address to physical address
Read in
  File contains 32-bit ints
  Mask the right most 16 bits
    Divided into 8 bit page number (8-15)
                 8 bit offset (0-7)
Page Table - 2^8 entries
 
TLB - 16 Entries
Page Size - 2^8 bytes
Frame Size - 2^8 bytes
Physical memory - 256 frames x 256 frames
1. Write program that extracts page number and offset from following ints:
1, 256, 32768, 32769, 128, 65534, 33153
2. Bypass TLB and implement using page table
3.  Implement TLB, must have replacement strategy (FIFO or LRU)
--------TODO----------*/


const int PAGE_TABLE_SIZE = 256;
const int BUFFER_SIZE = 256;
const int PHYS_MEM_SIZE = 256;
const int TLB_SIZE = 16;

struct TLB {
	unsigned char TLBpage[16];
	unsigned char TLBframe[16];
	int ind;
};
	









int findPage(int logAddy, char* PT, struct TLB *tlb,  char* PM, int* OF, int* pageFaults, int* TLBhits){

	unsigned char mask = 0xFF;
	unsigned char offset;
	unsigned char pageNum;
	bool TLBhit = false;
	int frame = 0;
	int val;
	int newFrame = 0;

	printf("Virtual address: %d\t", logAddy);

	pageNum = (logAddy >> 8) & mask;

	offset = logAddy & mask;
	
	//Check if in TLB
	int i = 0;
	for (i; i < TLB_SIZE; i++){
		if(tlb->TLBpage[i] == pageNum){
			frame = tlb->TLBframe[i];
			TLBhit = true;
			(*TLBhits)++;
		//	printf("TLBhit\t\t");
		}
			
	}

	//Check if in PageTable
	if (TLBhit == false){
		if (PT[pageNum] != -1){
		//	printf("Pagehit\t\t");
		}
		else{
			newFrame = readDisk(pageNum, PM, OF);
			PT[pageNum] = newFrame;
			(*pageFaults)++;
		}
		frame = PT[pageNum];
		tlb->TLBpage[tlb->ind] = pageNum;
		tlb->TLBframe[tlb->ind] = PT[pageNum];
		tlb->ind = (tlb->ind + 1)%TLB_SIZE;
		
	}
	int index = ((unsigned char)frame*PHYS_MEM_SIZE)+offset;
	val = *(PM+index);
	printf("Physical address: %d\t Value: %d\n",index, val);	

	
	return 0;


}

int readDisk (int pageNum, char *PM, int* OF){

	char buffer[BUFFER_SIZE];
	memset(buffer, 0, sizeof(buffer));

	FILE *BS;
	BS = fopen("BACKING_STORE.bin", "rb");
	if (BS == NULL){
		printf("File failed to open\n");
		exit(0);
	}
	

	if (fseek(BS, pageNum * PHYS_MEM_SIZE, SEEK_SET)!=0)
		printf("error in fseek\n");

	if (fread(buffer, sizeof(char), PHYS_MEM_SIZE, BS)==0)
		printf("error in fread\n");
	

	int i = 0;
	for(i; i < PHYS_MEM_SIZE; i++){
		*((PM+(*OF)*PHYS_MEM_SIZE)+i) = buffer[i];
	}
	
	(*OF)++;

	return (*OF)-1;

}


int main (int argc, char* argv[]){
	
	int val;
	FILE *fd;
	int openFrame = 0;

	int pageFaults = 0;
	int TLBhits = 0;
	int inputCount = 0;
	
	float pageFaultRate;
	float TLBHitRate;

	unsigned char PageTable[PAGE_TABLE_SIZE];
	memset(PageTable, -1, sizeof(PageTable));	

	struct TLB tlb;	
	memset(tlb.TLBpage, -1, sizeof(tlb.TLBpage));
	memset(tlb.TLBframe, -1, sizeof(tlb.TLBframe));
	tlb.ind = 0;

	char PhyMem[PHYS_MEM_SIZE][PHYS_MEM_SIZE]; 

	if (argc < 2){
		printf("Not enough arguments\nProgram Exiting\n");
		exit(0);
	}

	fd = fopen(argv[1], "r");
	if (fd == NULL){
		printf("File failed to open\n");
		exit(0);
	}

	while (fscanf(fd, "%d", &val)==1){
		findPage(val, PageTable, &tlb, (char*)PhyMem, &openFrame, &pageFaults, &TLBhits);
		inputCount++;
	}

	pageFaultRate = (float)pageFaults / (float)inputCount;
	TLBHitRate = (float)TLBhits / (float)inputCount;
	printf("Number of Translated Addresses = %d\n", inputCount);
	printf("Page Faults = %d\nPage Fault Rate = %.3f\nTLB Hits = %d\nTLB hit rate= %.3f\n",pageFaults, pageFaultRate, TLBhits, TLBHitRate);
	pclose(fd);
	return 0;

}