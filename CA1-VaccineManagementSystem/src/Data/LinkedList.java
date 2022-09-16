package Data;

import javafx.scene.control.ListView;

import java.sql.SQLOutput;

public class LinkedList<A> {

    private Node<A> head = null;
    private int length = 0;

    public void addElement(A element) {
        Node<A> n=new Node<>();
        n.setContents(element);
        n.next=head;
        head=n;
        length++;
    }

    public A get(int index) {
        Node<A> currentNode = head;
        if (currentNode == null || index >= length){
            return null;
        }
        int counter = 0;

        while (counter < index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode.getContents();
    }

    public void populateList(ListView lv) {
        Node<A> currentNode = head;
        while (currentNode != null) {
            lv.getItems().add(currentNode.getContents());
            currentNode = currentNode.next;
        }
    }

    public void deleteNode(int index) {
        Node<A> temp = head;
        if (head == null) return;

        length--;
        if (index == 0) {
            head = temp.next;
            return;
        }
        for (int i = 0; temp != null && i < index - 1; i++) {
            temp = temp.next;
        }

        if (temp == null || temp.next == null) return;
        Node next = temp.next.next;
        temp.next = next;
    }

    public void insertInto(int index, Node<A> nn) {
        Node<A> temp = head;
        for (int i = 0; temp != null && i < index - 1; i++) {
            temp = temp.next;
        }
        if (temp != null) {
            nn.next=temp.next;
        }

    }

    public int size() {
        return length;
    }
    public void addElement(Booth b) {
        Node n = new Node();
        n.setContents(b);
        n.next=head;
        head=n;
        length++;

    }

}
