#include <stdio.h>
#include <pthread.h>
#include <sys/time.h>

void hello_thread1(void)
{
	while(1)
	{
		printf("I'm thread1\n");
		sleep(1);
	}
}

void hello_thread2(void)
{
	while(1)
	{
		printf("I'm thread2\n");
		sleep(2);
	}
}

int main(void)
{
	pthread_t thread_1,thread_2;
	
	//创建线程
	pthread_create(&thread_1,NULL,(void *)hello_thread1, NULL); 
	
	pthread_create(&thread_2,NULL,(void *)hello_thread2, NULL); 
	
	
	pthread_join(thread_1,NULL);
	pthread_join(thread_2,NULL);
	return 0;
}
