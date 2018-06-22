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
		if (i >= 0 && i < this.rows && j >= 0 && j < this.columns) {
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
		} else {
			throw new IndexOutOfBoundsException("i=" + i + ", j=" + j);
		}

	}

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
}
