# File: cachesimulator.py
# Author(s): Ibrahim Ozgel, Gurhan Aydin
# Date: 12/8/2021
# Section: 504
# E-mail: ibrahim@tamu.edu, ga220604@tamu.edu
# Description:
# e.g. This file implements the cache and simulates it





#global vars..
import random
import sys
import math

c = []
ramDict = {}
global cacheHits
cacheHits = 0
global cacheMiss
cacheMiss = 0

global LFUs0
global LFUs1
global LFUs2
global LFUs3
LFUs0=0
LFUs1=0
LFUs2=0
LFUs3=0

global d_e
d_e = 0

global setIndexGlobal
setIndexGlobal = 0

"""This function checks whether there is a cache hit

@args:
   address: address in which to check the cache (string but in hexadecimal form)
   t: the tag-bit (integer)
   s: set index (integer)
   E: number of lines per set (integer)
@retval:
   Boolean representing whether there has been a cache hit
"""
def CacheHit(address, t, s, E): #t bits, s bits, lines per set
    out = False #output variable assumes not a cachehit til proven otherwise
    addressBin = (bin(int(address[2:], 16))[2:].zfill(8)) #convert hex --> binary
    #separate set index & tag bits;
    tag = addressBin[:int(t)]
    set_index = addressBin[int(t):int(t)+int(s)]
    if set_index != "":
        print(f"set:{int(set_index, 2)}")
        setIndexGlobal = int(set_index, 2) #use binary to access array
    else:
        print(f"set:0")
        setIndexGlobal = 0

    if tag != "": #print tag bit
        print(f"tag:{(hex(int(tag, 2)))[2:].upper()}") 
    else:
        print("tag:")

    #if the valid bit is 1, then check to see if the tag is in the address, if so, then it is a cache hit
    for e in range(E):
        if tag != "":
            if ((str(c[setIndexGlobal][e][3]).upper() == (str(hex(int(tag, 2)))[2:].zfill(2)).upper()) and (str(c[setIndexGlobal][e][0]) == "1")):
                d_e = e
                out = True
                break
        else:
            if ((str(c[setIndexGlobal][e][0])) == "1"):
                d_e = e
                out = True
                break
    return out


