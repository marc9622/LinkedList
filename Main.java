import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class Main {
  static public void main(String[] args) {

  }
}

class LinkedListIterator<Type> implements Iterator<Type> {

  Node<Type> currentNode;

  LinkedListIterator(LinkedList<Type> list) {
    this.currentNode = new Node<Type>(null, list.first);
  }

  public boolean hasNext() {
    return currentNode.next != null;
  }

  public Type next() {
    currentNode = currentNode.next;
    return currentNode.value;
  }
}

class LinkedList<Type> implements Iterable<Type> {

  Node<Type> first = null;

  //#region CONSTRUCTORS
  LinkedList() {

  }

  LinkedList(Type value) {
    add(value);
  }

  LinkedList(Node<Type> node) {
    first = node;
  }

  LinkedList(Type[] array) {
    add(array);
  }

  @SuppressWarnings("unchecked")
  LinkedList(LinkedList<? super Type> list) {
    add((LinkedList<Type>)list);
  }
  //#endregion

  Object[] toArray() {
    Object[] array = new Object[size()];
    Node<Type> currentNode = first;
    for(int i = 0; i < array.length; i++) {
      array[i] = currentNode.value;
      currentNode = currentNode.next;
    }
    return array;
  }

  ArrayList<Type> toArrayList() {
    ArrayList<Type> arrayList = new ArrayList<Type>();
    for(Type i : this) {
      arrayList.add(i);
    }
    return arrayList;
  }

  void printAll() {
    System.out.println(toString());
  }

  public String toString() {
    String string = "";
    for(Type i : this) {
      if(i == null)
        string += "[null]";
      else
        string += "[" + i.toString() + "]";
    }
    return string;
  }

  public LinkedList<Type> clone() {
    return new LinkedList<Type>(this);
  }

  //#region EQUALS
  public boolean equals(Object object) {
    if(!super.equals(object) || object == null || !(object instanceof LinkedList<?>))
      return false;
    return equals((LinkedList<?>) object);
  }

  boolean equals(LinkedList<Type> list) {
    int sizeThis = size();
    int sizeTarget = list.size();
    if(sizeThis != sizeTarget)
      return false;
    Node<Type> currentNodeThis = first;
    Node<Type> currentNodeTarget = list.first;
    for(int i = 0; i < sizeThis; i++) {
      if(currentNodeThis.value != currentNodeTarget.value)
        return false;
      currentNodeThis = currentNodeThis.next;
      currentNodeTarget = currentNodeTarget.next;
    }
    return true;
  }
  //#endregion

  void shuffle() {
    int size = size();
    Random r = new Random();
    for(int i = 0; i < size; i++) {
      swap(i, r.nextInt(size));
    }
  }

  //#region SWAP
  void swap(int indexA, int indexB) {
    if(indexA == indexB)
      return;
    Node<Type> nodeA = getNode(indexA);
    Node<Type> nodeB = getNode(indexB);
    Type temp = nodeA.value;
    nodeA.value = nodeB.value;
    nodeB.value = temp;
  }

  void swap(Node<Type> nodeA, Node<Type> nodeB) {
    swap(indexOf(nodeA), indexOf(nodeB));
  }
  //#endregion

  //#region ADD
  Node<Type> add(Type value) {
    if(first == null) {
      first = new Node<Type>(value);
      return first;
    }
    getLastNode().next = new Node<Type>(value);
    return null; 
  }

  Node<Type> add(int index, Type value) {
    if(first == null || index == 0) {
      Node<Type> newNode = new Node<Type>(value, first);
      first = newNode;
      return first;
    }
    Node<Type> previousNode = getNode(index - 1);
    if(previousNode.next == null) {
      previousNode.next = new Node<Type>(value);
      return previousNode.next;
    }
    Node<Type> oldNode = previousNode.next;
    previousNode.next = new Node<Type>(value, oldNode);
    return previousNode.next;
  }

  void add(Type[] array) {
    for(int i = 0; i < array.length; i++) {
      add(array[i]);
    }
  }

  void add(int index, Type[] array) {
    for(int i = 0; i < array.length; i++) {
      add(index + i, array[i]);
    }
  }

  void add(LinkedList<Type> list) {
    int listSize = list.size();
    for(int i = 0; i < listSize; i++) {
      add(list.get(i));
    }
  }

  void add(int index, LinkedList<Type> list) {
    int listSize = list.size();
    for(int i = 0; i < listSize; i++) {
      add(index + i, list.get(i));
    }
  }
  //#endregion

