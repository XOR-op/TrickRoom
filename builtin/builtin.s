	.file	"todo.c"
	.option nopic
	.attribute arch, "rv32i2p0_m2p0_a2p0"
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	2
	.globl	_abort
	.type	_abort, @function
_abort:
	addi	sp,sp,-16
	sw	s0,12(sp)
	addi	s0,sp,16
	li	a5,0
	sw	zero,0(a5)
	nop
	lw	s0,12(sp)
	addi	sp,sp,16
	jr	ra
	.size	_abort, .-_abort
	.section	.rodata
	.align	2
.LC0:
	.string	"%s"
	.text
	.align	2
	.globl	_gbl_print
	.type	_gbl_print, @function
_gbl_print:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC0)
	addi	a0,a5,%lo(.LC0)
	call	printf
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_print, .-_gbl_print
	.align	2
	.globl	_gbl_println
	.type	_gbl_println, @function
_gbl_println:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a0,-20(s0)
	call	puts
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_println, .-_gbl_println
	.section	.rodata
	.align	2
.LC1:
	.string	"%d"
	.text
	.align	2
	.globl	_gbl_printInt
	.type	_gbl_printInt, @function
_gbl_printInt:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC1)
	addi	a0,a5,%lo(.LC1)
	call	printf
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_printInt, .-_gbl_printInt
	.section	.rodata
	.align	2
.LC2:
	.string	"%d\n"
	.text
	.align	2
	.globl	_gbl_printlnInt
	.type	_gbl_printlnInt, @function
_gbl_printlnInt:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC2)
	addi	a0,a5,%lo(.LC2)
	call	printf
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_printlnInt, .-_gbl_printlnInt
	.align	2
	.globl	_gbl_getString
	.type	_gbl_getString, @function
_gbl_getString:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	li	a0,1024
	call	_gbl_string_malloc
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC0)
	addi	a0,a5,%lo(.LC0)
	call	scanf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_getString, .-_gbl_getString
	.align	2
	.globl	_gbl_getInt
	.type	_gbl_getInt, @function
_gbl_getInt:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	addi	a5,s0,-20
	mv	a1,a5
	lui	a5,%hi(.LC1)
	addi	a0,a5,%lo(.LC1)
	call	scanf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_getInt, .-_gbl_getInt
	.align	2
	.globl	_gbl_toString
	.type	_gbl_toString, @function
_gbl_toString:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	li	a0,16
	call	_gbl_string_malloc
	sw	a0,-20(s0)
	lw	a2,-36(s0)
	lui	a5,%hi(.LC1)
	addi	a1,a5,%lo(.LC1)
	lw	a0,-20(s0)
	call	sprintf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	_gbl_toString, .-_gbl_toString
	.align	2
	.globl	_str_length
	.type	_str_length, @function
_str_length:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a0,-20(s0)
	call	strlen
	mv	a5,a0
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_length, .-_str_length
	.align	2
	.globl	_str_substring
	.type	_str_substring, @function
_str_substring:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	a1,-40(s0)
	sw	a2,-44(s0)
	lw	a4,-44(s0)
	lw	a5,-40(s0)
	sub	a5,a4,a5
	addi	a5,a5,1
	mv	a0,a5
	call	_gbl_string_malloc
	sw	a0,-20(s0)
	lw	a0,-36(s0)
	call	__private_recover_str_addr
	sw	a0,-36(s0)
	lw	a5,-40(s0)
	lw	a4,-36(s0)
	add	a3,a4,a5
	lw	a4,-44(s0)
	lw	a5,-40(s0)
	sub	a5,a4,a5
	mv	a2,a5
	mv	a1,a3
	lw	a0,-20(s0)
	call	memcpy
	lw	a4,-44(s0)
	lw	a5,-40(s0)
	sub	a5,a4,a5
	mv	a4,a5
	lw	a5,-20(s0)
	add	a5,a5,a4
	sb	zero,0(a5)
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	_str_substring, .-_str_substring
	.align	2
	.globl	_str_parseInt
	.type	_str_parseInt, @function
