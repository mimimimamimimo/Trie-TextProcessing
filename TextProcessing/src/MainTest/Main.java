package MainTest;
import java.io.*;
import java.util.*;
import Trie.*;

public class Main {
	public static void main(String[] args) throws IOException {	//저장된 트라이 파일에서 데이터를 조회하는 테스트 진행하는 클래스
		File file=new File("C:\\Users\\KCE\\Desktop\\T.txt");
		

		
	
		
		ObjectInputStream objInputStream = null;    
		FileInputStream inputStream = null;

		try{

		inputStream = new FileInputStream("C:\\Users\\KCE\\Desktop\\quickstart\\TextProcessing\\objsave.ser"); //트라이 파일 경로 설정
		objInputStream = new ObjectInputStream (inputStream);
		Node head = (Node)objInputStream.readObject();    //head에는 트라이의 첫 번째 노드를 담는다
		
		TrieDT trie=new TrieDT(head); //트라이 객체를 생성하여 head로 초기화해준다 
		trie.PrintTrie(head);         //print : 트라이 내의 모든 node를 출력하는 함수 
	    System.out.println();      //?head안써도 되게 만들기!
	   
	    //"미분계수"
	    //미..분..계..수
	    //?없애기 위에
	   char defch='미';   //                
	   String word="";
	   word+=defch;
	   Node defnd=head.childs.get(defch);
	   ArrayList<Node> possible;
	
	   
	   while(word.length()<5) { //다섯자리 이내 단어들에 한해서
	   possible=trie.findPossible(defnd);  //가능한 다음 문자를 모두 고른다. 정렬은?
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