  //#region REMOVE
  Node<Type> remove(int index) {
    if(index == 0) {
      Node<Type> removedNode = first;
      first = first.next;
      return removedNode;
    }
    Node<Type> previousNode = getNode(index - 1);
    Node<Type> removedNode = previousNode.next;
    Node<Type> nextNode = removedNode.next;
    previousNode.next = nextNode;
    return removedNode;
  }

  Node<Type> remove(Node<Type> target) {
    Node<Type> previousNode = getPreviousNode(target);
    Node<Type> removedNode = previousNode.next;
    Node<Type> nextNode = previousNode.next.next;
    previousNode.next = nextNode;
    return removedNode;
  }
  //#endregion

  void set(int index, Type value) {
    getNode(index).value = value;
  }

  Type get(int index) {
    return getNode(index).value;
  }

  //#region INDEX OF
  int indexOf(Type target) {
    int index = 0;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.value == target)
        return index;
      currentNode = currentNode.next;
      index++;
    }
    return -1;
  }

  int indexOf(Node<Type> target) {
    int index = 0;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode == target)
        return index;
      currentNode = currentNode.next;
      index++;
    }
    return -1;
  }
  //#endregion

  //#region CONTAINS
  boolean contains(Type target) {
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.value == target)
        return true;
      currentNode = currentNode.next;
    }
    return false;
  }

  boolean contains(Node<Type> target) {
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode == target)
        return true;
      currentNode = currentNode.next;
    }
    return false;
  }
  //#endregion

  void setSize(int size) {
    if(size == 0)
      clear();
    if(first == null)
      first = new Node<Type>(null);
    size--;
    Node<Type> current = first;
    for(int i = 0; i < size; i++) {
      if(current.next == null) {
        int nodesToAdd = size - i - 1;
        Node<Type> currentToAdd = new Node<Type>(null);
        for(int j = 0; j < nodesToAdd; j++) {
          currentToAdd = new Node<Type>(null, currentToAdd);
        }
        current.next = currentToAdd;
        return;
      }
      current = current.next;
    }
    current.next = null;
  }

  int size() {
    int size = 0;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      size++;
      currentNode = currentNode.next;
    }
    return size;
  }

  void clear() {
    first = null;
  }

  boolean isEmpty() {
    return first == null;
  }

  boolean hasNext(int index) {
    return getNode(index).next != null;
  }

  Node<Type> getLastNode() {
    if(first == null)
      return null;
    Node<Type> currentNode = first;
    while(currentNode.next != null)
      currentNode = currentNode.next;
    return currentNode;
  }

  Node<Type> getPreviousNode(Node<Type> target) {
    Node<Type> currentNode = first;
    while(currentNode.next != null) {
      if(currentNode.next == target)
        return currentNode;
      currentNode = currentNode.next;
    }
    return null;
  }

  Node<Type> getNode(int index) {
    Node<Type> currentNode = first;
    for(int i = 0; i < index; i++)
      currentNode = currentNode.next;
    return currentNode;
  }

  public Iterator<Type> iterator() {
    return new LinkedListIterator<Type>(this);
  }
}

class IntegerLinkedList extends LinkedList<Integer> {

  //#region CONSTRUCTORS
  IntegerLinkedList() {
    super();
  }

  IntegerLinkedList(Integer value) {
    super(value);
  }

  IntegerLinkedList(Node<Integer> node) {
    super(node);
  }

  IntegerLinkedList(Integer[] array) {
    super(array);
  }

  IntegerLinkedList(LinkedList<? super Integer> list) {
    super(list);
  }

  IntegerLinkedList(IntegerLinkedList list) {
    super((LinkedList<Integer>)list);
  }
  //#endregion

  //#region GET MAX/MIN
  int getMax() {
    int maxValue = first.value;
    Node<Integer> currentNode = first;
    while(currentNode != null) {
      if(currentNode.value > maxValue) {
        maxValue = currentNode.value;
      }
      currentNode = currentNode.next;
    }
    return maxValue;
  }

  int getMax(int start, int end) {
    int maxValue = first.value;
    Node<Integer> currentNode = getNode(start);
    for(int i = 0; i < end - start; i++) {
      if(currentNode.value > maxValue) {
        maxValue = currentNode.value;
      }
      currentNode = currentNode.next;
    }
    return maxValue;
  }

  int getMin() {
    int minValue = first.value;
    Node<Integer> currentNode = first;
    while(currentNode != null) {
      if(currentNode.value < minValue) {
        minValue = currentNode.value;
      }
      currentNode = currentNode.next;
    }
    return minValue;
  }

  int getMin(int start, int end) {
    int minValue = first.value;
    Node<Integer> currentNode = getNode(start);
    for(int i = 0; i < end - start; i++) {
      if(currentNode.value < minValue) {
        minValue = currentNode.value;
      }
      currentNode = currentNode.next;
    }
    return minValue;
  }
  //#endregion