_str_parseInt:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	addi	a5,s0,-20
	mv	a2,a5
	lui	a5,%hi(.LC1)
	addi	a1,a5,%lo(.LC1)
	lw	a0,-36(s0)
	call	sscanf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	_str_parseInt, .-_str_parseInt
	.align	2
	.globl	_str_concat
	.type	_str_concat, @function
_str_concat:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	a1,-40(s0)
	li	a0,1024
	call	_gbl_string_malloc
	sw	a0,-20(s0)
	lw	a0,-36(s0)
	call	__private_recover_str_addr
	sw	a0,-36(s0)
	lw	a0,-40(s0)
	call	__private_recover_str_addr
	sw	a0,-40(s0)
	lw	a1,-36(s0)
	lw	a0,-20(s0)
	call	strcpy
	lw	a1,-40(s0)
	lw	a0,-20(s0)
	call	strcat
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	_str_concat, .-_str_concat
	.align	2
	.globl	_str_ord
	.type	_str_ord, @function
_str_ord:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a5,-24(s0)
	lw	a4,-20(s0)
	add	a5,a4,a5
	lbu	a5,0(a5)
	mv	a0,a5
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_ord, .-_str_ord
	.align	2
	.globl	_str_eq
	.type	_str_eq, @function
_str_eq:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	seqz	a5,a5
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_eq, .-_str_eq
	.align	2
	.globl	_str_ne
	.type	_str_ne, @function
_str_ne:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	snez	a5,a5
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_ne, .-_str_ne
	.align	2
	.globl	_str_lt
	.type	_str_lt, @function
_str_lt:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	srli	a5,a5,31
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_lt, .-_str_lt
	.align	2
	.globl	_str_le
	.type	_str_le, @function
_str_le:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	slti	a5,a5,1
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_le, .-_str_le
	.align	2
	.globl	_str_gt
	.type	_str_gt, @function
_str_gt:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	sgt	a5,a5,zero
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_gt, .-_str_gt
	.align	2
	.globl	_str_ge
	.type	_str_ge, @function
_str_ge:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	not	a5,a5
	srli	a5,a5,31
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_ge, .-_str_ge
	.globl	GC_control_root
	.section	.sbss,"aw",@nobits
	.align	2
	.type	GC_control_root, @object
	.size	GC_control_root, 4
GC_control_root:
	.zero	4
	.globl	mem_0_start
	.align	2
	.type	mem_0_start, @object
	.size	mem_0_start, 4
mem_0_start:
	.zero	4
	.globl	mem_1_start
	.align	2
	.type	mem_1_start, @object
	.size	mem_1_start, 4
mem_1_start:
	.zero	4
	.globl	mem_1_end
	.align	2
	.type	mem_1_end, @object
	.size	mem_1_end, 4
mem_1_end:
	.zero	4
	.globl	mem_cursor
	.align	2
	.type	mem_cursor, @object
	.size	mem_cursor, 4
mem_cursor:
	.zero	4
	.globl	new_region_start
	.align	2
	.type	new_region_start, @object
	.size	new_region_start, 4
new_region_start:
	.zero	4
	.globl	new_region_end
	.align	2
	.type	new_region_end, @object
	.size	new_region_end, 4
new_region_end:
	.zero	4
	.globl	GC_static_start
	.align	2
	.type	GC_static_start, @object
	.size	GC_static_start, 4
GC_static_start:
	.zero	4
	.globl	GC_control_start
	.align	2
	.type	GC_control_start, @object
	.size	GC_control_start, 4
GC_control_start:
	.zero	4
	.globl	GC_control_end
	.align	2
	.type	GC_control_end, @object
	.size	GC_control_end, 4
GC_control_end:
	.zero	4
	.globl	current_is_one
	.align	2
	.type	current_is_one, @object
	.size	current_is_one, 4
