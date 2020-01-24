package Tool;

import java.util.ArrayList;

public interface Node<T> {
    T getKey();
    ArrayList<Node<T>> getChild();
    Node<T> getParent();
}
