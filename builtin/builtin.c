#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
void _gbl_print(const char* str) {
    printf("%s", str);
}

void _gbl_println(const char* str) {
    printf("%s\n", str);
}

void _gbl_printInt(int n) {
    printf("%d", n);
}

void _gbl_printlnInt(int n) {
    printf("%d\n", n);
}

const char* _gbl_getString() {
    char* buf = malloc(sizeof(char) * 1024);
    scanf("%s", buf);
    return buf;
}

int _gbl_getInt() {
    int buf;
    scanf("%d", &buf);
    return buf;
}

const char* _gbl_toString(int i) {
    char* buf = malloc(sizeof(char) * 16);
    sprintf(buf, "%d", i);
    return buf;
}

char* _gbl_malloc(int size) {
    return malloc(size);
}

int _str_length(const char* str) {
    return strlen(str);
}

const char* _str_substring(const char* str, int left, int right) {
    char* buf = malloc(sizeof(char) * (right - left + 1));
    memcpy(buf, str + left, right - left);
    buf[right - left] = '\0';
    return buf;
}

int _str_parseInt(const char* str) {
    int ret;
    sscanf(str, "%d", &ret);
    return ret;
}

const char* _str_concat(const char* lhs, const char* rhs) {
    char* buf = malloc(sizeof(char) * 1024);
    strcpy(buf, lhs);
    strcat(buf, rhs);
    return buf;
}

int _str_ord(const char* str, int pos) {
    return str[pos];
}

bool _str_eq(const char* lhs, const char* rhs) {
    return strcmp(lhs, rhs) == 0;
}
bool _str_ne(const char* lhs, const char* rhs) {
    return strcmp(lhs, rhs) != 0;
}
bool _str_lt(const char* lhs, const char* rhs) {
    return strcmp(lhs, rhs) < 0;
}
bool _str_le(const char* lhs, const char* rhs) {
    return strcmp(lhs, rhs) <= 0;
}
bool _str_gt(const char* lhs, const char* rhs) {
    return strcmp(lhs, rhs) > 0;
}
bool _str_ge(const char* lhs, const char* rhs) {
    return strcmp(lhs, rhs) >= 0;
}

/*
 * GC functions
 */

typedef char* ptr_t;
ptr_t GC_control_root,
    mem_0_start, mem_1_start, mem_1_end,
    mem_cursor, new_region_start, new_region_end;
ptr_t GC_static_start, GC_control_start, GC_control_end;
int current_is_one = 0;

void _gbl_gc_init(int size) {
    GC_control_root = (ptr_t)malloc(size*1024);
    GC_control_start = GC_control_root;
    GC_control_end = GC_control_start;
    GC_static_start=GC_control_root;
    // 8 KiB control region
    mem_0_start = GC_control_root + 8 * 1024;
    mem_1_start = mem_0_start + ((size - 8) / 2) * 1024;
    mem_1_end = GC_control_root + size * 1024;
    mem_cursor=mem_0_start;
    current_is_one=0;
//    printf("DEBUG: gc init.\n");
//    printf("Current is %d, mem cursor: %d, mem_0_start:%d, mem_1_start:%d, mem_1_end:%d\n",1-current_is_one,mem_cursor,mem_0_start,mem_1_start,mem_1_end);
}

void _gbl_gc_reclaim() {
    free(GC_control_root);
}

#define SET_ZERO(x, bit) ((x) &= ~(1 << bit))
#define SET_ONE(x, bit) ((x) |= (1 << bit))
#define GET_BIT(x, bit) (((x) & (1 << bit)) >> bit)
#define IS_ARRAY(x) GET_BIT(*((int*)(x)), 31)
#define STRUCT_LEN(x) ((*((int*)(x)) & 0x3ffff000) >> 12)
#define IS_4BIT(x) GET_BIT(*((int*)(x)), 30)
#define CAN_DEREFERENCE(x) (*((int*)(x)) & 0x20000000)
#define ARRAY_ELE_LEN(x) (*((int*)(x)) & 0x1fffffff)
#define MARKED(x) ((*((int*)(x)) & 0xc0000000) == 0x40000000)
int __private_get_len(int* addr) {
    if (IS_ARRAY(addr)) {
        return STRUCT_LEN(addr);
    } else {
        return IS_4BIT(addr) ? (ARRAY_ELE_LEN(addr) << 2) : ARRAY_ELE_LEN(addr);
    }
}

bool __private_in_old_region(ptr_t addr) {
    if (current_is_one) {
        return addr >= mem_1_start && addr < mem_1_end;
    } else {
        return addr >= mem_0_start && addr < mem_1_start;
    }
}

ptr_t __private_move(ptr_t addr) {
    if(addr==0)
        return 0;
    if (!__private_in_old_region(addr))
        return addr;
    ptr_t meta_addr = addr - 4;
    if (MARKED(*((int*)meta_addr)))
        return (char*)(meta_addr + 1);
    int size = __private_get_len((int*)meta_addr) + 4;  // size with metadata
    ptr_t retval = new_region_end;
    memcpy(new_region_end, meta_addr, size);  // move
    new_region_end += size;
    *meta_addr = 0x80;                  // mark
    *((int*)(meta_addr + 1)) = (int)retval;  // save new address
    return retval;
}

