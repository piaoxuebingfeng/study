#include <stdio.h>  
#include "queue_list.h"
int main()  
{  
  int n, num, m;  
  int i;  
  Queue Q = CreateQueue();  
  printf( "Initialization complete.\n" );  
  if ( IsEmpty( Q ) )  
    printf( "Queue is empty \n" );  
  printf( "Please input the number of elements in the queue:" );  
  scanf( "%d", &n );  
  printf( "Please input %d elements put in queue:", n );  
  for(i = 0; i < n; i++ )  
  {  
    scanf( "%d", &num );  
    Enqueue( num, Q );  
  }  
  printf( "Front element: %d.\n", Front( Q ) );  
  printf( "Elements in queue:" );  
  while ( !IsEmpty( Q )) {  
    printf( "%3d", FrontAndDequeue( Q ) );  
  }  
  DisposeQueue( Q );  
  printf( "\n" );  
  return 0;  
}  