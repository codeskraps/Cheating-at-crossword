package ie.gmit;

public class TernaryTreeNode {

	private char c;
	private boolean isWord;
	private TernaryTreeNode leftNode;
	private TernaryTreeNode equaNode;
	private TernaryTreeNode righNode;
	
	public TernaryTreeNode (char c, boolean isWord) {
		setC(c);
		setWord(isWord);
		setLeftNode(null);
		setEquaNode(null);
		setRighNode(null);
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public boolean isWord() {
		return isWord;
	}

	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}

	public TernaryTreeNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(TernaryTreeNode leftNode) {
		this.leftNode = leftNode;
	}

	public TernaryTreeNode getEquaNode() {
		return equaNode;
	}

	public void setEquaNode(TernaryTreeNode equaNode) {
		this.equaNode = equaNode;
	}

	public TernaryTreeNode getRighNode() {
		return righNode;
	}

	public void setRighNode(TernaryTreeNode righNode) {
		this.righNode = righNode;
	}
}
