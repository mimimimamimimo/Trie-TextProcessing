package Trie;
import java.io.Serializable;
import java.util.*;

public class TrieDT{   //트라이 자료구조에 대해서 검색,삽입,프린트
   Node head=new Node(" ",false);  //루트 노드를 만듬(트라이의 루트노드는 공백이다)
   
   TrieDT(){                       //생성자
	   
   }
   
   public TrieDT(Node head){       //생성자에서 루트노드 초기화
	   this.head=head;
   }
   
   protected void Insert(String word) {       //노드를 추가
      String decWord=word;         //단어를 쪼갤수 있도록 준비한다
      Node cur=head;               //탐색을 시작할 위치를 head로 조정한다
      Node before=cur;			//이전 위치를 저장
      char branch;              //쪼개진 단어의 첫 글자
      int remainlen=decWord.length();     //남은 단어의 길이
      int subIdx=0;                       
      while(remainlen>0) {            //남은 단어가 없을 때까지 반복 
         branch=decWord.charAt(subIdx);  //남은 단어의 첫 문자를 브랜치로 저장
         
         if(cur.childs.containsKey(branch)==false) {  //현재 노드의 자식중 브랜치를 인덱스로 하는 자식이 비어있는 경우
            Node nd=new Node(decWord.substring(subIdx),true);   //브랜치로 들어갈 만큼만 잘라낸다
            cur.childs.put(nd.branch,nd); //브랜치에 추가한다
            break;
         }else{  //차있는 경우..
            before=cur;   //이전 위치를 저장해두고
            cur=cur.childs.get(branch);  //그 브랜치로 이동한다
            for(int i=0;i<cur.decChlen;i++) {   //겹치는 문자열을 마저 읽고
               if(cur.decCh.charAt(i)!=decWord.charAt(subIdx)) {  //처음 달라지는 부분에서
                  midInsert(before,cur,i,decWord,subIdx);  //문자열을 분해하여 추가한다.
                  remainlen=0;  //남은 수는 0으로 초기화 시킨다
                  break;
               }
               subIdx++;   //겹치는 문자열이 늘어나면
               remainlen--;  //남은 문자열은 줄어든다
               if(remainlen==0&&i<cur.decChlen-1) {  //남은 문자열이 없으면 
            	   midInsert(before,cur,i+1,decWord,subIdx); //그 부분을 추가하고
                   break;//그런데도 아직 읽어야할 부분이 남아있다면
               }else if(remainlen==0) {  //추가시 이미 동일한 단어가 존재하는 경우 단말 표시를 한다
            	   cur.setLeaf();//정확하게 같으면 그 노드에 단말 설정을 해준다
            	   break;
               }
            }
         }         
      }
   }
 
   
   protected void midInsert(Node before,Node cur,int curIdx,String decWord,int subIdx) {  //추가시 존재하는 노드를 분해해야 하는 경우
      String front=cur.decCh.substring(0,curIdx);   //현재 노드의 문자열을 front,back으로 나눈다
      String back=cur.decCh.substring(curIdx,cur.decChlen);
      String tail=decWord.substring(subIdx,decWord.length());  //검색할 단어의 남은 문자열을 tail로

      Node fr=new Node(front,false);  //front를 생성하여
      before.childs.put(fr.branch,fr); //원래 달려있던 자리에 넣어준다
      cur.setDecCh(back);  //앞이 떨어져나간 검색할 단어의 뒷부분으로 초기화를 해주고
      fr.childs.put(cur.branch,cur);  //뒷부분은 앞부분에 단다
     
      if(tail.length()==0) {  //만약 꼬리가 없으면 앞부분을 단말로 지정한다
         fr.setLeaf();
         return;
      }else {
      Node tl=new Node(tail,true);   //있으면 앞부분에 단다
      fr.childs.put(tl.branch,tl);   
      }
      
   }
   
   
   public ArrayList<ArrayList<Node>> Find(ArrayList<String> keys,boolean printbool) {  //여러 글자들에 대해 각각 검색해본다, printbool이 참이면 콘솔에 출력한다 
      
      ArrayList<ArrayList<Node>> ans = new ArrayList<ArrayList<Node>>();
      for(String key:keys) {  //key는 검색하는 글자 하나이다
         ans.add(subFind(key));  //key에 대한 각 답을 ans에 모두 추가한다
      }
      
      if(printbool==true) {  //ans를 출력한다. ans는 무조건 1이상으로 크기 잡힌다
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
     
   
   public ArrayList<Node> subFind(String key) {  //하나의 문자(문자열)에 대해 검색한다
      String decKey=key;
      int decKidx=0;
      Node cur=head;
      char branch;
      ArrayList<Node> anslist=new ArrayList<Node>();
      
         while(true) {
            branch=decKey.charAt(decKidx);
            if(cur.childs.containsKey(branch)) { //자식노드들이 map으로 달려있기 떄문에 해당 branch가 있는지 없는지 1의 시간으로 알 수 있다.
               cur=cur.childs.get(branch);  //있으면 그 노드로 이동한다
               for(int i=0;i<cur.decChlen;i++) {   //Insert랑 비슷하게 겹치는 문자열을 떼면서 간다
                  if(decKey.charAt(decKidx)!=cur.decCh.charAt(i))
                     return null;
                  decKidx++;  //현재 검색한 위치 증가
                  if(decKidx==decKey.length()) {  //모든 글자를 찾았으면 
                	  if(i==cur.decChlen-1)
                          return Found(cur,anslist);  //찾아진 위치의 노드의 자식들을 가져온다
                	  else
                		  return null;
                  }
               }
               
            }else {
               return null;
            }
         }
   }
   
   ArrayList<Node> Found(Node cur,ArrayList<Node> anslist) { //찾아진 위치의 노드의 자식들을 가져오는 함수
      
    //  if(cur.leaf==true)  //자체단어는 처음 헤드에서는 영역침범 일어난거 포함해줘야함
     //    anslist.add(cur);
      Set<Character> s= cur.childs.keySet(); //근데 노드를 줘야하는거 아닌가..
      for(Character next:s) {
    	  anslist.add(cur.childs.get(next));
      }
	 return anslist;
   }  //공간위해선 압축,검색위해선 압축안됨..근데 생각해보니 짤리기 전에 먼저 제시라 괜찮.첫부분 예외처리ㅑㅜㅅ
   
   public void PrintTrie(Node cur) {  //나중에
      
      if(cur.childs.isEmpty()==true) //없으면 가지말고
         return;
      Set<Character> s= cur.childs.keySet();      
      for(Character next:s) {  //있으면 그 묹
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
  
   protected int decidePercent(Node cur) { //재귀로 한번 쭉 돌려서 나올 확률을 각 노드에 붙여준다. 배치처리가 필요하다.
	   if(cur.childs.isEmpty()==true) {
		   cur.occurCnt++;
		   return 1;    //빈 경우 100%다
	   }
	   if(cur.leaf==true) //비진 않았으면 다음으로 가기 전 가중치를 올려준다
		   cur.occurCnt++;
	   Set<Character> s= cur.childs.keySet();      //자식들을 모두 찾아서
	   for(Character next:s) {  //모두 방문하며 가중치를 각각 올려준다
		   cur.occurCnt+=decidePercent(cur.childs.get(next));
	   }
	   
	   
	   for(Character next:s) {  //한 가지가 끝났으면 그 가지에 대해 모든 확률값을 붙여줄 것이다
		   Node ch=cur.childs.get(next);  // 키가 문자일때, 값은 노드다.
		   int q=cur.occurCnt; //노드가 적중한 횟수중에서 
		   if(cur.leaf==true) q--;  //해당 노드가 단말이면 차감해준다
		   ch.percent=(double)ch.occurCnt/q;  //부모노드가 사상된 횟수중의 이 노드가 사상된 횟수가 곧 확률이다
		 //  System.out.println(ch.percent+" "+ch.occurCnt+" "+cur.occurCnt);
	   }
	   return cur.occurCnt;
   }
   
   
   //캐시구현 가능한가?
   
   
   public ArrayList<Node> findPossible(Node cur) {  //잉?
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