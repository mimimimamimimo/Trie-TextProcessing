package Trie;
import java.io.Serializable;
import java.util.*;

public class Node implements Serializable{
	private static final long serialVersionUID = 1L;

	public String decCh;
	int decChlen;
	char branch;
	boolean leaf=false;
	int occurCnt=0;
	double percent=0.0;
	public Map<Character,Node> childs=new HashMap<Character,Node>();
	
	public Node(String decWord,boolean leaf){
		setDecCh(decWord);
		if(leaf==true) 
			setLeaf();
	}
	protected void setDecCh(String decCh) {
		this.decCh=decCh;
		decChlen=decCh.length();
		branch=decCh.charAt(0);
	}
	protected void setLeaf() {
		this.leaf=true;
	}

}


//Ȯ�� �׳� �ڽļ��� ��ü��
//ª�� ������� �����
//�������� �ּҹް� ������ �ٽ� ��
//b�ɰ����� ���� �ʿ����
//�Ẹ���� �־