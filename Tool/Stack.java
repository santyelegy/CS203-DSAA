package Tool;

/**
 * it is something similar to a stack,maybe is better to call it an array list
 * it have the both operation in a queue and a stack,since sometimes we may mix the operations together
 * @param <T>
 */
class Stack<T> {
    private ListNode<T> first;
    private ListNode<T> top;

    public Stack(T in) {
        ListNode<T> first = new ListNode<>( in, null);
        this.first = first;
        top = first;
    }
    public Stack(){}

    public void push(T in) {
        if(top!=null) {
            ListNode<T> node = new ListNode<>(in, top);
            top.setNext(node);
            top = node;
        }else {
            top=new ListNode<>( in, null);
            first=top;
        }
    }

    public T getTop() {
        if(top!=null) {
            return top.getKey();
        }else {
            return null;
        }
    }

    public void pop() {
        if(top.getParent()!=null) {
            top = top.getParent();
            top.getChild().get(0).setFather(null);
            top.setNext(null);
        }else {
            first=null;
            top=null;
        }
    }

    private T getFirst(){
        if(first!=null) {
            return first.getKey();
        }
        return null;
    }
    private void removeFirst(){
        if(first.getChild()!=null) {
            first = first.getChild().get(0);
            first.setFather(null);
        }else {
            first=null;
            top=null;
        }
    }

}