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

typedef int* int_ptr_t;
int_ptr_t GC_control_root,
    GC_static_start,
    GC_control_start, GC_control_end,
    mem_0_start, mem_1_start, mem_1_end,
    mem_cursor_in_alloc, mem_cursor_in_gc;
int current_state = 0;

char* __private_malloc(int size) {
    int_ptr_t retval = mem_cursor_in_alloc;
    mem_cursor_in_alloc += size;
    return retval;
}

void __private_gc_run() {
    if (current_state == 0) {
        scan =
    }
}

void _gbl_gc_init(int size) {
    GC_control_root = (int*)malloc(size);
    GC_control_end = GC_control_start;
    // 8 KiB control region
    mem_0_start = GC_control_start + 8 * 1024;
    mem_1_start = GC_control_start + (size / 2 + 4) * 1024;
    mem_1_end = GC_control_start + size * 1024;
}

void _gbl_gc_reclaim() {
    free(GC_control_root);
}

#define IS_ARRAY(x) ((x)&0x80000000)
#define STRUCT_LEN(x) (((x)&0x3ffff000) >> 12)
#define IS_4BIT(x) ((x)&0x40000000)
#define ARRAY_ELE_LEN(x) ((x)&0x3fffffff)
#define MARKED(x) ((x)&0x40000000)
int __private_get_len(int* addr) {
    if (IS_ARRAY(*addr)) {
        return STRUCT_LEN(*addr);
    } else {
        return IS_4BIT(*addr) ? (ARRAY_ELE_LEN(*addr) << 2) : ARRAY_ELE_LEN(*addr);
    }
}

char* __private_move(char* addr) {
    char* meta_addr = addr - 4;
    if (MARKED(*meta_addr))
        return (char*)(meta_addr + 1);
    int size = __private_get_len(meta_addr) + 4; // size with metadata
    char* retval = mem_cursor_in_gc;
    memcpy(mem_cursor_in_gc, meta_addr, size); // move
    *meta_addr = 0x80;  // mark
    *((int*)(meta_addr + 1)) = retval; // save new address
    return retval;
}

char* _gbl_malloc_gcVer(int size) {
}

void _gbl_gc_static_hint() {
    asm volatile(
        "    lui t0,%hi(GC_static_start)\n"
        "    lw t0,%lo(GC_static_start)(t0)\n"
        "    slli t1,a0,2\n"
        "    addi t1,t0,t1\n"
        "    lui t2,%hi(GC_control_start)\n"
        "    sw t1,%lo(GC_control_start)(t2)\n"
        "    lui t3,%hi(GC_control_end)\n"
        "    sw t1,%lo(GC_control_end)(t3)\n"
        "\n"
        "    bne t0,t1,label1\n"
        "    j after_label\n"
        "label1:\n"
        "    sw a1,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label2\n"
        "    j after_label\n"
        "label2:\n"
        "    sw a2,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label3\n"
        "    j after_label\n"
        "label3:\n"
        "    sw a3,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label4\n"
        "    j after_label\n"
        "label4:\n"
        "    sw a4,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label5\n"
        "    j after_label\n"
        "label5:\n"
        "    sw a5,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label6\n"
        "    j after_label\n"
        "label6:\n"
        "    sw a6,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label7\n"
        "    j after_label\n"
        "label7:\n"
        "    sw a7,t0\n"
        "    addi t0,t0,4\n"
        "    add t2,sp,a0\n"
        "br_label:\n"
        "    beq t0,t1,after_label\n"
        "    lw t3,t2\n"
        "    sw t3,t1\n"
        "    addi t2,t2,4\n"
        "    addi t0,t0,4\n"
        "after_label:\n"
        :
        :
        :)
        GC_control_end = GC_control_start;
}

void _gbl_gc_hint() {
    asm volatile(
        "    lui t0,%hi(GC_control_end)\n"
        "    lw t0,%lo(GC_control_end)(t0)\n"
        "    slli t1,a0,2\n"
        "    addi t1,t0,t1\n"
        "    lui t2,%hi(GC_control_end)\n"
        "    sw t1,%lo(GC_control_end)(t2)\n"
        "    bne t0,t1,label1\n"
        "    ret\n"
        "label1:\n"
        "    sw a1,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label2\n"
        "    ret\n"
        "label2:\n"
        "    sw a2,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label3\n"
        "    ret\n"
        "label3:\n"
        "    sw a3,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label4\n"
        "    ret\n"
        "label4:\n"
        "    sw a4,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label5\n"
        "    ret\n"
        "label5:\n"
        "    sw a5,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label6\n"
        "    ret\n"
        "label6:\n"
        "    sw a6,t0\n"
        "    addi t0,t0,4\n"
        "    bne t0,t1,label7\n"
        "    ret\n"
        "label7:\n"
        "    sw a7,t0\n"
        "    addi t0,t0,4\n"
        "    add t2,sp,a0\n"
        "br_label:\n"
        "    beq t0,t1,after_label\n"
        "    lw t3,t2\n"
        "    sw t3,t1\n"
        "    addi t2,t2,4\n"
        "    addi t0,t0,4\n"
        "after_label:\n"
        "    ret\n"
        "    \n"
        :
        :
        :);
}

void _gbl_gc_unhint(int num) {
    GC_control_end -= 4 * num;
}

char* _gbl_gc_struct_malloc(int size, int pointerCount) {
    char* memory = __private_malloc(size + 4);
    int metadata = pointerCount & 0xfff;
    metadata |= (size << 12);
    *((int*)memory) = metadata;
    return memory + 4;
}

char* __gbl_gc_array_malloc(int length, int elementSize) {
    char* memory = __private_malloc(length * elementSize + 4);
    int metadata = elementSize | 0x80000000;
    if (elementSize == 4)
        metadata |= 0x40000000;
    else
        metadata &= 0xbfffffff;
    *((int*)memory) = metadata;
    return memory + 4;
}