current_is_one:
	.zero	4
	.text
	.align	2
	.globl	_gbl_gc_init
	.type	_gbl_gc_init, @function
_gbl_gc_init:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a5,-20(s0)
	slli	a5,a5,10
	mv	a0,a5
	call	malloc
	mv	a5,a0
	mv	a4,a5
	lui	a5,%hi(GC_control_root)
	sw	a4,%lo(GC_control_root)(a5)
	lui	a5,%hi(GC_control_root)
	lw	a4,%lo(GC_control_root)(a5)
	lui	a5,%hi(GC_control_start)
	sw	a4,%lo(GC_control_start)(a5)
	lui	a5,%hi(GC_control_start)
	lw	a4,%lo(GC_control_start)(a5)
	lui	a5,%hi(GC_control_end)
	sw	a4,%lo(GC_control_end)(a5)
	lui	a5,%hi(GC_control_root)
	lw	a4,%lo(GC_control_root)(a5)
	lui	a5,%hi(GC_static_start)
	sw	a4,%lo(GC_static_start)(a5)
	lui	a5,%hi(GC_control_root)
	lw	a4,%lo(GC_control_root)(a5)
	li	a5,8192
	add	a4,a4,a5
	lui	a5,%hi(mem_0_start)
	sw	a4,%lo(mem_0_start)(a5)
	lui	a5,%hi(mem_0_start)
	lw	a4,%lo(mem_0_start)(a5)
	lw	a5,-20(s0)
	addi	a5,a5,-8
	srli	a3,a5,31
	add	a5,a3,a5
	srai	a5,a5,1
	slli	a5,a5,10
	add	a4,a4,a5
	lui	a5,%hi(mem_1_start)
	sw	a4,%lo(mem_1_start)(a5)
	lui	a5,%hi(GC_control_root)
	lw	a5,%lo(GC_control_root)(a5)
	lw	a4,-20(s0)
	slli	a4,a4,10
	add	a4,a5,a4
	lui	a5,%hi(mem_1_end)
	sw	a4,%lo(mem_1_end)(a5)
	lui	a5,%hi(mem_0_start)
	lw	a4,%lo(mem_0_start)(a5)
	lui	a5,%hi(mem_cursor)
	sw	a4,%lo(mem_cursor)(a5)
	lui	a5,%hi(current_is_one)
	sw	zero,%lo(current_is_one)(a5)
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_gc_init, .-_gbl_gc_init
	.align	2
	.globl	_gbl_gc_reclaim
	.type	_gbl_gc_reclaim, @function
_gbl_gc_reclaim:
	addi	sp,sp,-16
	sw	ra,12(sp)
	sw	s0,8(sp)
	addi	s0,sp,16
	lui	a5,%hi(GC_control_root)
	lw	a5,%lo(GC_control_root)(a5)
	mv	a0,a5
	call	free
	nop
	lw	ra,12(sp)
	lw	s0,8(sp)
	addi	sp,sp,16
	jr	ra
	.size	_gbl_gc_reclaim, .-_gbl_gc_reclaim
	.align	2
	.globl	__private_get_len
	.type	__private_get_len, @function
__private_get_len:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a5,-20(s0)
	lw	a5,0(a5)
	bge	a5,zero,.L37
	lw	a5,-20(s0)
	lw	a4,0(a5)
	li	a5,1073741824
	and	a5,a4,a5
	beq	a5,zero,.L38
	lw	a5,-20(s0)
	lw	a5,0(a5)
	slli	a4,a5,2
	li	a5,-2147483648
	xori	a5,a5,-4
	and	a5,a4,a5
	j	.L40
.L38:
	lw	a5,-20(s0)
	lw	a4,0(a5)
	li	a5,536870912
	addi	a5,a5,-1
	and	a5,a4,a5
	j	.L40
.L37:
	lw	a5,-20(s0)
	lw	a5,0(a5)
	srai	a4,a5,12
	li	a5,262144
	addi	a5,a5,-1
	and	a5,a4,a5
