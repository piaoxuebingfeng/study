#include<stdio.h>
#include<stdlib.h>

//单向链表结构体
struct node
{
	int data;
	struct node *pNext;
};

int main(void)
{
		struct node *p,*p1,*head;
		head=p=(struct node *)malloc(sizeof(struct node));
		printf("please input linked_list head date:");
		scanf("%d",&p->data);//头结点数据成员
		
		while(p->data != 0)
		{
			p1 = p;
			p = (struct node *)malloc(sizeof(struct node));
			scanf("%d",&p->data);
			p1->pNext=p;
		}
		p->pNext =NULL;  //链表尾部指向NULL
		
		//显示链表数据
		p=head;
		printf("linked_list data is :\n");
		while(p->pNext !=NULL)
		{
			printf("%d\n",p->data);
			p=p->pNext;
		}
		printf("%d\n",p->data);
		return 0;
}
