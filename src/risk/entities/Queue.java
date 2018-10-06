package risk.entities;

import java.util.LinkedList;

public class Queue<T>{
private LinkedList<T> list=new LinkedList<>();

public void insert(T element){
     list.addLast(element);
}

public void remove(){
    list.removeFirst();
}

public int size(){
    return list.size();
}

public T element(){
     return list.getFirst();
}
}