.L40:
	mv	a0,a5
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	__private_get_len, .-__private_get_len
	.align	2
	.globl	__private_in_old_region
	.type	__private_in_old_region, @function
__private_in_old_region:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lui	a5,%hi(current_is_one)
	lw	a5,%lo(current_is_one)(a5)
	beq	a5,zero,.L42
	lui	a5,%hi(mem_1_start)
	lw	a5,%lo(mem_1_start)(a5)
	lw	a4,-20(s0)
	bltu	a4,a5,.L43
	lui	a5,%hi(mem_1_end)
	lw	a5,%lo(mem_1_end)(a5)
	lw	a4,-20(s0)
	bgeu	a4,a5,.L43
	li	a5,1
	j	.L44
.L43:
	li	a5,0
.L44:
	andi	a5,a5,1
	andi	a5,a5,0xff
	j	.L45
.L42:
	lui	a5,%hi(mem_0_start)
	lw	a5,%lo(mem_0_start)(a5)
	lw	a4,-20(s0)
	bltu	a4,a5,.L46
	lui	a5,%hi(mem_1_start)
	lw	a5,%lo(mem_1_start)(a5)
	lw	a4,-20(s0)
	bgeu	a4,a5,.L46
	li	a5,1
	j	.L47
.L46:
	li	a5,0
.L47:
	andi	a5,a5,1
	andi	a5,a5,0xff
.L45:
	mv	a0,a5
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	__private_in_old_region, .-__private_in_old_region
	.align	2
	.globl	__private_move
	.type	__private_move, @function
__private_move:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	lw	a5,-36(s0)
	bne	a5,zero,.L49
	li	a5,0
	j	.L50
.L49:
	lw	a0,-36(s0)
	call	__private_in_old_region
	mv	a5,a0
	xori	a5,a5,1
	andi	a5,a5,0xff
	beq	a5,zero,.L51
	lw	a5,-36(s0)
	j	.L50
.L51:
	lw	a5,-36(s0)
	addi	a5,a5,-4
	sw	a5,-20(s0)
	lw	a5,-20(s0)
	lw	a5,0(a5)
	mv	a4,a5
	li	a5,-1073741824
	and	a4,a4,a5
	li	a5,1073741824
	bne	a4,a5,.L52
	lw	a5,-20(s0)
	lw	a5,0(a5)
	slli	a5,a5,8
	lw	a4,-20(s0)
	addi	a4,a4,4
	lbu	a4,0(a4)
	or	a5,a5,a4
	j	.L50
.L52:
	lw	a0,-20(s0)
	call	__private_get_len
	mv	a5,a0
	addi	a5,a5,4
	sw	a5,-24(s0)
	lui	a5,%hi(new_region_end)
	lw	a5,%lo(new_region_end)(a5)
	addi	a5,a5,4
	sw	a5,-28(s0)
	lui	a5,%hi(new_region_end)
	lw	a5,%lo(new_region_end)(a5)
	lw	a4,-24(s0)
	mv	a2,a4
	lw	a1,-20(s0)
	mv	a0,a5
	call	memcpy
	lui	a5,%hi(new_region_end)
	lw	a4,%lo(new_region_end)(a5)
	lw	a5,-24(s0)
	add	a4,a4,a5
	lui	a5,%hi(new_region_end)
	sw	a4,%lo(new_region_end)(a5)
	lw	a5,-28(s0)
	srli	a4,a5,8
	li	a5,1073741824
	or	a5,a4,a5
	mv	a4,a5
	lw	a5,-20(s0)
	sw	a4,0(a5)
	lw	a4,-28(s0)
	lw	a5,-20(s0)
	addi	a5,a5,4
	andi	a4,a4,0xff
	sb	a4,0(a5)
	lw	a5,-28(s0)
.L50:
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	__private_move, .-__private_move
	.align	2
	.globl	__private_scan_and_perform
	.type	__private_scan_and_perform, @function
