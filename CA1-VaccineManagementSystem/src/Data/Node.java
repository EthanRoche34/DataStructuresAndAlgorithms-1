package Data;

public class Node<A> {
    public Node<A> next = null;
    private A contents;

    public void setContents(A c) {
        contents=c;
    }

    public A getContents() {
        return contents;
    }
}
