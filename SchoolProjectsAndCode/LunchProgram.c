/*
    Ryan Adolfs
    CWID: 12104169
    Professor: Xiaoyan Hong
    Course: CS 300 - Operating Systems
    Project Lunch
*/
#include <stdlib.h>
#include <stdio.h>
#include "mytime.h"
#include "mytime.c"
#include <unistd.h>
#include <semaphore.h>
#include <time.h>
#include <limits.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <pthread.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

#define MAXBUFFER 50 


int tickBuff[MAXBUFFER];
int ticket_counter = 1; //represents the smallest int that has not been used yet
int count = 0;
int semingtonVal;
int order = 0;
int C_Curr = 1; //current customer
int i;
sem_t ServerSem; 
sem_t semington;
sem_t semington2;
sem_t semington3;
pthread_mutex_t lock;

typedef struct {
    int tick_num;
    pthread_t threadID;
    sem_t customers;
    sem_t servers;
} lunch;

typedef struct {
    int tick_num;
    pthread_t threadID;
} Customer;

Customer customerArray[MAXBUFFER]; //customers being served
Customer w_customer_array[MAXBUFFER]; //customers waiting to be served


void *customer(void *arg);
void *server(void *arg);
void Show_serving(int number);
int lunch_get_ticket(lunch *lunch);
void lunch_init(lunch *lunch);
void lunch_wait_turn(lunch *lunch);
void lunch_wait_customer(lunch *lunch);



int main(int argc, char *argv[]) {

    srand(time(NULL));
    if (argc != 3) {
        printf("Try again. Correct format: %s <# of servers> <# of customers>\n", argv[0]);
        return 0;
    }

    
    //declare stuff
    int numServers = atoi(argv[1]);  
    int numCustomers = atoi(argv[2]);
    pthread_t customerIDs[numCustomers];
    pthread_t serverIDs[numServers];
    lunch Lunch;
    lunch_init(&Lunch); // initalize to no customers and no servers
    sem_init(&semington, 0, 1);
    sem_init(&semington2, 0, 1);
    sem_init(&semington3, 0, 0);
    sem_init(&ServerSem, 0, numServers);

    
    //thread creates and joins
    for (i = 0; i < numServers; i++) {
        pthread_create(&serverIDs[i], NULL, server, (lunch *)&Lunch);
    }
    for (i = 0; i < numCustomers; i++) {
        pthread_create(&customerIDs[i], NULL, customer, &Lunch);
    }
    for (i = 0; i < numServers; i++) {
        pthread_join(serverIDs[i], NULL);
    }
    for (i = 0; i < numCustomers; i++) {
        pthread_join(customerIDs[i], NULL);
    }


    return 0;
}

//required function
void Show_serving(int number) {
    printf("Serving <%d>\n", number);
    sem_post(&semington3);
    return;
}

//initialize
void lunch_init(lunch *lunch) {
    sem_init(&lunch->customers, 0, 0);
    sem_init(&lunch->servers, 0, 1);
    return;
}

//make a customer (customer enters store)
void *customer(void *arg) {
    lunch *lunch = arg;
    lunch_get_ticket(arg);
    return NULL;
}

//make a server (server shows up and is ready to serve after sim sleep)
void *server(void *arg) {
    lunch *lunch = arg;
    lunch_wait_customer(lunch);
    return NULL;
}

//wait til server is ready
void lunch_wait_turn(lunch *lunch) {
    pthread_t ID = pthread_self();
    int tick_num;

    //find correct customer
    for (i = 0; i < MAXBUFFER; i++) {

        if (ID == customerArray[i].threadID) {

            tick_num = customerArray[i].tick_num;
            w_customer_array[order % MAXBUFFER].threadID = ID;
            w_customer_array[order % MAXBUFFER].tick_num = tick_num;
            order++;
            break;
        }
    }

    printf("<%lu> enter <lunch_wait_turn> with <%d>\n", ID, tick_num);
    sem_getvalue(&ServerSem, &semingtonVal);
    sem_wait(&ServerSem);
    sem_getvalue(&ServerSem, &semingtonVal);
    
    Show_serving(tick_num); //serve 

    sem_post(&semington);
    ID = pthread_self();

    printf("<%lu> leave <lunch_wait_turn> after ticket <%d> served\n", ID, tick_num);
    sem_post(&lunch->servers);
    return;
}

//wait until there is a customer needing to be served
void lunch_wait_customer(lunch *lunch) {
    printf("<%lu> enter <lunch_wait_customer>\n", pthread_self());
    int snoozington = mytime(0, 5);
    printf("Sleeping Time: %d sec; Thread Id = %lu\n", snoozington, pthread_self());
    sleep(snoozington);

    sem_getvalue(&lunch->customers, &semingtonVal);
    if (semingtonVal == 0) sem_wait(&lunch->customers);

    sem_getvalue(&semington, &semingtonVal);
    sem_wait(&semington2);
    sem_getvalue(&semington, &semingtonVal);
    sem_wait(&semington3);

    printf("<%lu> after served ticket <%d>\n", pthread_self(), C_Curr);
    pthread_mutex_lock(&lock);
    C_Curr++;
    pthread_mutex_unlock(&lock);
    sem_post(&semington2);

    printf("<%lu> leave <lunch_wait_customer>\n", pthread_self());
    sem_wait(&lunch->servers);
    sem_post(&lunch->servers);
    return;
}

//get customer's ticket number
int lunch_get_ticket(lunch *lunch) { 
    printf("<%lu> enter <lunch_get_ticket>\n", pthread_self());

    sem_wait(&semington);
    pthread_mutex_lock(&lock);
    int curr = ticket_counter;
    customerArray[count % MAXBUFFER].threadID = pthread_self();
    customerArray[count % MAXBUFFER].tick_num = curr;
    count++;

    pthread_mutex_unlock(&lock);
    pthread_mutex_lock(&lock);

    ticket_counter++;

    pthread_mutex_unlock(&lock);
    pthread_mutex_lock(&lock);
    pthread_mutex_unlock(&lock);
    sem_post(&lunch->customers);
    printf("<%lu> get ticket <%d>\n", pthread_self(), curr);

    int snoozington = mytime(0, 5); // snoozington = length of sleep
    printf("Sleeping Time: %d sec; Thread Id = %lu\n", snoozington, pthread_self());
    sleep(snoozington);
    
    lunch_wait_turn(lunch);

    sem_post(&semington);
    printf("<%lu> leave <lunch_get_ticket>\n", pthread_self());
    return curr;
}




