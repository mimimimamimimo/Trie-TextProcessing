package Trie;
import java.io.Serializable;
import java.util.*;

public class TrieDT{
   Node head=new Node(" ",false);  //
   TrieDT(){
	   
   }
   public TrieDT(Node head){
	   this.head=head;
   }
   
   protected void Insert(String word) {
      String decWord=word;
      Node cur=head;   
      Node before=cur;
      char branch;
      int remainlen=decWord.length();
      int subIdx=0;
      while(remainlen>0) {
         branch=decWord.charAt(subIdx);
         
         if(cur.childs.containsKey(branch)==false) {
            Node nd=new Node(decWord.substring(subIdx),true); 
            cur.childs.put(nd.branch,nd);
            break;
         }else{
            before=cur;
            cur=cur.childs.get(branch);
            for(int i=0;i<cur.decChlen;i++) {
               if(cur.decCh.charAt(i)!=decWord.charAt(subIdx)) {
                  midInsert(before,cur,i,decWord,subIdx);
                  remainlen=0;
                  break;
               }
               subIdx++;
               remainlen--;
               if(remainlen==0) {
               
                  if(i<cur.decChlen-1) {
                     midInsert(before,cur,i+1,decWord,subIdx);
                     break;
                  }
                  existInsert(cur);
                  break;
               }     
            }
         }         
      }
   }
   
   protected void existInsert(Node cur) {
      cur.setLeaf();
   }
   
   
   protected void midInsert(Node before,Node cur,int curIdx,String decWord,int subIdx) {
      String front=cur.decCh.substring(0,curIdx);
      String back=cur.decCh.substring(curIdx,cur.decChlen);
      String tail=decWord.substring(subIdx,decWord.length());

      Node fr=new Node(front,false);
      before.childs.put(fr.branch,fr);
      cur.setDecCh(back);
      fr.childs.put(cur.branch,cur);
     
      if(tail.length()==0) {
         fr.setLeaf();
         return;
      }else {
      Node tl=new Node(tail,true);
      fr.childs.put(tl.branch,tl);   
      }
      
   }
   
   
   public ArrayList<ArrayList<Node>> Find(ArrayList<String> keys,boolean printbool) {
      
      ArrayList<ArrayList<Node>> ans = new ArrayList<ArrayList<Node>>();
      for(String key:keys) {
         ans.add(subFind(key));
      }
      
      if(printbool==true) {  //ans는 무조건 1이상으로 크기 잡힌다
         int i=0;
         for(ArrayList<Node> list:ans) {
            if(list==null) {
               System.out.print(keys.get(i++)+": ");
               System.out.println("no have childs");
               continue;
            }
            System.out.print(keys.get(i++)+": ");
            for(Node nd:list) {
               System.out.print(nd.decCh+"("+nd.percent+")"+" ");
            }
            System.out.println();
         }
      }
      
      return ans;
   }
     
   
   public ArrayList<Node> subFind(String key) {
      String decKey=key;
      int decKidx=0;
      Node cur=head;
      char branch;
      ArrayList<Node> anslist=new ArrayList<Node>();
      
         while(true) {
            branch=decKey.charAt(decKidx);
            if(cur.childs.containsKey(branch)) {
               cur=cur.childs.get(branch);  //두번 검색해야하는지
               for(int i=0;i<cur.decChlen;i++) {
                  if(decKey.charAt(decKidx)!=cur.decCh.charAt(i))
                     return null;
                  decKidx++;
                  if(decKidx==decKey.length()) {
                	  if(i==cur.decChlen-1)
                          return Found(cur,anslist);
                	  else
                		  return null;
                  }
               }
               
            }else {
               return null;
            }
         }
   }
   
   ArrayList<Node> Found(Node cur,ArrayList<Node> anslist) {
      
    //  if(cur.leaf==true)  //자체단어는 처음 헤드에서는 영역침범 일어난거 포함해줘야함
     //    anslist.add(cur);
      Set<Character> s= cur.childs.keySet();
      for(Character next:s) {
    	  anslist.add(cur.childs.get(next));
      }
	 return anslist;
   }  //공간위해선 압축,검색위해선 압축안됨..근데 생각해보니 짤리기 전에 먼저 제시라 괜찮.첫부분 예외처리ㅑㅜㅅ
   
