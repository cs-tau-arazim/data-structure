package test1;

import test1.RBTree.RBNode;

public class RBInternalTester 
{

	public static boolean isTreeValid(RBTree t)
	{
		if(checkRedRule(t.getRoot()) == false)
		{
			 System.out.println("Red rule failed");
			 return false;
		}
		if(checkBlackRule(t.getRoot()) == false)
		{
			System.out.println("Black rule failed");
			System.out.println(minBlackPaths(t.getRoot(),0)+":"+maxBlackPaths(t.getRoot(),0));
			//return false;
		}
		//if(checkIsSearchTree(t.getRoot()) == false)
		//{
			//System.out.println("Search tree failed");
			//return false;
		//}
		//if(checkSize(t) == false)
		//{
			//System.out.println("Size consistency failed");
			//System.out.println(countSize(t.getRoot())+":"+t.size());
			//return false;
		//}
		return true;
	}
	
	private static boolean checkRedRule(RBNode currNode)
	{
		if(currNode == null || currNode.getKey() < 0)
		{
			return true;
		}
		if(currNode.isRed())
		{
			boolean isLeftRed = currNode.getLeft() != null && currNode.getLeft().isRed();
			boolean isRightRed = currNode.getRight() != null && currNode.getRight().isRed();
			if(isLeftRed || isRightRed)
				return false;
		}
			
				
		boolean leftOk = checkRedRule(currNode.getLeft());
		boolean rightOk = checkRedRule(currNode.getRight());
		return leftOk && rightOk;
	}
	
	private static boolean checkBlackRule(RBNode currNode)
	{
		return minBlackPaths(currNode,0) == maxBlackPaths(currNode,0);
	}
	
	private static int minBlackPaths(RBNode currNode,int currBlackCount)
	{
		if(currNode == null || currNode.getKey() < 0)
			return currBlackCount;
		if(currNode.isRed() == false)
			currBlackCount+=1;
		return Math.min(minBlackPaths(currNode.getLeft(),currBlackCount),minBlackPaths(currNode.getRight(),currBlackCount));
		
	}
	
	private static int maxBlackPaths(RBNode currNode,int currBlackCount)
	{
		if(currNode == null|| currNode.getKey() < 0)
			return currBlackCount;
		if(currNode.isRed() == false)
			currBlackCount+=1;
		return Math.max(maxBlackPaths(currNode.getLeft(),currBlackCount),maxBlackPaths(currNode.getRight(),currBlackCount));
	}
	
	private static boolean checkIsSearchTree(RBNode currNode)
	{
		if(currNode == null)
		{
			return true;
		}
		else
		{
			return checkIsSearchTree(currNode,Integer.MIN_VALUE,Integer.MAX_VALUE);
		}
	}
	
	private static boolean checkIsSearchTree(RBNode currNode,int min,int max)
	{
		if(currNode == null)
			return true;
		if(currNode.getKey() < min || currNode.getKey() > max)
			return false;
		boolean leftOk = checkIsSearchTree(currNode.getLeft(),min,currNode.getKey());
		boolean rightOk = checkIsSearchTree(currNode.getRight(),currNode.getKey(),max);
		return  leftOk && rightOk;
	}
	
	private static int countSize(RBNode currNode)
	{
		if(currNode == null)
			return 0;
		return 1 + countSize(currNode.getLeft()) + countSize(currNode.getRight()); 
	}
	
	private static boolean checkSize(RBTree t)
	{
		return countSize(t.getRoot()) == t.size();
	}
}

