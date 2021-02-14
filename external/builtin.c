#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>
void _gbl_print(const char* str){
    printf("%s",str);
}

void _gbl_println(const char* str){
    printf("%s\n",str);
}

void _gbl_printInt(int n){
    printf("%d",n);
}

void _gbl_printlnInt(int n){
    printf("%d\n",n);
}

const char* _gbl_getString(){
    char* buf=malloc(sizeof(char)*1024);
    scanf("%s",buf);
    return buf;
}

int _gbl_getInt(){
    int buf;
    scanf("%d",&buf);
    return buf;
}

const char* _gbl_toString(int i){
    char* buf=malloc(sizeof(char)*16);
    sprintf(buf,"%d",i);
    return buf;
}

int _str_length(const char* str){
    return strlen(str);
}

const char* _str_substring(const char* str,int left,int right){
    char* buf=malloc(sizeof(char)*(right-left+1));
    memcpy(buf,str+left,right-left);
    buf[right-left]='\0';
    return buf;
}

int _str_parseInt(const char* str){
    int ret;
    sscanf(str,"%d",&ret);
    return ret;
}

const char* _str_concat(const char* lhs,const char* rhs){
    char* buf=malloc(sizeof(char)*1024);
    strcpy(buf,lhs);
    strcat(buf,rhs);
    return buf;
}

int _str_ord(const char* str,int pos){
    return str[pos];
}

bool _str_eq(const char* lhs,const char* rhs){
    return strcmp(lhs,rhs)==0;
}
bool _str_ne(const char* lhs,const char* rhs){
    return strcmp(lhs,rhs)!=0;
}
bool _str_lt(const char* lhs,const char* rhs){
    return strcmp(lhs,rhs)<0;
}
bool _str_le(const char* lhs,const char* rhs){
    return strcmp(lhs,rhs)<=0;
}
bool _str_gt(const char* lhs,const char* rhs){
    return strcmp(lhs,rhs)>0;
}
bool _str_ge(const char* lhs,const char* rhs){
    return strcmp(lhs,rhs)>=0;
}