   public void PrintTrie(Node cur) {
      
      if(cur.childs.isEmpty()==true)
         return;
      Set<Character> s= cur.childs.keySet();      
      for(Character next:s) {
         PrintTrie(cur.childs.get(next));
      }
      
      try {
         System.out.println(cur.decCh+":");
         Node child;
         for(Character next:s) {
            child=cur.childs.get(next);
            String leaf=(child.leaf)?"E":"M";
            System.out.print(child.decCh+"/"+leaf+" "+child.occurCnt+"  ");
         }
      
      System.out.println();
      }catch(NullPointerException e){
         System.out.println("Print Error");
      }
      
   }
  
   protected int decidePercent(Node cur) {
	   if(cur.childs.isEmpty()==true) {
		   cur.occurCnt++;
		   return 1;  
	   }
	   if(cur.leaf==true)
		   cur.occurCnt++;
	   Set<Character> s= cur.childs.keySet();      
	   for(Character next:s) {
		   cur.occurCnt+=decidePercent(cur.childs.get(next));
	   }
	   
	   
	   for(Character next:s) {
		   Node ch=cur.childs.get(next);
		   int q=cur.occurCnt;
		   if(cur.leaf==true) q--;
		   ch.percent=(double)ch.occurCnt/q;
		 //  System.out.println(ch.percent+" "+ch.occurCnt+" "+cur.occurCnt);
	   }
	   return cur.occurCnt;
   }
   
   
   
   public ArrayList<Node> findPossible(Node cur) {
 	  ArrayList<Node> res=new ArrayList<Node>();
 	  Set<Character> s= cur.childs.keySet();
 	  for(Character ch:s) {
 		  res.add(cur.childs.get(ch));
 	  }
 	  //sort
 	  return res;
   }
   
   
   
}



/*초중종성 실패썰,논문

System.out.println('ㄱ'-0x3131);
System.out.println('ㄲ'-0x3131);
System.out.println('ㄳ'-0x3131);
System.out.println('ㄴ'-0x3131);
System.out.println('ㄵ'-0x3131);
System.out.println('ㄶ'-0x3131);
System.out.println('ㄷ'-0x3131);
System.out.println('ㄸ'-0x3131);
System.out.println('ㄹ'-0x3131);
System.out.println('ㄺ'-0x3131);
System.out.println('ㄻ'-0x3131);
System.out.println('ㄼ'-0x3131);
System.out.println('ㄽ'-0x3131);
System.out.println('ㄾ'-0x3131);
System.out.println('ㄿ'-0x3131);
System.out.println('ㅀ'-0x3131);
System.out.println('ㅁ'-0x3131);
System.out.println('ㅂ'-0x3131);
System.out.println('ㅄ'-0x3131);
System.out.println('ㅃ'-0x3131);
System.out.println('ㅅ'-0x3131);
System.out.println('ㅆ'-0x3131);
System.out.println('ㅇ'-0x3131);
System.out.println('ㅈ'-0x3131);
System.out.println('ㅉ'-0x3131);
System.out.println('ㅊ'-0x3131);
System.out.println('ㅋ'-0x3131);
System.out.println('ㅌ'-0x3131);
System.out.println('ㅍ'-0x3131);
System.out.println('ㅎ'-0x3131);
System.out.println('ㅏ'-0x3131);
System.out.println('ㅐ'-0x3131);
System.out.println('ㅑ'-0x3131);
System.out.println('ㅒ'-0x3131);
System.out.println('ㅓ'-0x3131);
System.out.println('ㅔ'-0x3131);
System.out.println('ㅕ'-0x3131);
System.out.println('ㅖ'-0x3131);
System.out.println('ㅗ'-0x3131);
System.out.println('ㅘ'-0x3131);
System.out.println('ㅙ'-0x3131);
System.out.println('ㅚ'-0x3131);
System.out.println('ㅛ'-0x3131);
System.out.println('ㅜ'-0x3131);
System.out.println('ㅝ'-0x3131);
System.out.println('ㅞ'-0x3131);
System.out.println('ㅟ'-0x3131);
System.out.println('ㅠ'-0x3131);
System.out.println('ㅡ'-0x3131);
System.out.println('ㅢ'-0x3131);
System.out.println('ㅣ'-0x3131);

*/
//상속관계 f4
//stringbuffer에서 string으로 object 통해 형변환..이래도 됨?
//stringbuffer 스레드 안전하고 문자열추가 너무 많아서 무조건 써야하는데..
//근데 저건 mutable이라서 안쪼개지잖아? 그럼 결국 객체 생성해야하는데 string이랑똑같?
//아,아예 맵을 만들어서 hear에 문자열말고 사상값 위치넘버를 넣어두자.
//혹시 내가 봤던 해시테이블 얘기가 이건가..