def CacheRead(address, s, t, b, S, E, B, Lrep, RAM): #set index, tag bits, block offset etc.
    
    global c, cacheHits, cacheMiss, LFUs0, LFUs1, LFUs2, LFUs3, d_e, setIndexGlobal #access global variables
    # AssetIndexGlobalgning Important Variables
    addressBin = (bin(int(address[2:], 16))[2:].zfill(8))
    offsetBin = addressBin[int(t)+int(s):]
    tag = addressBin[:int(t)]

    if CacheHit(address, t, s, E):
        cacheHits += 1  # iterate hit if its a hit
        print("hit:yes")
        print("eviction_line:-1")
        print("ram_address:-1")
        print("data:0x"+c[setIndexGlobal][d_e][int(offsetBin, 2) + 4])
        # LFU global variable updates depending on line hit.
        if Lrep == 3:
            if d_e == 0:
                LFUs0 += 1
                c[setIndexGlobal][0][2] = LFUs0
            elif d_e == 1:
                LFUs1 += 1
                c[setIndexGlobal][1][2] = LFUs1
            elif d_e == 2:
                LFUs2 += 1
                c[setIndexGlobal][2][2] = LFUs2
            else:
                LFUs3 += 1
                c[setIndexGlobal][3][2] = LFUs3
    else: #if it is cache miss:
        cacheMiss += 1  # iterate miss..
        print("hit:no")
        if Lrep == 1:  # equals 1 if random replacement = true
            i = 0
            for e1 in range(E):
                if c[setIndexGlobal][e1][0] == "1":  # will be 0 if lines are not valid
                    i += 1
            if i != 4:  # evict the lines that are invalid
                while True:
                    l = random.randint(1, E)
                    if (c[setIndexGlobal][l-1][0] == "0"):
                        break
            else:  # evict a ramdom line if all of them are valid
                l = random.randint(1, E)
        elif Lrep == 2:  # equals two if replacemen policy is least recently used
            if E == 1: #we can remove the line since it is the only one in the set
                l = 1  
            elif E == 2:
                # index bit determines the line 2 be evicted
                if c[setIndexGlobal][0][2] == 0:
                    l = 1  # line to be evicted
                    c[setIndexGlobal][0][2] = 1
                else:
                    l = 2
                    c[setIndexGlobal][0][2] = 0
            else:
                if c[setIndexGlobal][0][2] == 0: #the index will equal zero if it is the least recently used
                    l = 1
                    
                    c[setIndexGlobal][0][2] = 1# iterate the index to the next bit
                    # Set next index to 0 setIndexGlobalnce that is next in line to be evicted
                    c[setIndexGlobal][1][2] = 0
                    
                # do the same process 3 more times
                elif c[setIndexGlobal][1][2] == 0:
                    l = 2
                    c[setIndexGlobal][1][2] = 1
                    c[setIndexGlobal][2][2] = 0
                elif c[setIndexGlobal][2][2] == 0:
                    l = 3
                    c[setIndexGlobal][2][2] = 1
                    c[setIndexGlobal][3][2] = 0
                else:
                    l = 4
                    c[setIndexGlobal][3][2] = 1
                    # We go back and set first line to 0 to denote least recently used
                    c[setIndexGlobal][0][2] = 0

        print(f"eviction_line:{l}")
        c[setIndexGlobal][l-1][0] = "1"  #set valid bit in the line
        if tag != "":
            # Set tag in cache list if there is a tag as stated before.
            c[setIndexGlobal][l-1][3] = str(hex(int(tag, 2)))[2:].zfill(2).upper()
        for b in range(4, B+4):
            # set the cache to the data from ram
            c[setIndexGlobal][l-1][b] = RAM["0x" + (hex((int(address[2:], 16)-int(offsetBin, 2))+(b-4))[2:].zfill(2)).upper()]
        print(f"ram_address:{address}") 
        print("data:0x"+RAM[address])  


