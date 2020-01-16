package Trie;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class makeTrieDT {
	   public static void main(String[] args) throws IOException{
		     FileOutputStream fileStream = new FileOutputStream("C:\\Users\\PKNU\\Desktop\\스프링5\\objsave.ser");
		     ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		     
		     TrieDT trie=new TrieDT();    		    
		     File file = new File("C:\\Users\\PKNU\\Desktop\\스프링5\\test.txt");  //hwp파일 문제를 메모장에 옮기면 수식 제외됨
		      try {
		    	
		      //   BufferedReader bf=new BufferedReader(new FileReader(file));  //bf리더와 스케너 차이?
		         Scanner sc=new Scanner(file);  //여기로 중단점 걸면 안됨
		         StringBuilder strbd=new StringBuilder();
		         String str;			         
		         while(sc.hasNext()) {
		            str=sc.nextLine();
		            for(int i=0;i<str.length();i++) {
		            	if(Character.getType(str.charAt(i))==5)
		            		strbd.append(str.charAt(i));
		            	else {
		            		trie.Insert(strbd.toString());
		            		strbd=new StringBuilder();
		            	}
		            }
		            if(strbd.length()>0) {  //개행문자 처리안되서 예외
		            	trie.Insert(strbd.toString());
	            		strbd=new StringBuilder();   //수정가능하면 다시
		            }
		         }
		        
		      } catch (FileNotFoundException e) {
		         e.printStackTrace();
		      }  
		      
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
 