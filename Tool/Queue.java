package Tool;
public class Queue<T> {
    private int size;
    private T[] queue;
    private int front=0;
    private int rear=0;
    /**
     *because we cannot 'new' a template array,so I let user to input an empty array base queue to implement a queue
     *需要测试
     */
    public Queue(T[] empty){
        size=0;
        queue=empty;
    }
    public void inQueue(T i){
        if(size==queue.length){
            throw new IndexOutOfBoundsException();
        }
        queue[rear]=i;
        rear++;
        rear%=queue.length;
        size++;
    }
    public void deQueue(){
        front++;
        size--;
    }
    public T getHead(){
        return queue[front];
    }
}