def CacheWrite(address, data, s, t, b, S, E, B, Lrep, RAM, writeHit, writeMiss):
    global c, cacheHits, cacheMiss, LFUs0, LFUs1, LFUs2, LFUs3 #access global variables
    # get the binary of the address, offset-bits, and the tag-bits
    #print(address)
    addressBin = (bin(int(address[2:], 16))[2:].zfill(8))
    offsetBin = addressBin[int(t) + int(s):]
    tag = addressBin[:int(t)]

    if CacheHit(address, t, s, E): 
        cacheHits += 1  # increment global variable for cacheHit
        print("writeHit:yes")
        print("eviction_line:-1")
        print("ram_address:-1")
        print(f"data:{data}")

        c[setIndexGlobal][d_e][int(offsetBin, 2)+4] = data[2:]
        if writeHit == 1:
            RAM[address] = data[2:]
        else:
            c[setIndexGlobal][d_e][1] = 1  # equals one when it is the dirty bit
        # LFU global variable updates depending on line hit. --------------------------------------------------------------------------
        if Lrep == 3:
            if d_e == 0:
                LFUs0 += 1
                c[setIndexGlobal][0][2] = LFUs0
            elif d_e == 1:
                LFUs1 += 1
                c[setIndexGlobal][1][2] = LFUs1
            elif d_e == 2:
                LFUs2 += 1
                c[setIndexGlobal][2][2] = LFUs2
            else:
                LFUs3 += 1
                c[setIndexGlobal][3][2] = LFUs3
        print(f"dirty_bit:{c[setIndexGlobal][d_e][1]}")

    else: #if write-miss:
        cacheMiss += 1  # iterate cacheMiss 
        print("writeHit:no")
        if Lrep == 1:  # ==1 if random replacement
            i = 0
            for e1 in range(E):
                if c[setIndexGlobal][e1][0] == "1":  # equals 1 whent the lines are valid
                    i += 1
            if i != 4:  # If all lines are not valid we evict those lines
                while True:
                    l = random.randint(1, E)
                    if (c[setIndexGlobal][l-1][0] == "0"):
                        break
            else:  # If all lines are valid we evict any at random.
                l = random.randint(1, E)
        elif Lrep == 2:  # Least Recently Used
            if E == 1:
                l = 1  # evict line
            elif E == 2:
                if c[setIndexGlobal][0][2] == 0:
                    l = 1  
                    c[setIndexGlobal][0][2] = 1
                else:
                    l = 2
                    c[setIndexGlobal][0][2] = 0
            else:
                if c[setIndexGlobal][0][2] == 0:
                    l = 1
                    # Since the index is now used, we can set it to one
                    c[setIndexGlobal][0][2] = 1
                    # Set next index to 0
                    c[setIndexGlobal][1][2] = 0
                # Repeats for all lines
                elif c[setIndexGlobal][1][2] == 0:
                    l = 2
                    c[setIndexGlobal][1][2] = 1
                    c[setIndexGlobal][2][2] = 0
                elif c[setIndexGlobal][2][2] == 0:
                    l = 3
                    c[setIndexGlobal][2][2] = 1
                    c[setIndexGlobal][3][2] = 0
                else:
                    l = 4
                    c[setIndexGlobal][3][2] = 1
                    # We go back and set first line to 0 to denote least recently used
                    c[setIndexGlobal][0][2] = 0

        if writeMiss == 1:
            print(f"eviction_line:{l}")
            # set the valid bit in the line to 1
            c[setIndexGlobal][l-1][0] = "1"
            if tag != "":
                c[setIndexGlobal][l-1][3] = str(hex(int(tag, 2)))[2:].zfill(2).upper()
            for b in range(4, B+4):
                # get the data from ram and put it in cache
                c[setIndexGlobal][l-1][b] = RAM["0x" +  (hex((int(address[2:], 16) - int(offsetBin, 2))+(b-4))[2:].zfill(2)).upper()]

            c[setIndexGlobal][l-1][int(offsetBin, 2)+4] = data[2:]
            if writeHit == 1:
                RAM[address] = data[2:]
            else:
                c[setIndexGlobal][l-1][1] = 1 
            #output info:
            print(f"ram_address:{address}") 
            print("data:0x"+RAM[address]) 
            print(f"dirty_bit:{c[setIndexGlobal][l-1][1]}")
        else:
            RAM[address] = data[2:]
            #output info:
            print(f"eviction_line:{-1}")
            print(f"ram_address:{address}") 
            print("data:0x"+RAM[address])
            print("dirty_bit:0") 


def CacheFlush(B, E, S):
    # loops through the cache and sets everything to zero basically..
    for i in range(S):
        for e in range(E):
            for b in range(B+4):
                if b == 0:
                    c[i][e][b] = "0"
                elif b == 1:
                    c[i][e][b] = "0"
                elif b == 2:
                    c[i][e][b] = "0"
                else:
                    c[i][e][b] = "00"
    print("cache_cleared")


def CacheView(B, E, S, C, Lrep, writeHit, writeMiss):
    # convert the input numbers to what they signify..
    if Lrep == 1:
        rep = "random_replacement"
    elif Lrep == 2:
        rep = "least recently used"
    else:
        rep = "least frequently used"
    if writeHit == 1:
        wrhit = "write_through"
    else:
        wrhit = "write_back"
    if writeMiss == 1:
        wrmiss = "write_allocate"
    else:
        wrmiss = "no write_allocate"

    # print info:
    print(f"cache_size:{C}")
    print(f"data_block_size:{B}")
    print(f"associativity:{E}")
    print(f"replacement_policy:{rep}")
    print(f"write_hit_policy:{wrhit}")
    print(f"write_miss_policy:{wrmiss}")
    print(f"number_of_cacheHits:{cacheHits}")
    print(f"number_of_cacheMisses:{cacheMiss}")
    print("cache_content:")
    for i in range(S):
        for e in range(E):
            for b in range(B+4):
                if b != 2:  
                    # skip the least frequenty/ recently used
                    print(c[i][e][b], end=" ")
            print("")
            
