package com.examples.java.interview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q1 {
    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(3);
        TreeNode n2 = new TreeNode(9);
        TreeNode n3 = new TreeNode(20);
        n1.left=n2;
        n1.right=n3;
        List<List<Integer>> res = levelOrder(n1);
        for(List<Integer> level : res){
            for(Integer i : level){
                System.out.print(i);
            }
            System.out.println();
        }
    }
    public static List<List<Integer>> levelOrder(TreeNode root){
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            List<Integer> level = new ArrayList<>();
            int curLevel = queue.size();
            for(int i=0;i<curLevel;i++){
                TreeNode tmp = queue.poll();
                level.add(tmp.val);
                if(tmp.left!=null){
                    queue.offer(tmp.left);
                }
                if(tmp.right!=null){
                    queue.offer(tmp.right);
                }
            }
            result.add(level);
        }
        return result;
    }
}
class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x){
        this.val=x;
    }

}
