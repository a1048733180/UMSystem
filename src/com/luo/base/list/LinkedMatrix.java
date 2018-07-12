package com.luo.base.list;

/*
 *  稀疏矩阵三元组单链表
 */
public class LinkedMatrix {
	public int rows, columns;

	public SeqList<SortedSinglyList<Triple>> rowlist;

	public LinkedMatrix(int m, int n) {
		if (m > 0 && m > 0) {
			this.rows = m;
			this.columns = n;
			this.rowlist = new SeqList<SortedSinglyList<Triple>>();
			for (int i = 0; i < m; i++) {
				this.rowlist.insert(new SortedSinglyList<Triple>());
			}
		} else
			throw new IllegalArgumentException("矩阵行列数不能<=0， m=" + m + ", n=" + n);
	}

	public LinkedMatrix(int m) {
		this(m, m);
	}

	public LinkedMatrix(int m, int n, Triple[] tris) {
		this(m, n);
		for (int i = 0; i < tris.length; i++) {
			this.set(tris[i]);
		}
	}

	public void set(int m, int n, int x) {
		this.set(new Triple(m, n, x));
	}

	public void set(Triple tri) {
		int i = tri.row, j = tri.column;

		SortedSinglyList<Triple> link = this.rowlist.get(i);
		if (tri.value == 0) {
			link.delete(tri);
		} else {
			Triple find = link.find(tri);
			if (find != null) {
				find.value = tri.value;
			} else {
				link.insert(tri);
			}
		}
	}
	// else {
	// throw new IndexOutOfBoundsException("i=" + i + ", j=" + j);
	// }

	public void setRowsColumns(int m, int n) {
		if (m > 0 && n > 0) {
			if (m > this.rows) {
				for (int i = this.rows; i < m; i++) {
					this.rowlist.insert(new SortedSinglyList<Triple>());
				}
			}
			this.rows = m;
			this.columns = n;
		} else {
			throw new IllegalArgumentException("矩阵行列数不能<=0,m=" + m + ", n=" + n);
		}
	}

	// 返回某一行全部元素
	public Triple[] getElementsOfRow(int id) {
		// 获取某一行的链表头
		SortedSinglyList<Triple> link = this.rowlist.get(id);

		// 把链表里的内容全部输出
		Triple[] tri = new Triple[this.columns / 2];

		// 直接用链表输出全部元素时间复杂度高 采用空间换时间 转换成顺序表 再输出
		SeqList<Triple> linkList = link.traverse();
		for (int i = 0; i < linkList.size(); i++) {
			tri[i] = linkList.get(i);
		}

		return tri;
	}
}
