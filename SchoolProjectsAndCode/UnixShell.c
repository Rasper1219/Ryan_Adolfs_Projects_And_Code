#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/wait.h>

#define MAX_LINE 80 /* The maximum length command */
#define HISTORY_LENGTH 10

char history[HISTORY_LENGTH][MAX_LINE];
int history_index = 0;

void add_history(char *command) {
    strcpy(history[history_index], command);
    history_index = (history_index + 1) % HISTORY_LENGTH;
}

void print_history() {
    int i, j;
    i = history_index;
    for(j = 1; j <= HISTORY_LENGTH; j++) {
        i = (i - 1 + HISTORY_LENGTH) % HISTORY_LENGTH;
        if(history[i][0] != '\0') {
            printf("%d %s\n", j, history[i]);
        }
    }
}

void execute_command(char *command) {
    char *args[MAX_LINE/2 + 1];
    int background = 0;
    int i = 0;
    char *token = strtok(command, " \t\n");
    while(token != NULL) {
        args[i] = token;
        i++;
        token = strtok(NULL, " \t\n");
    }
    args[i] = NULL;
    if(i > 0 && strcmp(args[i - 1], "&") == 0) {
        background = 1;
        args[i - 1] = NULL;
    }
    int pid = fork();
    if(pid < 0) {
        fprintf(stderr, "Fork failed.\n");
        exit(1);
    }
    else if(pid == 0) {
        execvp(args[0], args);
        fprintf(stderr, "Command not found.\n");
        exit(1);
    }
    else {
        if(!background) {
            int status;
            waitpid(pid, &status, 0);
        }
    }
}

int main(void) {
    char command[MAX_LINE];
    int should_run = 1; /* flag to determine when to exit program */
    while (should_run) {
        printf("osh>");
        fflush(stdout);
        if(fgets(command, MAX_LINE, stdin) == NULL) {
            exit(0);
        }
        if(strcmp(command, "exit\n") == 0) {
            should_run = 0;
        }
        else if(strcmp(command, "history\n") == 0) {
            print_history();
        }
        else if (strncmp(command, "!!", 2) == 0) { //WORK on
            execute_command(history[0]);
        }
        else if(strncmp(command, "!", 1) == 0) {
            int number = atoi(command + 1);
            if(number > HISTORY_LENGTH || number <= 0) {
                printf("No such command in history.\n");
            }
            else {
                number = (history_index - number + HISTORY_LENGTH) % HISTORY_LENGTH;
                execute_command(history[number]);
            }
        }
        else {
            add_history(command);
            execute_command(command);
        }
    }
    return 0;
}
