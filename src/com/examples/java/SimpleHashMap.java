package com.examples.java;

import java.util.LinkedHashMap;

public class SimpleHashMap<K, V> {
    //单项链表
    static class Node<K, V>{
        final K key;
        V value;
        Node<K, V> next;
        Node(K key, V value, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    private Node<K, V>[] table; //桶数组
    private int size;
    private int threshold; //扩容阈值 = 容量 * 0.75
    static final int DEFAULT_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    //扰动函数 + 定位桶下标
    private int hash(Object key) {
        int h = key.hashCode();
        return (h ^ (h>>>16)) & (table.length -1);
    }

    public V put(K key, V value){
        int index = hash(key);
        if(table[index]==null){
            table[index] = new Node<>(key, value,null);
        }else{
            Node<K, V> p = table[index];
            while(true){
                if (p.key.equals(key)) {      // key 已存在 → 覆盖并返回旧值
                    V old = p.value;
                    p.value = value;
                    return old;
                }
                if(p.next==null)
                    break;
                p =p.next;
            }
            p.next = new Node<>(key, value,null);
        }

        if(++size>threshold) resize();
        return null;
    }

    public V get(K key){
        int index = hash(key);
        for(Node<K, V> p = table[index]; p!=null; p = p.next){
            if(p.key.equals(key)){
                return p.value;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize(){
        Node<K, V>[] old = table;
        table = (Node<K, V>[]) new Node[old.length<<1];
        threshold = (int) (table.length*DEFAULT_LOAD_FACTOR);
        for(Node<K, V> head : old){
            while(head != null){
                Node<K, V> next = head.next;
                int h = head.key.hashCode() ^ (head.key.hashCode() >>> 16);
                int i = h & (table.length-1);
                head.next = table[i];         // 头插搬入新桶
                table[i] = head;
                head = next;
            }
        }
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        for (int i = 0; i < 50; i++) map.put("key" + i, i); // 触发扩容
        System.out.println(map.get("key0"));      // 0
        System.out.println(map.get("key49"));     // 49
        System.out.println(map.get("notExist"));  // null
        map.put("key0", 100);                     // 覆盖
        System.out.println(map.get("key0"));      // 100
        System.out.println("size = " + map.size()); // size = 50
    }


}
