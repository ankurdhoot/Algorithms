# -*- coding: utf-8 -*-
"""
Created on Sat May 30 10:16:22 2015

@author: Ankur Dhoot
"""

def test():
    N = 21
    sz = 1
    while sz < N:
        lo = 0
        while (lo) < (N-sz):
            print(lo, lo + sz - 1, min(lo + sz + sz - 1, N - 1))
            lo += sz + sz
        sz = sz + sz