__private_scan_and_perform:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	zero,-20(s0)
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	addi	a5,a5,4
	sw	a5,-24(s0)
	j	.L54
.L55:
	lw	a5,-24(s0)
	lw	a5,0(a5)
	mv	a0,a5
	call	__private_move
	mv	a4,a0
	lw	a5,-24(s0)
	sw	a4,0(a5)
	lw	a5,-24(s0)
	addi	a5,a5,4
	sw	a5,-24(s0)
	lw	a5,-20(s0)
	addi	a5,a5,1
	sw	a5,-20(s0)
.L54:
	lw	a4,-20(s0)
	lw	a5,-36(s0)
	blt	a4,a5,.L55
	nop
	nop
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	__private_scan_and_perform, .-__private_scan_and_perform
	.align	2
	.globl	__private_gc_run
	.type	__private_gc_run, @function
__private_gc_run:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	sw	s1,36(sp)
	addi	s0,sp,48
	lui	a5,%hi(current_is_one)
	lw	a5,%lo(current_is_one)(a5)
	beq	a5,zero,.L57
	lui	a5,%hi(mem_0_start)
	lw	a5,%lo(mem_0_start)(a5)
	j	.L58
.L57:
	lui	a5,%hi(mem_1_start)
	lw	a5,%lo(mem_1_start)(a5)
.L58:
	lui	a4,%hi(new_region_start)
	sw	a5,%lo(new_region_start)(a4)
	lui	a5,%hi(new_region_start)
	lw	a4,%lo(new_region_start)(a5)
	lui	a5,%hi(new_region_end)
	sw	a4,%lo(new_region_end)(a5)
	lui	a5,%hi(GC_static_start)
	lw	a5,%lo(GC_static_start)(a5)
	sw	a5,-20(s0)
	j	.L59
.L60:
	lw	a5,-20(s0)
	lw	a5,0(a5)
	lw	a4,0(a5)
	lw	a5,-20(s0)
	lw	s1,0(a5)
	mv	a0,a4
	call	__private_move
	mv	a5,a0
	sw	a5,0(s1)
	lw	a5,-20(s0)
	addi	a5,a5,4
	sw	a5,-20(s0)
.L59:
	lui	a5,%hi(GC_control_start)
	lw	a5,%lo(GC_control_start)(a5)
	lw	a4,-20(s0)
	bne	a4,a5,.L60
	call	_pile
	lui	a5,%hi(GC_control_start)
	lw	a5,%lo(GC_control_start)(a5)
	sw	a5,-24(s0)
	j	.L61
.L64:
	lw	a5,-24(s0)
	lw	a5,0(a5)
	sw	a5,-32(s0)
	lw	a5,-24(s0)
	lw	a5,4(a5)
	sw	a5,-36(s0)
	sw	zero,-28(s0)
	j	.L62
.L63:
	lw	a5,-28(s0)
	slli	a5,a5,2
	mv	a4,a5
	lw	a5,-32(s0)
	add	a5,a5,a4
	sw	a5,-40(s0)
	lw	a5,-40(s0)
	lw	a5,0(a5)
	mv	a0,a5
	call	__private_move
	mv	a4,a0
	lw	a5,-40(s0)
	sw	a4,0(a5)
	lw	a5,-28(s0)
	addi	a5,a5,1
	sw	a5,-28(s0)
.L62:
	lw	a4,-28(s0)
	lw	a5,-36(s0)
	blt	a4,a5,.L63
	lw	a5,-24(s0)
	addi	a5,a5,8
	sw	a5,-24(s0)
.L61:
	lui	a5,%hi(GC_control_end)
	lw	a5,%lo(GC_control_end)(a5)
	lw	a4,-24(s0)
	bne	a4,a5,.L64
	j	.L65
.L68:
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	lw	a5,0(a5)
	bge	a5,zero,.L66
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	lw	a4,0(a5)
	li	a5,536870912
	and	a5,a4,a5
	beq	a5,zero,.L67
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	lw	a4,0(a5)
	li	a5,536870912
	addi	a5,a5,-1
	and	a5,a4,a5
	mv	a0,a5
	call	__private_scan_and_perform
	j	.L67
