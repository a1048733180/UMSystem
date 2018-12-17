package com.luo.base.list;

/**
 * 
 * @Description 矩阵类
 *
 */
public class Matrix {
	private int rows, columns; // 定义行数、列数
	private int[][] element; // 创建一个二维数组，存储矩阵元素

	public Matrix(int m, int n) {
		this.element = new int[m][n];
		this.rows = m;
		this.columns = n;
	}

	public Matrix(int n) {
		this(n, n);
	}

	public Matrix(int m, int n, int[][] value) {
		this(m, n);
		for (int i = 0; i < value.length && i < m; i++) {
			for (int j = 0; j < value[i].length && j < n; j++) {
				this.element[i][j] = value[i][j];
			}
		}
	}

	// 返回行数
	public int getRows() {
		return this.rows;
	}

	// 返回列数
	public int getColumns() {
		return this.columns;
	}

	// 获取某行某列的元素
	public int get(int i, int j) {
		if (i >= 0 && i < this.rows && j >= 0 && j < this.columns) {
			return this.element[i][j];
		}
		throw new IndexOutOfBoundsException("i=" + i + ", j=" + j);
	}

	public void set(int i, int j, int x) {
		if (i >= 0 && i < this.rows && j >= 0 && j < this.columns) {
			this.element[i][j] = x;
		} else
			throw new IndexOutOfBoundsException("i=" + i + ", j=" + j);
	}

	// 设置矩阵为m行n列，如果超果原数组，则扩容数组，并复制原数组
	public void setRowsColumns(int m, int n) {
		if (m > 0 && n > 0) {
			if (m > this.element.length || n > this.element[0].length) {
				// 参数指定的行数或列数较大时，扩充二维数组容量
				int[][] source = this.element;
				this.element = new int[m][n];
				for (int i = 0; i < this.rows; i++) {
					for (int j = 0; j < this.columns; j++) {
						this.element[i][j] = source[i][j];
					}
				}
			}
			this.rows = m;
			this.columns = n;
		} else
			throw new IllegalArgumentException("矩阵行列数不能<=0，m=" + m + ",n=" + n);
	}

	public String toString() {
		String str = "矩阵" + this.getClass().getName() + "(" + this.rows + "*" + this.columns + "): \n";
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				str += String.format("%6d", this.element[i][j]);
			}
			str += "\n";
		}
		return str;
	}

}
