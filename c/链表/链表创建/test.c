#include<stdio.h>
#include<stdlib.h>

//��������ṹ��
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
		scanf("%d",&p->data);//ͷ������ݳ�Ա
		
		while(p->data != 0)
		{
			p1 = p;
			p = (struct node *)malloc(sizeof(struct node));
			scanf("%d",&p->data);
			p1->pNext=p;
		}
		p->pNext =NULL;  //����β��ָ��NULL
		
		//��ʾ��������
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