  //#region INDEX OF MAX/MIN
  int indexOfMax() {
    Node<Integer> currentNode = first;
    int maxValue = currentNode.value;
    int maxIndex = 0;
    int size = size();
    for(int i = 0; i < size; i++) {
      if(currentNode.value > maxValue) {
        maxValue = currentNode.value;
        maxIndex = i;
      }
      currentNode = currentNode.next;
    }
    return maxIndex;
  }

  int indexOfMax(int start, int end) {
    Node<Integer> currentNode = getNode(start);
    int maxValue = currentNode.value;
    int maxIndex = start;
    for(int i = start; i < end; i++) {
      if(currentNode.value > maxValue) {
        maxValue = currentNode.value;
        maxIndex = i;
      }
      currentNode = currentNode.next;
    }
    return maxIndex;
  }

  int indexOfMin() {
    Node<Integer> currentNode = first;
    int minValue = currentNode.value;
    int minIndex = 0;
    int size = size();
    for(int i = 0; i < size; i++) {
      if(currentNode.value < minValue) {
        minValue = currentNode.value;
        minIndex = i;
      }
      currentNode = currentNode.next;
    }
    return minIndex;
  }
  
  int indexOfMin(int start, int end) {
    Node<Integer> currentNode = getNode(start);
    int minValue = currentNode.value;
    int minIndex = start;
    for(int i = start; i < end; i++) {
      if(currentNode.value < minValue) {
        minValue = currentNode.value;
        minIndex = i;
      }
      currentNode = currentNode.next;
    }
    return minIndex;
  }
  //#endregion

  //#region SORT
  void selectionSort() {
    int size = size();
    for(int i = 0; i < size; i++) {
      swap(i, indexOfMin(i, size));
    }
  }

  void insertionSort() {
    Node<Integer> sorted = new Node<Integer>(remove(0).value);
    while(first != null) {
      int newValue = remove(0).value;
      if(newValue < sorted.value) {
        sorted = new Node<Integer>(newValue, sorted);
        continue;
      }
      Node<Integer> currentNode = sorted;
      while(currentNode.next != null && newValue > currentNode.next.value) {
        currentNode = currentNode.next;
      }
      currentNode.next = new Node<Integer>(newValue, currentNode.next);
    }
    first = sorted;
  }

  void bubbleSort() {
    int size = size();
    boolean isSorted = false;
    Node<Integer> currentNode;
    for(int i = 0; i < size; i++) {
      if(isSorted)
        return;
      isSorted = true;
      currentNode = first;
      while(currentNode.next != null) {
        if(currentNode.next.value < currentNode.value) {
          swap(currentNode.next, currentNode);
          isSorted = false;
        }
        currentNode = currentNode.next;
      }
    }
  }

  //#region QUICKSORT
  void quickSort() {
    quickSort(first, getLastNode());
  }

  private void quickSort(Node<Integer> start, Node<Integer> end)
  {
    if(start == null || start == end || start == end.next)
      return;
    Node<Integer> pivotPrev = partition(start, end);
    quickSort(start, pivotPrev);
    if(pivotPrev == null)
      return;
    if(pivotPrev == start)
      quickSort(pivotPrev.next, end);
    else if(pivotPrev.next != null)
      quickSort(pivotPrev.next.next, end);
  } 
  
  private Node<Integer> partition(Node<Integer> start, Node<Integer> pivot) { //pivot is end
    if(start == pivot || start == null || pivot == null)
      return start;
    Node<Integer> pivotPrev = start;
    Node<Integer> current = start;
    while(current != pivot) {
      if(current.value < pivot.value) {
        swap(current, start);
        pivotPrev = start;
        start = start.next;
      }
      current = current.next;
    }
    swap(start, pivot);
    return pivotPrev;
  }
  //#endregion
  
  //#region MERGESORT
  void mergeSort() {
    first = mergeSort(first);
  }

  private Node<Integer> mergeSort(Node<Integer> start) {
    if(start == null || start.next == null)
      return start;
    
    Node<Integer> middle = getMiddleNode(start);
    Node<Integer> middleNext = middle.next;

    middle.next = null;

    Node<Integer> left = mergeSort(start);
    Node<Integer> right = mergeSort(middleNext);

    return merge(left, right);
  }

  private Node<Integer> merge(Node<Integer> a, Node<Integer> b) {
    if(a == null)
      return b;
    if(b == null)
      return a;
    
    Node<Integer> result;

    if(a.value <= b.value) {
      result = a;
      result.next = merge(a.next, b);
    }
    else {
      result = b;
      result.next = merge(a, b.next);
    }
    return result;
  }

