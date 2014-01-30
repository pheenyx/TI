li %%r5, 0
addi %%r3, %%r3, -4
addi %%r4, %%r4, -4
loop1:
lwzu %%r10, 4(%%r3)
lwzu %%r11, 4(%%r4)
add %%r5, %%r10, %%r5
subf %%r5, %%r11, %%r5
addi %%r6, %%r6, -1
cmpi %%cr0, 0, %%r6, 0
bne loop1