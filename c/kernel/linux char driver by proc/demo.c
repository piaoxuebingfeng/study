/*
 * This file is subject to the terms and conditions of the GNU General Public
 * License.  See the file "COPYING" in the main directory of this archive
 * for more details.
 *
 * Copyright (C) 2007, 2010 fengGuojin(fgjnew@163.com)
 */


#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/proc_fs.h>
#include <linux/string.h>
#include <linux/vmalloc.h>
#include <asm/uaccess.h>

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("PROC FILE DEMO");

#define MAX_DEMO_LENGTH   1024
static struct proc_dir_entry *proc_entry;

static char *DEMO_buffer;
static int DEMO_index;
static int next_demo;

int demo_read( char *page, char **start, off_t off,
                   int count, int *eof, void *data )
{
  int len;

  if (off > 0) {
    *eof = 1;
    return 0;
  }

  if (next_demo >= DEMO_index) next_demo = 0;

  len = sprintf(page, "%s\n", &DEMO_buffer[next_demo]);
  
  next_demo += len;

  return len;
}

ssize_t demo_write( struct file *filp, const char __user *buff,
                        unsigned long len, void *data )
{
  int space_available = (MAX_DEMO_LENGTH-DEMO_index)+1;

  if (len > space_available) {

    printk(KERN_INFO "demo: DEMO buffer is full!\n");
    return -ENOSPC;
  }

  if (copy_from_user( &DEMO_buffer[DEMO_index], buff, len )) {
    return -EFAULT;
  }

  DEMO_index += len;
  DEMO_buffer[DEMO_index-1] = 0;

  return len;
}


int init_demo_module( void )
{
  int ret = 0;

  DEMO_buffer = (char *)vmalloc( MAX_DEMO_LENGTH );

  if (!DEMO_buffer) {
    ret = -ENOMEM;
  } else {

    memset( DEMO_buffer, 0, MAX_DEMO_LENGTH );

    proc_entry = create_proc_entry( "demo", 0644, NULL );

    if (proc_entry == NULL)
     {
      ret = -ENOMEM;
      vfree(DEMO_buffer);
      printk(KERN_INFO "demo: Couldn't create proc entry\n");

    } 
    else
    {
      DEMO_index = 0;
      next_demo = 0;

      proc_entry->read_proc = demo_read;
      proc_entry->write_proc = demo_write;
      proc_entry->owner = THIS_MODULE;
      printk(KERN_INFO "demo: Module loaded.\n");
    }
  }
  return ret;
}


void cleanup_demo_module( void )
{
  remove_proc_entry("demo", &proc_root);
  vfree(DEMO_buffer);
  printk(KERN_INFO "demo: Module unloaded.\n");
}


module_init( init_demo_module );
module_exit( cleanup_demo_module );