.L66:
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	lw	a4,0(a5)
	li	a5,4096
	addi	a5,a5,-1
	and	a5,a4,a5
	mv	a0,a5
	call	__private_scan_and_perform
.L67:
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	mv	a0,a5
	call	__private_get_len
	mv	a5,a0
	addi	a4,a5,4
	lui	a5,%hi(new_region_start)
	lw	a5,%lo(new_region_start)(a5)
	add	a4,a5,a4
	lui	a5,%hi(new_region_start)
	sw	a4,%lo(new_region_start)(a5)
.L65:
	lui	a5,%hi(new_region_start)
	lw	a4,%lo(new_region_start)(a5)
	lui	a5,%hi(new_region_end)
	lw	a5,%lo(new_region_end)(a5)
	bltu	a4,a5,.L68
	lui	a5,%hi(new_region_end)
	lw	a4,%lo(new_region_end)(a5)
	lui	a5,%hi(mem_cursor)
	sw	a4,%lo(mem_cursor)(a5)
	lui	a5,%hi(current_is_one)
	lw	a5,%lo(current_is_one)(a5)
	li	a4,1
	sub	a4,a4,a5
	lui	a5,%hi(current_is_one)
	sw	a4,%lo(current_is_one)(a5)
	nop
	lw	ra,44(sp)
	lw	s0,40(sp)
	lw	s1,36(sp)
	addi	sp,sp,48
	jr	ra
	.size	__private_gc_run, .-__private_gc_run
	.align	2
	.globl	__private_malloc
	.type	__private_malloc, @function
__private_malloc:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	lui	a5,%hi(current_is_one)
	lw	a5,%lo(current_is_one)(a5)
	beq	a5,zero,.L70
	lui	a5,%hi(mem_cursor)
	lw	a4,%lo(mem_cursor)(a5)
	lw	a5,-36(s0)
	addi	a5,a5,4
	add	a4,a4,a5
	lui	a5,%hi(mem_1_end)
	lw	a5,%lo(mem_1_end)(a5)
	bltu	a4,a5,.L71
	call	__private_gc_run
	j	.L71
.L70:
	lui	a5,%hi(mem_cursor)
	lw	a4,%lo(mem_cursor)(a5)
	lw	a5,-36(s0)
	addi	a5,a5,4
	add	a4,a4,a5
	lui	a5,%hi(mem_1_start)
	lw	a5,%lo(mem_1_start)(a5)
	bltu	a4,a5,.L71
	call	__private_gc_run
.L71:
	lui	a5,%hi(mem_cursor)
	lw	a5,%lo(mem_cursor)(a5)
	sw	a5,-20(s0)
	lui	a5,%hi(mem_cursor)
	lw	a4,%lo(mem_cursor)(a5)
	lw	a5,-36(s0)
	add	a4,a4,a5
	lui	a5,%hi(mem_cursor)
	sw	a4,%lo(mem_cursor)(a5)
	lw	a5,-36(s0)
	mv	a2,a5
	li	a1,0
	lw	a0,-20(s0)
	call	memset
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	__private_malloc, .-__private_malloc
	.align	2
	.globl	_gbl_gc_static_hint
	.type	_gbl_gc_static_hint, @function
_gbl_gc_static_hint:
 #APP
# 273 "riscv-assembly/todo.c" 1
	    lui t0,%hi(GC_static_start)
    lw t0,%lo(GC_static_start)(t0)
    slli t1,a0,2
    add t1,t0,t1
    lui t2,%hi(GC_control_start)
    sw t1,%lo(GC_control_start)(t2)
    lui t3,%hi(GC_control_end)
    sw t1,%lo(GC_control_end)(t3)

    bne t0,t1,.LB1
    ret
.LB1:
    sw a1,0(t0)
    addi t0,t0,4
    bne t0,t1,.LB2
    ret
