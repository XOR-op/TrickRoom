	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 10, 15	sdk_version 10, 15, 4
	.globl	__gbl_print             ## -- Begin function _gbl_print
	.p2align	4, 0x90
__gbl_print:                            ## @_gbl_print
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rsi
	leaq	L_.str(%rip), %rdi
	movb	$0, %al
	callq	_printf
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__gbl_println           ## -- Begin function _gbl_println
	.p2align	4, 0x90
__gbl_println:                          ## @_gbl_println
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rsi
	leaq	L_.str.1(%rip), %rdi
	movb	$0, %al
	callq	_printf
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__gbl_printInt          ## -- Begin function _gbl_printInt
	.p2align	4, 0x90
__gbl_printInt:                         ## @_gbl_printInt
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movl	-4(%rbp), %esi
	leaq	L_.str.2(%rip), %rdi
	movb	$0, %al
	callq	_printf
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__gbl_printlnInt        ## -- Begin function _gbl_printlnInt
	.p2align	4, 0x90
__gbl_printlnInt:                       ## @_gbl_printlnInt
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movl	-4(%rbp), %esi
	leaq	L_.str.3(%rip), %rdi
	movb	$0, %al
	callq	_printf
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__gbl_getString         ## -- Begin function _gbl_getString
	.p2align	4, 0x90
__gbl_getString:                        ## @_gbl_getString
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	$1024, %edi             ## imm = 0x400
	callq	_malloc
	movq	%rax, -8(%rbp)
	movq	-8(%rbp), %rsi
	leaq	L_.str(%rip), %rdi
	movb	$0, %al
	callq	_scanf
	movq	-8(%rbp), %rcx
	movl	%eax, -12(%rbp)         ## 4-byte Spill
	movq	%rcx, %rax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__gbl_getInt            ## -- Begin function _gbl_getInt
	.p2align	4, 0x90
__gbl_getInt:                           ## @_gbl_getInt
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	leaq	L_.str.2(%rip), %rdi
	leaq	-4(%rbp), %rsi
	movb	$0, %al
	callq	_scanf
	movl	-4(%rbp), %ecx
	movl	%eax, -8(%rbp)          ## 4-byte Spill
	movl	%ecx, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__gbl_toString          ## -- Begin function _gbl_toString
	.p2align	4, 0x90
__gbl_toString:                         ## @_gbl_toString
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$32, %rsp
	movl	%edi, -4(%rbp)
	movl	$16, %edi
	callq	_malloc
	xorl	%esi, %esi
	movq	$-1, %rdx
	movq	%rax, -16(%rbp)
	movq	-16(%rbp), %rdi
	movl	-4(%rbp), %r8d
	leaq	L_.str.2(%rip), %rcx
	movb	$0, %al
	callq	___sprintf_chk
	movq	-16(%rbp), %rcx
	movl	%eax, -20(%rbp)         ## 4-byte Spill
	movq	%rcx, %rax
	addq	$32, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_length            ## -- Begin function _str_length
	.p2align	4, 0x90
__str_length:                           ## @_str_length
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rdi
	callq	_strlen
                                        ## kill: def $eax killed $eax killed $rax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_substring         ## -- Begin function _str_substring
	.p2align	4, 0x90
__str_substring:                        ## @_str_substring
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$32, %rsp
	movq	%rdi, -8(%rbp)
	movl	%esi, -12(%rbp)
	movl	%edx, -16(%rbp)
	movl	-16(%rbp), %eax
	subl	-12(%rbp), %eax
	addl	$1, %eax
	movslq	%eax, %rcx
	shlq	$0, %rcx
	movq	%rcx, %rdi
	callq	_malloc
	movq	$-1, %rcx
	movq	%rax, -24(%rbp)
	movq	-24(%rbp), %rdi
	movq	-8(%rbp), %rax
	movslq	-12(%rbp), %r8
	addq	%r8, %rax
	movl	-16(%rbp), %edx
	subl	-12(%rbp), %edx
	movslq	%edx, %rdx
	movq	%rax, %rsi
	callq	___memcpy_chk
	movq	-24(%rbp), %rcx
	movl	-16(%rbp), %r9d
	subl	-12(%rbp), %r9d
	movslq	%r9d, %rdx
	movb	$0, (%rcx,%rdx)
	movq	-24(%rbp), %rcx
	movq	%rax, -32(%rbp)         ## 8-byte Spill
	movq	%rcx, %rax
	addq	$32, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_parseInt          ## -- Begin function _str_parseInt
	.p2align	4, 0x90
