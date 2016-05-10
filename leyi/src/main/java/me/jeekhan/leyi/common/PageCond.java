package me.jeekhan.leyi.common;
/**
 * ��ҳ������Ϣ��
 * @author Jee Khan
 *
 */
public class PageCond {
	public static int DEFAULT_PAGESIZE = 20;
	private int begin;		//��ʼ��¼��
	private int pageSize;	//ÿҳ��С
	private int count;		//�ܼ�¼��
	public PageCond(int begin ,int pageSize,int count){
		this.begin = begin;
		this.pageSize = pageSize;
		this.count = count;
	}
	public PageCond(int begin){
		this.begin = begin;
		this.pageSize = DEFAULT_PAGESIZE;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
