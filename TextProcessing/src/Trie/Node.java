package Trie;
import java.io.Serializable;
import java.util.*;

public class Node implements Serializable{  //트라이를 구성하는 노드들을 직렬화하기 위해서 Serializable 상속
	private static final long serialVersionUID = 1L;

	public String decCh;  //중간글자(현위치)
	int decChlen;         //중간글자의 길이
	char branch;          //중간글자의 첫문자
	boolean leaf=false;   //중간글자가 마지막 글자인지
	int occurCnt=0;       //발견된 횟수
	double percent=0.0;   //발견될 확률?
	public Map<Character,Node> childs=new HashMap<Character,Node>();  //현 노드의 자식노드들(다음글자들)
	
	public Node(String decWord,boolean leaf){   //노드 생성할 때 중간글자는 항상 설정하되 단말이면 leaf를 참으로 설정
		setDecCh(decWord);
		if(leaf==true) 
			setLeaf();
	}
	protected void setDecCh(String decCh) {  //중간글자를 설정. 
		this.decCh=decCh;          //중간글자 초기화
		decChlen=decCh.length();   //중간글자에 대한 길이
		branch=decCh.charAt(0);    //중간글자의 첫글자
	}
	protected void setLeaf() {   //단말노드임을 표시
		this.leaf=true;
	}
	@Override
	public String toString() {
		return "Node [decCh=" + decCh + ", decChlen=" + decChlen + ", branch=" + branch + ", leaf=" + leaf
				+ ", occurCnt=" + occurCnt + ", percent=" + percent + ", childs=" + childs + "]";
	}
	
	

}


//Ȯ�� �׳� �ڽļ��� ��ü��
//ª�� ������� �����
//�������� �ּҹް� ������ �ٽ� ��
//b�ɰ����� ���� �ʿ����
//�Ẹ���� �־