.LB2:
    sw a2,0(t0)
    addi t0,t0,4
    bne t0,t1,.LB3
    ret
.LB3:
    sw a3,0(t0)
    addi t0,t0,4
    bne t0,t1,.LB4
    ret
.LB4:
    sw a4,0(t0)
    addi t0,t0,4
    bne t0,t1,.LB5
    ret
.LB5:
    sw a5,0(t0)
    addi t0,t0,4
    bne t0,t1,.LB6
    ret
.LB6:
    sw a6,0(t0)
    addi t0,t0,4
    bne t0,t1,.LB7
    ret
.LB7:
    sw a7,0(t0)
    addi t0,t0,4
    add t2,sp,a0
.BRLABEL:
    bge t0,t1,.AFTERLABEL
    lw t3,0(t2)
    sw t3,0(t1)
    addi t2,t2,4
    addi t0,t0,4
    j .BRLABEL
.AFTERLABEL:
    ret

# 0 "" 2
 #NO_APP
	nop
	.size	_gbl_gc_static_hint, .-_gbl_gc_static_hint
	.align	2
	.globl	_gbl_gc_hint
	.type	_gbl_gc_hint, @function
_gbl_gc_hint:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lui	a5,%hi(GC_control_end)
	lw	a5,%lo(GC_control_end)(a5)
	lw	a4,-20(s0)
	sw	a4,0(a5)
	lui	a5,%hi(GC_control_end)
	lw	a5,%lo(GC_control_end)(a5)
	addi	a5,a5,4
	lw	a4,-24(s0)
	sw	a4,0(a5)
	lui	a5,%hi(GC_control_end)
	lw	a5,%lo(GC_control_end)(a5)
	addi	a4,a5,8
	lui	a5,%hi(GC_control_end)
	sw	a4,%lo(GC_control_end)(a5)
	nop
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_gc_hint, .-_gbl_gc_hint
	.align	2
	.globl	_gbl_gc_unhint
	.type	_gbl_gc_unhint, @function
_gbl_gc_unhint:
	addi	sp,sp,-16
	sw	s0,12(sp)
	addi	s0,sp,16
	lui	a5,%hi(GC_control_end)
	lw	a5,%lo(GC_control_end)(a5)
	addi	a4,a5,-8
	lui	a5,%hi(GC_control_end)
	sw	a4,%lo(GC_control_end)(a5)
	nop
	lw	s0,12(sp)
	addi	sp,sp,16
	jr	ra
	.size	_gbl_gc_unhint, .-_gbl_gc_unhint
	.align	2
	.globl	_gbl_gc_struct_malloc
	.type	_gbl_gc_struct_malloc, @function
_gbl_gc_struct_malloc:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	a1,-40(s0)
	lw	a5,-36(s0)
	addi	a5,a5,4
	mv	a0,a5
	call	__private_malloc
	sw	a0,-20(s0)
	lw	a4,-40(s0)
	li	a5,4096
	addi	a5,a5,-1
	and	a5,a4,a5
	sw	a5,-24(s0)
	lw	a5,-36(s0)
	slli	a5,a5,12
	lw	a4,-24(s0)
	or	a5,a4,a5
	sw	a5,-24(s0)
	lw	a5,-20(s0)
	lw	a4,-24(s0)
	sw	a4,0(a5)
	lw	a5,-20(s0)
	addi	a5,a5,4
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	_gbl_gc_struct_malloc, .-_gbl_gc_struct_malloc
	.align	2
	.globl	_gbl_string_malloc
	.type	_gbl_string_malloc, @function
_gbl_string_malloc:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	li	a1,0
	lw	a0,-20(s0)
	call	_gbl_gc_struct_malloc
	mv	a5,a0
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_string_malloc, .-_gbl_string_malloc
	.align	2
	.globl	_gbl_gc_array_malloc
	.type	_gbl_gc_array_malloc, @function
