li %%r6, 4
addi %%r3, %%r3, -8
addi %%r4, %%r4, -8
addi %%r5, %%r5, -8
lfdu %%f5, 8(%%r4)
lfdu %%f6, 8(%%r4)
lfdu %%f7, 8(%%r4)
lfdu %%f8, 8(%%r4)
loop1:
lfdu %%f1, 8(%%r3)
lfdu %%f2, 8(%%r3)
lfdu %%f3, 8(%%r3)
lfdu %%f4, 8(%%r3)
fsub %%f9, %%f9, %%f9
fmadd %%f9, %%f1, %%f5, %%f9
fmadd %%f9, %%f2, %%f6, %%f9
fmadd %%f9, %%f3, %%f7, %%f9
fmadd %%f9, %%f4, %%f8, %%f9
stfdu %%f9, 8(%%r5)
addi %%r6, %%r6, -1
cmpi %%cr0, 0, %%r6, 0
bne loop1