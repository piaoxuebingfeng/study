#include<stdio.h>  
#include<stdlib.h>  
#include<netinet/in.h>  
#include<netdb.h> 
int main(int argc,char **argv){  
    char *ptr,**pptr;  
    struct hostent *hptr;  
    char addr[32];  
  
    if(argc>1){  
        if((hptr=gethostbyname(argv[1]))== NULL){  
            printf("gethostbyname error!:%s\n",argv[1]);  
            exit(1);  
        }  
        printf("h_name: %s\n",hptr->h_name);  
        for(pptr=hptr->h_aliases;*pptr!=NULL;pptr++){  
            printf("alias: %s\n",*pptr);  
        }    
        for(pptr=hptr->h_addr_list;*pptr!=NULL;pptr++){  
            inet_ntop(hptr->h_addrtype,*pptr,addr,sizeof(addr));  
            printf("addr :%s \n",addr);  
        }  
  
    }  
  
}  