# Layout

## Struct

| | | | | | |
|:---:|:---:|:---:|:---:|:---:|:---:|
|isArray|marked|length of struct| count of pointers | pointer members| non-pointer members|
|1bit|1bit|18 bits|12 bits||
|0|0 | | |<-address of pointer||

## Array

| | | | |
|:---:|:---:|:---:|:---:|
| isArray| is-4-bits|count of elements | elements |
| 1bit |1bit|30 bits|||
|1 | 1 if 4-bits; 0 if 1-bit| |<-address of pointer|

## Marked 
| | | | |
|:---:|:---:|:---:|:---:|
|mark|moved addr(high 24 bits)|moved addr(low 8bits)|no used|
| 2bits| |<-address of pointer| |