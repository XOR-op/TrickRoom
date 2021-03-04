	.file	"builtin.c"
	.option nopic
	.attribute arch, "rv32i2p0_m2p0_a2p0"
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.section	.rodata.str1.4,"aMS",@progbits,1
	.align	2
.LC0:
	.string	"%s"
	.text
	.align	2
	.globl	_gbl_print
	.type	_gbl_print, @function
_gbl_print:
	mv	a1,a0
	lui	a0,%hi(.LC0)
	addi	a0,a0,%lo(.LC0)
	tail	printf
	.size	_gbl_print, .-_gbl_print
	.align	2
	.globl	_gbl_println
	.type	_gbl_println, @function
_gbl_println:
	tail	puts
	.size	_gbl_println, .-_gbl_println
	.section	.rodata.str1.4
	.align	2
.LC1:
	.string	"%d"
	.text
	.align	2
	.globl	_gbl_printInt
	.type	_gbl_printInt, @function
_gbl_printInt:
	mv	a1,a0
	lui	a0,%hi(.LC1)
	addi	a0,a0,%lo(.LC1)
	tail	printf
	.size	_gbl_printInt, .-_gbl_printInt
	.section	.rodata.str1.4
	.align	2
.LC2:
	.string	"%d\n"
	.text
	.align	2
	.globl	_gbl_printlnInt
	.type	_gbl_printlnInt, @function
_gbl_printlnInt:
	mv	a1,a0
	lui	a0,%hi(.LC2)
	addi	a0,a0,%lo(.LC2)
	tail	printf
	.size	_gbl_printlnInt, .-_gbl_printlnInt
	.align	2
	.globl	_gbl_getString
	.type	_gbl_getString, @function
_gbl_getString:
	addi	sp,sp,-16
	li	a0,1024
	sw	ra,12(sp)
	sw	s0,8(sp)
	call	malloc
	mv	s0,a0
	mv	a1,a0
	lui	a0,%hi(.LC0)
	addi	a0,a0,%lo(.LC0)
	call	scanf
	lw	ra,12(sp)
	mv	a0,s0
	lw	s0,8(sp)
	addi	sp,sp,16
	jr	ra
	.size	_gbl_getString, .-_gbl_getString
	.align	2
	.globl	_gbl_getInt
	.type	_gbl_getInt, @function
_gbl_getInt:
	addi	sp,sp,-32
	lui	a0,%hi(.LC1)
	addi	a1,sp,12
	addi	a0,a0,%lo(.LC1)
	sw	ra,28(sp)
	call	scanf
	lw	ra,28(sp)
	lw	a0,12(sp)
	addi	sp,sp,32
	jr	ra
	.size	_gbl_getInt, .-_gbl_getInt
	.align	2
	.globl	_gbl_toString
	.type	_gbl_toString, @function
_gbl_toString:
	addi	sp,sp,-16
	sw	s1,4(sp)
	mv	s1,a0
	li	a0,16
	sw	ra,12(sp)
	sw	s0,8(sp)
	call	malloc
	lui	a1,%hi(.LC1)
	mv	a2,s1
	addi	a1,a1,%lo(.LC1)
	mv	s0,a0
	call	sprintf
	lw	ra,12(sp)
	mv	a0,s0
	lw	s0,8(sp)
	lw	s1,4(sp)
	addi	sp,sp,16
	jr	ra
	.size	_gbl_toString, .-_gbl_toString
	.align	2
	.globl	_gbl_malloc
	.type	_gbl_malloc, @function
_gbl_malloc:
	tail	malloc
	.size	_gbl_malloc, .-_gbl_malloc
	.align	2
	.globl	_str_length
	.type	_str_length, @function
_str_length:
	tail	strlen
	.size	_str_length, .-_str_length
	.align	2
	.globl	_str_substring
	.type	_str_substring, @function
_str_substring:
	addi	sp,sp,-32
	sw	s0,24(sp)
	sub	s0,a2,a1
	sw	s3,12(sp)
	mv	s3,a0
	addi	a0,s0,1
	sw	ra,28(sp)
	sw	s1,20(sp)
	sw	s2,16(sp)
	mv	s2,a1
	call	malloc
	mv	s1,a0
	add	a1,s3,s2
	mv	a2,s0
	add	s0,s1,s0
	call	memcpy
	sb	zero,0(s0)
	lw	ra,28(sp)
	lw	s0,24(sp)
	lw	s2,16(sp)
	lw	s3,12(sp)
	mv	a0,s1
	lw	s1,20(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_substring, .-_str_substring
	.align	2
	.globl	_str_parseInt
	.type	_str_parseInt, @function
_str_parseInt:
	addi	sp,sp,-32
	lui	a1,%hi(.LC1)
	addi	a2,sp,12
	addi	a1,a1,%lo(.LC1)
	sw	ra,28(sp)
	call	sscanf
	lw	ra,28(sp)
	lw	a0,12(sp)
	addi	sp,sp,32
	jr	ra
	.size	_str_parseInt, .-_str_parseInt
	.align	2
	.globl	_str_concat
	.type	_str_concat, @function
_str_concat:
	addi	sp,sp,-16
	sw	s0,8(sp)
	mv	s0,a0
	li	a0,1024
	sw	ra,12(sp)
	sw	s1,4(sp)
	mv	s1,a1
	call	malloc
	mv	a1,s0
	mv	s0,a0
	call	strcpy
	mv	a1,s1
	call	strcat
	lw	ra,12(sp)
	mv	a0,s0
	lw	s0,8(sp)
	lw	s1,4(sp)
	addi	sp,sp,16
	jr	ra
	.size	_str_concat, .-_str_concat
	.align	2
	.globl	_str_ord
	.type	_str_ord, @function
_str_ord:
	add	a0,a0,a1
	lbu	a0,0(a0)
	ret
	.size	_str_ord, .-_str_ord
	.align	2
	.globl	_str_eq
	.type	_str_eq, @function
_str_eq:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	seqz	a0,a0
	addi	sp,sp,16
	jr	ra
	.size	_str_eq, .-_str_eq
	.align	2
	.globl	_str_ne
	.type	_str_ne, @function
_str_ne:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	snez	a0,a0
	addi	sp,sp,16
	jr	ra
	.size	_str_ne, .-_str_ne
	.align	2
	.globl	_str_lt
	.type	_str_lt, @function
_str_lt:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	srli	a0,a0,31
	addi	sp,sp,16
	jr	ra
	.size	_str_lt, .-_str_lt
	.align	2
	.globl	_str_le
	.type	_str_le, @function
_str_le:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	slti	a0,a0,1
	addi	sp,sp,16
	jr	ra
	.size	_str_le, .-_str_le
	.align	2
	.globl	_str_gt
	.type	_str_gt, @function
_str_gt:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	sgt	a0,a0,zero
	addi	sp,sp,16
	jr	ra
	.size	_str_gt, .-_str_gt
	.align	2
	.globl	_str_ge
	.type	_str_ge, @function
_str_ge:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	not	a0,a0
	srli	a0,a0,31
	addi	sp,sp,16
	jr	ra
	.size	_str_ge, .-_str_ge
	.ident	"GCC: (GNU) 10.1.0"