  private Node<Integer> getMiddleNode(Node<Integer> start) {
    if(start == null)
      return start;
    Node<Integer> slow = start;
    Node<Integer> fast = start;
    while(fast.next != null && fast.next.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }
  //#endregion
  //#endregion

  //#region RANDOMIZE
  void randomize(int size) {
    randomize(size, Integer.MAX_VALUE);
  }

  void randomize(int size, int max) {
    clear();
    Random r = new Random();
    for(int i = 0; i < size; i++) {
      add(r.nextInt(max));
    }
  }

  static IntegerLinkedList randomList(int size) {
    IntegerLinkedList list = new IntegerLinkedList();
    list.randomize(size);
    return list;
  }

  static IntegerLinkedList randomList(int size, int max) {
    IntegerLinkedList list = new IntegerLinkedList();
    list.randomize(size, max);
    return list;
  }
  //#endregion
}

class Node<Type> {

  Type value;
  Node<Type> next;

  Node(Type value) {
    this.value = value;
    this.next = null;
  }

  Node(Type value, Node<Type> node) {
    this.value = value;
    this.next = node;
  }

  public String toString() {
    return value.toString();
  }
}

class Queue<Type> {

  Object[] array;
  int rear, front, size, capacity;

  Queue() {
    capacity = 8;
    array = new Object[capacity];
    front = capacity;
  }

  Queue(int capacity) {
    this.capacity = capacity;
    array = new Object[capacity];
    front = capacity;
  }

  void printAll() {
    System.out.println(toString());
  }

  public String toStringInOrder() {
    if(isEmpty())
      return "";
    String string = "";
    if(rear < front)
      for(int i = rear; i < front; i++)
        string += "[" + array[i] + "]";
    else
      for(int i = rear; i < front + capacity; i++)
        if(i < capacity)
          string += "[" + array[i] + "]";
        else
          string += "[" + array[i - capacity] + "]";
    return string;
  }

  public String toString() {
    String string = "";
    for(Object i : array)
      string += "[" + i + "]";
    return string;
  }

  void enqueue(Type value) {
    if(isFull())
      increaseCapacity();
    size++;
    rear--;
    if(rear < 0)
      rear += capacity;
    array[rear] = value;
  }

  @SuppressWarnings("unchecked")
  Type dequeue() {
    if(isEmpty())
      return null;
    size--;
    front--;
    if(front < 0)
      front += capacity;
    Type value = (Type)array[front];
    array[front] = null;
    return value;
  }

  @SuppressWarnings("unchecked")
  Type front() {
    if(isEmpty())
      return null;
    return (Type)array[front];
  }

  void increaseCapacity() {
    setCapacity(capacity * 2);
  }

  void setCapacity(int newCapacity) {
    if(newCapacity <= capacity)
      return;
    Object[] newArray = new Object[newCapacity];
    if(rear < front)
      for(int i = rear; i < front; i++)
        newArray[i] = array[i];
    else
      for(int i = rear; i < front + capacity; i++)
        if(i < capacity)
          newArray[i] = array[i];
        else
          newArray[i] = array[i - capacity];
      front += capacity;
    array = newArray;
  }

  int size() {
    return size;
  }

  boolean isFull() {
    return size == capacity;
  }

  boolean isEmpty() {
    return size == 0;
  }
}

class Stack<Type> {

  Object[] array;
  int front, capacity;

  Stack() {
    capacity = 8;
    array = new Object[capacity];
  }

  Stack(int capacity) {
    this.capacity = capacity;
    array = new Object[capacity];
  }

  void printAll() {
    System.out.println(toString());
  }

  public String toString() {
    String string = "";
    for(Object i : array)
      string += "[" + i + "]";
    return string;
  }

  void push(Type value) {
    if(isFull())
      increaseCapacity();
    array[front] = value;
    front++;
  }

  @SuppressWarnings("unchecked")
  Type pop() {
    if(isEmpty())
      return null;
    front--;
    Type value = (Type)array[front];
    array[front] = null;
    return value;
  }

  @SuppressWarnings("unchecked")
  Type peek() {
    if(isEmpty())
      return null;
    return (Type)array[front];
  }

  void increaseCapacity() {
    setCapacity(capacity * 2);
  }

  void setCapacity(int newCapacity) {
    if(newCapacity <= capacity)
      return;
    Object[] newArray = new Object[newCapacity];
    for(int i = 0; i <= front - 1; i++)
      newArray[i] = array[i];
    array = newArray;
  }

  boolean isFull() {
    return front == capacity;
  }

  boolean isEmpty() {
    return front == 0;
  }
}