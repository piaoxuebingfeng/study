#! /bin/bash
set_mode()
{
	gpio mode 21 OUT;
	gpio mode 22 OUT;
	gpio mode 23 OUT;
	gpio mode 24 OUT;
	gpio mode 25 OUT;

	gpio mode 27 OUT;
	gpio mode 28 OUT;
	gpio mode 29 OUT;
	
	gpio write 21 0;
	gpio write 22 0;
	gpio write 23 0;
	gpio write 24 0;
	
	gpio write 25 0;
	gpio write 27 0;
	gpio write 28 0;
	gpio write 29 0;
}
set_go()
{v
	gpio write 21 1;
	gpio write 23 1;
	gpio write 25 1;
	gpio write 28 1;
	
	gpio write 22 0;
	gpio write 24 0;
	gpio write 27 0;
	gpio write 29 0;
}
set_back()
{
	gpio write 21 0;
	gpio write 23 0;
	gpio write 25 0;
	gpio write 28 0;
	
	gpio write 22 1;
	gpio write 24 1;
	gpio write 27 1;
	gpio write 29 1;
}

set_mode
set_go
sleep 3
set_back
sleep 3





