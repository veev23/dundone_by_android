package com.makers.dundone;

/*
다른 프래그먼트에서 해당 프래그먼트에 요소를 삽입할 때 사용
해당 프래그먼트는 메모리에 존재해야함
 */
public interface AddToAdapterInterface<E> {
    void add(E data);
}
