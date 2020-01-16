package MainTest;
import java.io.*;
import java.util.*;
import Trie.*;

public class Main {
	public static void main(String[] args) {	
		ObjectInputStream objInputStream = null;
		FileInputStream inputStream = null;

		try{

		inputStream = new FileInputStream("C:\\Users\\PKNU\\Desktop\\스프링5\\objsave.ser");
		objInputStream = new ObjectInputStream (inputStream);
		Node head = (Node)objInputStream.readObject();
		
		TrieDT trie=new TrieDT(head);
		trie.PrintTrie(head);
	    System.out.println();
	   
	    //"미분계수"
	    //미..분..계..수
	   
	   char defch='미';
	   String word="";
	   word+=defch;
	   Node defnd=head.childs.get(defch);
	   ArrayList<Node> possible;
	
	   
	   while(word.length()<5) {
	   possible=trie.findPossible(defnd);
	   for(Node nd:possible) {
		   if(nd.decCh.equals("분")) {
			   defnd=nd;
			   word+=nd.decCh;
			   break;
		   }
	    }
	   }
	    
	   System.out.print(word);  //언리처블 떴었음 위의 와일 종료조건 땜에
	   objInputStream.close();
		
		
	} catch (FileNotFoundException e) {

		e.printStackTrace();

		} catch (IOException e) {

		e.printStackTrace();

		} catch (ClassNotFoundException e) {

		e.printStackTrace();

		}finally{

		if (objInputStream != null){

		try{objInputStream.close();}catch (Exception e){}

		}

		else if (inputStream != null){

		try{inputStream.close();}catch (Exception e){}

		}

		}
	}
}
