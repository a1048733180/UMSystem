package com.luo.base.list;

/**
 * @Description 顺序链表
 * @param <T>
 */
public class SeqList<T> {
	protected Object[] element; // 对象数组存储的元素
	protected int n; // 元素长度

	// 构造长度为length的空表
	public SeqList(int length) {
		this.element = new Object[length];
		this.n = 0;
	}

	// 默认空间大小为64
	public SeqList() {
		this(64);
	}

	public SeqList(T[] values) {
		this(values.length);
		for (int i = 0; i < values.length; i++) {
			this.element[i] = values[i]; // 这里是对象引用赋值
		}
		this.n = values.length;
	}

	//返回长度
	public int size() {
		return this.n;
	}
	// 插入x作为第i个元素
	public int insert(int i, T x) {
		if (x == null) {
			throw new NullPointerException("x == null");
		}
		if (i < 0) {
			i = 0;
		}
		if (i > this.n) {
			i = this.n;
		}
		Object[] source = this.element;
		if (this.n == element.length) {
			this.element = new Object[source.length * 2];
			for (int j = 0; j < i; j++) {
				this.element[j] = source[j];
			}
		}
		for (int j = this.n - 1; j >= i; j--) {
			this.element[j + 1] = source[j];
		}
		this.element[i] = x;
		this.n++;
		return i;
	}

	// 尾插入
	public int insert(T x) {
		return this.insert(this.n, x);
	}

	// 获取某个元素
	public T get(int i) {
		if (i >= 0 && i < this.n) {
			return (T)this.element[i];
		}
		return null;
	}

//	@Override
//	public String toString() {
//		String str = "( ";
//		if (this.n > 0) {
//			str += element[0].toString();
//		}
//		for (int i = 1; i < this.n; i++) {
//			str += ", " + this.element[i].toString();
//		}
//		return str + ")";
//	}
}