__str_parseInt:                         ## @_str_parseInt
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rdi
	leaq	L_.str.2(%rip), %rsi
	leaq	-12(%rbp), %rdx
	movb	$0, %al
	callq	_sscanf
	movl	-12(%rbp), %ecx
	movl	%eax, -16(%rbp)         ## 4-byte Spill
	movl	%ecx, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_concat            ## -- Begin function _str_concat
	.p2align	4, 0x90
__str_concat:                           ## @_str_concat
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$48, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movl	$1024, %edi             ## imm = 0x400
	callq	_malloc
	movq	$-1, %rdx
	movq	%rax, -24(%rbp)
	movq	-24(%rbp), %rdi
	movq	-8(%rbp), %rsi
	callq	___strcpy_chk
	movq	$-1, %rdx
	movq	-24(%rbp), %rdi
	movq	-16(%rbp), %rsi
	movq	%rax, -32(%rbp)         ## 8-byte Spill
	callq	___strcat_chk
	movq	-24(%rbp), %rcx
	movq	%rax, -40(%rbp)         ## 8-byte Spill
	movq	%rcx, %rax
	addq	$48, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_ord               ## -- Begin function _str_ord
	.p2align	4, 0x90
__str_ord:                              ## @_str_ord
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movq	%rdi, -8(%rbp)
	movl	%esi, -12(%rbp)
	movq	-8(%rbp), %rax
	movslq	-12(%rbp), %rcx
	movsbl	(%rax,%rcx), %eax
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_eq                ## -- Begin function _str_eq
	.p2align	4, 0x90
__str_eq:                               ## @_str_eq
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	callq	_strcmp
	cmpl	$0, %eax
	sete	%cl
	andb	$1, %cl
	movzbl	%cl, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_ne                ## -- Begin function _str_ne
	.p2align	4, 0x90
__str_ne:                               ## @_str_ne
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	callq	_strcmp
	cmpl	$0, %eax
	setne	%cl
	andb	$1, %cl
	movzbl	%cl, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_lt                ## -- Begin function _str_lt
	.p2align	4, 0x90
__str_lt:                               ## @_str_lt
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	callq	_strcmp
	cmpl	$0, %eax
	setl	%cl
	andb	$1, %cl
	movzbl	%cl, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_le                ## -- Begin function _str_le
	.p2align	4, 0x90
__str_le:                               ## @_str_le
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	callq	_strcmp
	cmpl	$0, %eax
	setle	%cl
	andb	$1, %cl
	movzbl	%cl, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_gt                ## -- Begin function _str_gt
	.p2align	4, 0x90
__str_gt:                               ## @_str_gt
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	callq	_strcmp
	cmpl	$0, %eax
	setg	%cl
	andb	$1, %cl
	movzbl	%cl, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	__str_ge                ## -- Begin function _str_ge
	.p2align	4, 0x90
__str_ge:                               ## @_str_ge
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	callq	_strcmp
	cmpl	$0, %eax
	setge	%cl
	andb	$1, %cl
	movzbl	%cl, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"%s"

L_.str.1:                               ## @.str.1
	.asciz	"%s\n"

L_.str.2:                               ## @.str.2
	.asciz	"%d"

L_.str.3:                               ## @.str.3
	.asciz	"%d\n"


.subsections_via_symbols