_gbl_gc_array_malloc:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	a1,-40(s0)
	mv	a5,a2
	sb	a5,-41(s0)
	lw	a4,-36(s0)
	lw	a5,-40(s0)
	mul	a5,a4,a5
	addi	a5,a5,4
	mv	a0,a5
	call	__private_malloc
	sw	a0,-24(s0)
	lw	a4,-36(s0)
	li	a5,-2147483648
	or	a5,a4,a5
	sw	a5,-20(s0)
	lw	a4,-40(s0)
	li	a5,4
	bne	a4,a5,.L81
	lw	a4,-20(s0)
	li	a5,1073741824
	or	a5,a4,a5
	sw	a5,-20(s0)
	j	.L82
.L81:
	lw	a4,-20(s0)
	li	a5,-1073741824
	addi	a5,a5,-1
	and	a5,a4,a5
	sw	a5,-20(s0)
.L82:
	lbu	a5,-41(s0)
	beq	a5,zero,.L83
	lw	a4,-20(s0)
	li	a5,536870912
	or	a5,a4,a5
	sw	a5,-20(s0)
	j	.L84
.L83:
	lw	a4,-20(s0)
	li	a5,-536870912
	addi	a5,a5,-1
	and	a5,a4,a5
	sw	a5,-20(s0)
.L84:
	lw	a5,-24(s0)
	lw	a4,-20(s0)
	sw	a4,0(a5)
	lw	a5,-24(s0)
	addi	a5,a5,4
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	_gbl_gc_array_malloc, .-_gbl_gc_array_malloc
	.align	2
	.globl	__private_recover_str_addr
	.type	__private_recover_str_addr, @function
__private_recover_str_addr:
	addi	sp,sp,-48
	sw	s0,44(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sb	zero,-17(s0)
	lui	a5,%hi(current_is_one)
	lw	a5,%lo(current_is_one)(a5)
	bne	a5,zero,.L87
	lui	a5,%hi(mem_1_start)
	lw	a5,%lo(mem_1_start)(a5)
	lw	a4,-36(s0)
	bltu	a4,a5,.L88
	lui	a5,%hi(mem_1_end)
	lw	a5,%lo(mem_1_end)(a5)
	lw	a4,-36(s0)
	bgeu	a4,a5,.L88
	li	a5,1
	j	.L89
.L88:
	li	a5,0
.L89:
	sb	a5,-17(s0)
	lbu	a5,-17(s0)
	andi	a5,a5,1
	sb	a5,-17(s0)
	j	.L90
.L87:
	lui	a5,%hi(mem_0_start)
	lw	a5,%lo(mem_0_start)(a5)
	lw	a4,-36(s0)
	bltu	a4,a5,.L91
	lui	a5,%hi(mem_1_start)
	lw	a5,%lo(mem_1_start)(a5)
	lw	a4,-36(s0)
	bgeu	a4,a5,.L91
	li	a5,1
	j	.L92
.L91:
	li	a5,0
.L92:
	sb	a5,-17(s0)
	lbu	a5,-17(s0)
	andi	a5,a5,1
	sb	a5,-17(s0)
.L90:
	lbu	a5,-17(s0)
	beq	a5,zero,.L93
	lw	a5,-36(s0)
	addi	a5,a5,-4
	lw	a5,0(a5)
	slli	a5,a5,8
	lw	a4,-36(s0)
	lbu	a4,0(a4)
	or	a5,a5,a4
	j	.L94
.L93:
	lw	a5,-36(s0)
.L94:
	mv	a0,a5
	lw	s0,44(sp)
	addi	sp,sp,48
	jr	ra
	.size	__private_recover_str_addr, .-__private_recover_str_addr
	.align	2
	.globl	_pile
	.type	_pile, @function
_pile:
	addi	sp,sp,-16
	sw	s0,12(sp)
	addi	s0,sp,16
	nop
	lw	s0,12(sp)
	addi	sp,sp,16
	jr	ra
	.size	_pile, .-_pile
	.ident	"GCC: (GNU) 10.1.0"