void __private_scan_and_perform(int pointerSize) {
    int i = 0;
    for (ptr_t* ptr = (ptr_t*) (new_region_start + 4); i < pointerSize; ptr++, i++) {
        *ptr = __private_move(*ptr);
    }
    new_region_start += STRUCT_LEN(new_region_start) + 4;
}

void __private_gc_run() {
    new_region_start = current_is_one ? mem_0_start : mem_1_start;
    new_region_end = new_region_start;
    // static root
    for (ptr_t ptr = GC_static_start; ptr != GC_control_start; ptr += 4) {
        *((ptr_t*)ptr) = __private_move(*((ptr_t*)ptr));
    }
    // stack root
    for (ptr_t ptr = GC_control_start; ptr != GC_control_end; ptr += 8) {
        ptr_t sp = *((ptr_t*)ptr);
        int n = *((int*)(ptr + 4));
        for (int i = 0; i < n; ++i) {
            ptr_t* local_ptr = (ptr_t*)(sp - 4 * i);
            *local_ptr = __private_move(*local_ptr);
        }
    }
    // bfs
    while (new_region_start < new_region_end) {
        if (IS_ARRAY(new_region_start)) {
            if (CAN_DEREFERENCE(new_region_start)) {
                __private_scan_and_perform(*((int*)new_region_start) & 0x1fffffff);
            }
        } else {
            // struct
            __private_scan_and_perform(*((int*)new_region_start) & 0xfff);
        }
    }
    mem_cursor = new_region_end;
    current_is_one = 1 - current_is_one;
}

ptr_t __private_malloc(int size) {
    if (current_is_one) {
        if (mem_cursor + size + 4 >= mem_1_end)
            __private_gc_run();
    } else {
        if (mem_cursor + size + 4 >= mem_1_start)
            __private_gc_run();
    }
    ptr_t retval = mem_cursor;
    mem_cursor += size;
    return retval;
}

__attribute__ ((naked))
void _gbl_gc_static_hint() {
    asm volatile(
        "    lui t0,%%hi(GC_static_start)\n"
        "    lw t0,%%lo(GC_static_start)(t0)\n"
        "    slli t1,a0,2\n"
        "    add t1,t0,t1\n"
        "    lui t2,%%hi(GC_control_start)\n"
        "    sw t1,%%lo(GC_control_start)(t2)\n"
        "    lui t3,%%hi(GC_control_end)\n"
        "    sw t1,%%lo(GC_control_end)(t3)\n"
        "\n"
        "    bne t0,t1,.LB1\n"
        "    ret\n"
        ".LB1:\n"
        "    sw a1,0(t0)\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,.LB2\n"
        "    ret\n"
        ".LB2:\n"
        "    sw a2,0(t0)\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,.LB3\n"
        "    ret\n"
        ".LB3:\n"
        "    sw a3,0(t0)\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,.LB4\n"
        "    ret\n"
        ".LB4:\n"
        "    sw a4,0(t0)\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,.LB5\n"
        "    ret\n"
        ".LB5:\n"
        "    sw a5,0(t0)\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,.LB6\n"
        "    ret\n"
        ".LB6:\n"
        "    sw a6,0(t0)\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,.LB7\n"
        "    ret\n"
        ".LB7:\n"
        "    sw a7,0(t0)\n"
        "    addi t0,t0,4\n"
        "    add t2,sp,a0\n"
        ".BRLABEL:\n"
        "    bge t0,t1,.AFTERLABEL\n"
        "    lw t3,0(t2)\n"
        "    sw t3,0(t1)\n"
        "    addi t2,t2,4\n"
        "    addi t0,t0,4\n"
        "    j .BRLABEL\n"
        ".AFTERLABEL:\n"
        "    ret\n"
        :
        :
        :);
}

void _gbl_gc_hint(int* sp, int n) {
    *((int**)GC_control_start) = sp;
    *((int*)(GC_control_start + 4)) = n;
}

void _gbl_gc_unhint() {
    GC_control_end -= 8;
}

ptr_t _gbl_gc_struct_malloc(int size, int pointerCount) {
//    printf("DEBUG: gc run. Current is %d, mem cursor: %d, mem_1_start:%d, mem_1_end:%d\n",1-current_is_one,mem_cursor,mem_1_start,mem_1_end);
    ptr_t memory = __private_malloc(size + 4);
    int metadata = pointerCount & 0xfff;
    metadata |= (size << 12);
    *((int*)memory) = metadata;
    return memory + 4;
}

ptr_t _gbl_gc_array_malloc(int length, int elementSize, bool isPointer) {
    ptr_t memory = __private_malloc(length * elementSize + 4);
    int metadata = length| 0x80000000;
    if (elementSize == 4)
        SET_ONE(metadata, 30);
    else
        SET_ZERO(metadata, 30);
    if (isPointer)
        SET_ONE(metadata, 29);
    else
        SET_ZERO(metadata, 29);
    *((int*)memory) = metadata;
    return memory + 4;
}
