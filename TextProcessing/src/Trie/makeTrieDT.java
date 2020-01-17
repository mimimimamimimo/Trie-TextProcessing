package Trie;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class makeTrieDT {   //문제파일을 입력으로 트라이 객체를 생성
	   public static void main(String[] args) throws IOException{
		     FileOutputStream fileStream = new FileOutputStream("C:\\Users\\KCE\\Desktop\\quickstart\\TextProcessing\\objsave.ser");
		     ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		     
		     TrieDT trie=new TrieDT();    		    
		     File file = new File("C:\\Users\\KCE\\Desktop\\quickstart\\TextProcessing\\test.txt");  //hwp파일 문제를 메모장에 옮기면 수식 제외됨
		     BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));  //bf리더와 스케너 차이?
	         String str;	
		       //  Scanner sc=new Scanner(file);  //여기로 중단점 걸면 안됨
		    //널익셉션->파일은 있는데 글을 못읽음->한글일때만 안됨.    
		      try {
		         while(br.ready()) {   //단어가 있을 때마다
		            str=br.readLine();
		            String array[]=str.split(" ");
		            for(int s=0;s<array.length;s++) {
		            	str=array[s];
		            	StringBuilder strbd=new StringBuilder();  //계속 편집할 것이기 떄문이다
		            	for(int i=0;i<str.length();i++) {  //모든 글자중에서
		            		if(Character.getType(str.charAt(i))==5)  //한글인 것만 따로 떼와서 strbd에 문자열로 만든다
		            			strbd.append(str.charAt(i));
		            	}
		            	trie.Insert(strbd.toString());
		            }
		         }

		      } catch (FileNotFoundException e) {
		         e.printStackTrace();
		      }  
		      trie.PrintTrie(trie.head);
		      
		      trie.decidePercent(trie.head);
		      
		      objectOutputStream.writeObject(trie.head);
		      objectOutputStream.close();
		      
		      /*
		      trie.Insert("미지수");
		      trie.Insert("미분");
		      trie.Insert("미분계수");
		      trie.Insert("지수");
		      trie.Insert("미지상수");
		      */
	
		     // trie.PrintTrie(trie.head);

		   }
}


/*
 * scanner 앞뒤로 에러뜨는 이유
 * 1.인코딩 utf-8로 안해줬을 때
 * 2.그냥 스캐너 근처에서 중단점 걸 때
 * 3.개행문자 처리 안한채로 받을 때
 * */
 