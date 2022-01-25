package com.myspring.Art.Admin.notice.VO;

public class Criteria {
 private int page; //���� ������ ��ȣ
 private int perPageNum; // �� �������� ������ �� ����
 
 public int getpageStart() { //Ư�� �������� �Խñ� ���۹�ȣ
	 return(this.page-1)*perPageNum;
 }
 
 public Criteria() {
	 this.page =1;
	 this.perPageNum = 10;
 }
 
public int getPage() {
	return page;
}
public void setPage(int page) { //�������� �������� ���� �ʰ� ����
	if(page <= 0) {
		this.page = 1;
	}else {
		this.page = page;		
	}
}
public int getPerPageNum() {
	return perPageNum;
}
public void setPerPageNum(int pageCount) { // �������� ������ �Խñ� ���� ������ �ʰ� ����
	int cnt = this.perPageNum;
	if(pageCount != cnt) {
		this.perPageNum = cnt;
	}else {
		this.perPageNum = pageCount;		
	}
}
 
 
}