def CacheDump(B, E, S):
    # loop through c and dump it in a .txt file
    o = open("cache.txt", "w")
    for i in range(S):
        for e in range(E):
            for b in range(4, B+4):  # Start at the first index 4 of the data Bytes in the cache
                o.write(str(c[i][e][b]) +" ")
            o.write("\n")


def MemoryDump(RAM):
    # loop through RAM and write it in a .txt file
    o = open("ram.txt", "w")
    o.write("memory-dump\n")
    for r in RAM:
        if len(str(hex(RAM[r]))[2:])==1:
            o.write('0')
        o.write(str(hex(RAM[r]))[2:].upper())
        o.write('\n')

def MemoryView(RAM, count):
    print(f"memory_size:{count}")
    print("memory_content:")
    print("Address:data", end="")
    # address : [8bytes]
   #count = 0
    for r in RAM:
        if count % 8 == 0:
            print(f"\n{r}:", end="")
        print(str(hex(RAM[r]))[2:], end=" ")
        count += 1
    print("")


#main-------------------------------------------------
def main():
    ramFile  = open(sys.argv[1], "r")
    print("*** Welcome to the cache simulator ***")
    print()
    inpt = input("initialize the RAM:\n")
    init= inpt.split()
    ramNum = 0
    for line in ramFile:
        #convert hex value to int
        if ramNum>=int(init[1],16) and ramNum<=int(init[2],16):
            data = line.strip()
            ramDict[hex(ramNum)]=int(data, 16)
        ramNum += 1
    print("RAM successfully initialized!")
    print("Configure the Cache")
    #TODO: get user input and check if input is within bounds
    cacheSize = int(input("cache size: "))
    dataBlockSize = int(input("data block size: "))
    associativity = int(input("associativity: "))
    replacementPolicy = int(input("replacement policy: "))
    writeHitPolicy = int(input("write hit policy: "))
    writeMissPolicy = int(input("write miss policy: "))

    global c
    # define B, E, S
    B = dataBlockSize
    E = associativity
    S = int(cacheSize/(B*E))
    b = math.log(B, 2)
    s = math.log(S, 2)
    t = 8 - (s + b)
    
    for i in range(S):
        c.append([])
        for e in range(E):
            c[i].append([])
            for b in range(B + 4):
                if b==0 or b==1:
                    c[i][e].append("0") # Valid or Dirty bit
                elif b==2:
                    c[i][e].append(0)
                else:
                    c[i][e].append("00")



    print("Cache Successfully Configured")
    
    
    print("*** Cache simulator menu ***")
    print("type one command:")
    print("1. cache-read")
    print("2. cache-write")
    print("3. cache-flush")
    print("4. cache-view")
    print("5. memory-view")
    print("6. cache-dump")
    print("7. memory-dump")
    print("8. quit")
    print("****************************")
    selection = input()
    while( not selection=="quit"):
        if("cache-read" in selection):
            addr = selection.replace("cache-read", "").strip()
            CacheRead(addr,s, t, b, S, E, B, replacementPolicy, ramDict)
        elif("cache-write" in selection):   
            selection = selection.replace("cache-read", "")
            spl = selection.split()
            CacheWrite(spl[1], spl[2], s, t, b, S, E, B, replacementPolicy, ramDict, writeHitPolicy, writeMissPolicy)
        elif(selection == "cache-flush"):
            CacheFlush(B, E, S)
        elif(selection == "cache-view"):
            CacheView(B, E, S, cacheSize, replacementPolicy, writeHitPolicy, writeMissPolicy)
        elif(selection == "cache-dump"):
            CacheDump(B, E, S)
        elif(selection == "memory-view"):
            MemoryView(ramDict, len(ramDict))
        elif(selection == "memory-dump"):
            MemoryDump(ramDict)

        selection = input("type one command\n")
    return
main()        