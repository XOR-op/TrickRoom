# Layout

## Struct

| | | | | | |
|:---:|:---:|:---:|:---:|:---:|:---:|
|isArray|marked|length of struct| count of pointers | pointer members| non-pointer members|
|1bit|1bit|18 bits|12 bits||
|0|0 | | |<-address of pointer||

## Array

| | | | | |
|:---:|:---:|:---:|:---:|:---:|
| isArray| is-4-bits|isPointer|count of elements | elements |
| 1bit |1bit|1bit|29 bits|||
|1 | 1 if 4-bits; 0 if 1-bit| 0 or 1| |<-address of pointer|

## Marked 
| | | | | |
|:---:|:---:|:---:|:---:|:---:|
|mark| no used |moved addr|moved addr|no used|
| 2bits| 6bits|24bits | 8bits| |
| 01| | |<-address of pointer| |