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

set_mode