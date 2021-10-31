import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

class Main {
  static public void main(String[] args) {

    LinkedList<String> strings = new LinkedList<String>(new String[]{"Hej", "med", "dig!"});

    ArrayStack<String> stack = new ArrayStack<String>(strings.size());
    for(String i : strings)
      stack.add(i);
    stack.printAll();

    IntegerLinkedList integers1 = IntegerLinkedList.randomList(20, 99);
    integers1.printAll();

    integers1.quickSort();
    integers1.printAll();

    integers1.shuffle();
    integers1.mergeSort();
    integers1.printAll();

    LinkedList<Integer> integers2 = new IntegerLinkedList();

    ArrayDeque<Integer> deque = new ArrayDeque<Integer>(integers1.size());
    for(Integer i : integers1)
      deque.add(i);

    ListIterator<Integer> listIterator = deque.listIterator(deque.size());
    System.out.println(Arrays.toString(deque.toDescendingArray()));

    integers1.clear();

    for(int i = 0, size = deque.size(); i < size; i++) {
      integers1.add(deque.remove());
    }

    while(listIterator.hasPrevious())
      integers2.add(listIterator.previous());

    System.out.println(integers1.equals(integers2));
  }
}

class DoubleLinkedList<Type> {
  //TODO
}

class DoubleLinkedListIterator<Type> implements ListIterator<Type> {

  //TODO

  //list variable

  int cursor; //An index between two objects in the list. next() returns the object after and previous() returns object before.

  DoubleLinkedListIterator(LinkedList<Type> list) {

  }

  DoubleLinkedListIterator(LinkedList<Type> list, int index) {
    
  }

  public boolean hasNext() {
    return true;
  }

  public boolean hasPrevious() {
    return true;
  }

  public Type next() {
    return null;
  }

  public Type previous() {
    return null;
  }

  public int nextIndex() {
    return 0;
  }

  public int previousIndex() {
    return 0;
  }

  public void remove() {
    return;
  }

  public void set(Type object) {
    return;
  }

  public void add(Type object) {
    return;
  }
}

class LinkedListIterator<Type> implements Iterator<Type> {

  Node<Type> currentNode;

  LinkedListIterator(LinkedList<Type> list) {
    currentNode = new Node<Type>(null, list.first);
  }

  LinkedListIterator(LinkedList<Type> list, int index) {
    currentNode = new Node<Type>(null, list.first);
    for(int i = 0; i < index; i++)
      next();
  }

  public boolean hasNext() {
    return currentNode.next != null;
  }

  public Type next() {
    currentNode = currentNode.next;
    return currentNode.value;
  }
}

class LinkedList<Type> implements List<Type> {

  Node<Type> first = null;
  int size;

  //#region CONSTRUCTOR
  LinkedList() {

  }

  LinkedList(Type value) {
    add(value);
  }

  LinkedList(Node<Type> node) {
    first = node;
    size = calculateSize();
  }

  LinkedList(Type[] array) {
    addAll(array);
  }

  @SuppressWarnings("unchecked")
  LinkedList(LinkedList<? extends Type> list) {
    addAll((LinkedList<Type>)list);
  }

  LinkedList(Collection<? extends Type> collection) {
    addAll(collection);
  }
  //#endregion

  //#region TO ARRAY
  public Object[] toArray() {
    Object[] array = new Object[size()];
    Node<Type> currentNode = first;
    for(int i = 0; i < array.length; i++) {
      array[i] = currentNode.value;
      currentNode = currentNode.next;
    }
    return array;
  }

  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] array) {
    if(array.length < size()) {
      array = Arrays.copyOf(array, size());
    }
    Node<Type> currentNode = first;
    for(int i = 0; currentNode != null; i++) {
      array[i] = (T)currentNode.value;
      currentNode = currentNode.next;
    }
    if(array.length > size())
      array[size()] = null;
    return array;
  }
  //#endregion

  ArrayList<Type> toArrayList() {
    ArrayList<Type> arrayList = new ArrayList<Type>();
    arrayList.ensureCapacity(size());
    for(Type i : this) {
      arrayList.add(i);
    }
    return arrayList;
  }

  //#region SUB LIST
  public LinkedList<Type> subList(int start) {
    Node<Type> nodeToAdd = new Node<Type>();
    Node<Type> currentNodeToAdd = nodeToAdd;
    Node<Type> currentNode = getNode(start);
    while(currentNode != null) {
      currentNodeToAdd.value = currentNode.value;
      if(currentNode.next != null) {
        currentNodeToAdd.next = new Node<Type>();
        currentNodeToAdd = currentNodeToAdd.next;
      }
      currentNode = currentNode.next;
    }
    return new LinkedList<Type>(nodeToAdd);
  }

  public LinkedList<Type> subList(int start, int end) {
    Node<Type> nodeToAdd = new Node<Type>();
    Node<Type> currentNodeToAdd = nodeToAdd;
    Node<Type> currentNode = getNode(start);
    for(int i = start; i < end; i++) {
        currentNodeToAdd.value = currentNode.value;
        if(i + 1 < end) {
          currentNodeToAdd.next = new Node<Type>();
          currentNodeToAdd = currentNodeToAdd.next;
        }
      currentNode = currentNode.next;
    }
    return new LinkedList<Type>(nodeToAdd);
  }
  //#endregion

  void printAll() {
    System.out.println(toString());
  }

  public String toString() {
    String string = "";
    for(Type i : this)
      string += i == null ? "[null]" : "[" + i.toString() + "]";
    return string;
  }

  public LinkedList<Type> clone() {
    return new LinkedList<Type>(this);
  }

  static <T> LinkedList<T> clone(LinkedList<T> list) {
    return new LinkedList<T>(list);
  }

  //#region EQUALS
  public boolean equals(Object object) {
    if(!super.equals(object) || object == null || !(object instanceof LinkedList<?>))
      return false;
    return equals((LinkedList<?>) object);
  }

  public boolean equals(LinkedList<?> list) {
    int sizeTarget = list.size();
    if(size() != sizeTarget)
      return false;
    Node<Type> currentNodeThis = first;
    Node<?> currentNodeTarget = list.first;
    for(int i = 0; i < size(); i++) {
      if(!currentNodeThis.value.equals(currentNodeTarget.value))
        return false;
      currentNodeThis = currentNodeThis.next;
      currentNodeTarget = currentNodeTarget.next;
    }
    return true;
  }
  //#endregion

  public void shuffle() {
    int size = size();
    Random r = new Random();
    for(int i = 0; i < size; i++) {
      swap(i, r.nextInt(size));
    }
  }

  //#region SWAP
  public void swap(int indexA, int indexB) {
    if(indexA == indexB)
      return;
    Node<Type> nodeA = getNode(indexA);
    Node<Type> nodeB = getNode(indexB);
    Type temp = nodeA.value;
    nodeA.value = nodeB.value;
    nodeB.value = temp;
  }

  public void swap(Node<Type> nodeA, Node<Type> nodeB) {
    swap(indexOf(nodeA), indexOf(nodeB));
  }
  //#endregion

  //#region ADD FIRST
  public void addFirst(Type value) {
    size++;
    first = new Node<Type>(value);
  }

  public void addFirst(Node<Type> node) {
    size++;
    first = node;
  }
  //#endregion
  
  //#region ADD
  public boolean add(Type value) {
    if(first == null) {
      addFirst(value);
      return true;
    }
    size++;
    getLastNode().next = new Node<Type>(value);
    return true;
  }

  public void add(int index, Type value) {
    if(index == 0) {
      addFirst(value);
      return;
    }
    size++;
    Node<Type> previousNode = getNode(index - 1);
    if(previousNode.next == null) {
      previousNode.next = new Node<Type>(value);
      return;
    }
    Node<Type> oldNode = previousNode.next;
    previousNode.next = new Node<Type>(value, oldNode);
  }
  //#endregion
  
  //#region ADD NODE
  public void addNode(Node<Type> node) {
    if(first == null) {
      addFirst(node);
      return;
    }
    size++;
    getLastNode().next = node;
  }

  public void addNode(int index, Node<Type> node) {
    if(index == 0) {
      addFirst(node);
      return;
    }
    size++;
    Node<Type> previousNode = getNode(index - 1);
    if(previousNode.next == null) {
      previousNode.next = node;
      return;
    }
    Node<Type> oldNode = previousNode.next;
    previousNode.next = node;
    node.next = oldNode;
  }
  //#endregion

  //#region ADD ALL
  public void addAll(Type[] array) {
    for(int i = 0; i < array.length; i++) {
      add(array[i]);
    }
  }

  public void addAll(int index, Type[] array) {
    for(int i = 0; i < array.length; i++) {
      add(index + i, array[i]);
    }
  }

  @SuppressWarnings("unchecked")
  public boolean addAll(LinkedList<? extends Type> list) {
    Node<Type> currentNodeToAdd = (Node<Type>)list.first;
    Node<Type> currentNode = getLastNode();
    if(currentNode == null) {
      size++;
      currentNode = new Node<Type>(currentNodeToAdd.value);
      currentNodeToAdd = currentNodeToAdd.next;
    }
    while(currentNodeToAdd != null) {
      size++;
      currentNode.next = new Node<Type>(currentNodeToAdd.value);
      currentNodeToAdd = currentNodeToAdd.next;
      currentNode = currentNode.next;
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  public boolean addAll(int index, LinkedList<? extends Type> list) {
    Node<Type> currentNodeToAdd = (Node<Type>)list.first;
    Node<Type> currentNode = getNode(index);
    Node<Type> oldNode;
    if(currentNode == null) {
      oldNode = null;
      size++;
      currentNode = new Node<Type>(currentNodeToAdd.value);
      currentNodeToAdd = currentNodeToAdd.next;
    }
    else
      oldNode = currentNode.next;
    while(currentNodeToAdd.next != null) {
      size++;
      currentNode.next = new Node<Type>(currentNodeToAdd.value);
      currentNodeToAdd = currentNodeToAdd.next;
      currentNode = currentNode.next;
    }
    size++;
    currentNode.next = new Node<Type>(currentNodeToAdd.value, oldNode);
    return true;
  }

  public boolean addAll(Collection<? extends Type> collection) {
    Node<Type> currentNode = getLastNode();
    for(Type i : collection) {
      if(first == null) {
        addFirst(i);
        currentNode = first;
        continue;
      }
      size++;
      currentNode.next = new Node<Type>(i);
      currentNode = currentNode.next;
    }
    return true;
  }

  public boolean addAll(int index, Collection<? extends Type> collection) {
    Node<Type> currentNode = getNode(index);
    for(Type i : collection) {
      size++;
      currentNode.next = new Node<Type>(i);
      currentNode = currentNode.next;
    }
    return true;
  }
  //#endregion

  //#region REMOVE
  public Type remove(int index) {
    size--;
    if(index == 0) {
      Node<Type> removedNode = first;
      first = first.next;
      return removedNode.value;
    }
    Node<Type> previousNode = getNode(index - 1);
    Node<Type> removedNode = previousNode.next;
    Node<Type> nextNode = removedNode.next;
    previousNode.next = nextNode;
    return removedNode.value;
  }

  public boolean remove(Object target) {
    size--;
    Node<Type> previousNode = getNode(indexOf(target) - 1);
    Node<Type> nextNode = previousNode.next.next;
    previousNode.next = nextNode;
    return true;
  }

  public Node<Type> remove(Node<Type> target) {
    size--;
    Node<Type> previousNode = getPreviousNode(target);
    Node<Type> removedNode = previousNode.next;
    Node<Type> nextNode = previousNode.next.next;
    previousNode.next = nextNode;
    return removedNode;
  }

  public boolean removeAll(Collection<?> collection) {
    for(Object i : collection)
      remove(i);
    return true;
  }
  //#endregion

  public boolean retainAll(Collection<?> collection) {
    if(isEmpty())
      return false;
    Node<Type> currentNode = first;
    while(currentNode.next != null) {
      if(!isNodeContainedIn(currentNode.next, collection))
        remove(currentNode.next);
    }
    if(!isNodeContainedIn(first, collection)) {
      first = first.next;
    }
    return true;
  }

  //#region SET
  public Type set(int index, Type value) {
    getNode(index).value = value;
    return value;
  }

  public Node<Type> set(int index, Node<Type> node) {
    Node<Type> previousNode = getNode(index - 1);
    Node<Type> nextNode = previousNode.next.next;
    previousNode.next = node;
    node.next = nextNode;
    return node;
  }
  //#endregion

  public Type get(int index) {
    return getNode(index).value;
  }

  //#region INDEX OF
  public int indexOf(Object target) {
    if(target.getClass() != first.value.getClass())
      return -1;
    int index = 0;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.value.equals(target))
        return index;
      currentNode = currentNode.next;
      index++;
    }
    return -1;
  }

  public int indexOf(Node<Type> target) {
    int index = 0;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.equals(target))
        return index;
      currentNode = currentNode.next;
      index++;
    }
    return -1;
  }

  public int lastIndexOf(Object target) {
    if(target.getClass() != first.value.getClass())
      return -1;
    int index = 0;
    int lastIndex = -1;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.equals(target))
        lastIndex = index;
      currentNode = currentNode.next;
      index++;
    }
    return lastIndex;
  }

  public int lastIndexOf(Node<Type> target) {
    int index = 0;
    int lastIndex = -1;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.equals(target))
        lastIndex = index;
      currentNode = currentNode.next;
      index++;
    }
    return lastIndex;
  }
  //#endregion

  //#region CONTAINS
  public boolean contains(Object target) {
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.value.equals(target))
        return true;
      currentNode = currentNode.next;
    }
    return false;
  }

  public boolean contains(Node<Type> target) {
    Node<Type> currentNode = first;
    while(currentNode != null) {
      if(currentNode.equals(target.value))
        return true;
      currentNode = currentNode.next;
    }
    return false;
  }

  public boolean containsAll(Collection<?> collection) {
    for(Object i : collection)
      if(!contains(i))
        return false;
    return true;
  }

  @SuppressWarnings("unchecked")
  public boolean isNodeContainedIn(Node<Type> node, Collection<?> collection) {
    for(Type i : (Collection<Type>)collection) {
      if(node == i)
        return true;
    }
    return false;
  }

  public boolean isNodeContainedIn(int index, Collection<?> collection) {
    return isNodeContainedIn(getNode(index), collection);
  }
  //#endregion

  void setSize(int size) {
    this.size = size;
    if(size == 0)
      clear();
    if(first == null)
      first = new Node<Type>();
    size--;
    Node<Type> currentNode = first;
    for(int i = 0; i < size; i++) {
      if(currentNode.next == null) {
        int nodesToAdd = size - i - 1;
        Node<Type> currentToAdd = new Node<Type>();
        for(int j = 0; j < nodesToAdd; j++) {
          currentToAdd = new Node<Type>(null, currentToAdd);
        }
        currentNode.next = currentToAdd;
        return;
      }
      currentNode = currentNode.next;
    }
    currentNode.next = null;
  }

  int calculateSize() {
    int size = 0;
    Node<Type> currentNode = first;
    while(currentNode != null) {
      size++;
      currentNode = currentNode.next;
    }
    return size;
  }

  public int size() {
    return size;
  }

  public void clear() {
    first = null;
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
    //return first == null;
  }

  //#region HAS NEXT
  public boolean hasNext(int index) {
    return index + 1 < size;
    //return getNode(index).next != null;
  }

  public static boolean hasNext(Node<?> node) {
    return node.next != null;
    //return getNode(index).next != null;
  }
  //#endregion

  Node<Type> getLastNode() {
    if(first == null)
      return null;
    Node<Type> currentNode = first;
    while(currentNode.next != null)
      currentNode = currentNode.next;
    return currentNode;
  }

  Node<Type> getPreviousNode(Node<Type> target) {
    if(target == first)
      return null;
    Node<Type> currentNode = first;
    while(currentNode.next != target)
      currentNode = currentNode.next;
    return currentNode;
  }

  Node<Type> getNode(int index) {
    Node<Type> currentNode = first;
    for(int i = 0; i < index; i++)
      currentNode = currentNode.next;
    return currentNode;
  }

  //#region ITERATOR
  public Iterator<Type> iterator() {
    return new LinkedListIterator<Type>(this);
  }

  public Iterator<Type> iterator(int index) {
    return new LinkedListIterator<Type>(this, index);
  }

  public ListIterator<Type> listIterator() {
    return new DoubleLinkedListIterator<Type>(this);
  }

  public ListIterator<Type> listIterator(int index) {
    return new DoubleLinkedListIterator<Type>(this, index);
  }
  //#endregion
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

  IntegerLinkedList(LinkedList<? extends Integer> list) {
    super(list);
  }

  IntegerLinkedList(Collection<? extends Integer> collection) {
    super(collection);
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

  void selectionSort() {
    int size = size();
    for(int i = 0; i < size; i++) {
      swap(i, indexOfMin(i, size));
    }
  }

  void insertionSort() {
    Node<Integer> sorted = new Node<Integer>(remove(0));
    while(first != null) {
      int newValue = remove(0);
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

  Node() {
    this.value = null;
    this.next = null;
  }

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

  public boolean equals(Object target) {
    return value.equals(target);
  }
}

class ArrayStack<Type> implements Collection<Type> {

  Type[] array;
  int front = -1;

  //#region CONSTRUCTORS
  ArrayStack() {
    this(1);
  }

  @SuppressWarnings("unchecked")
  ArrayStack(int capacity) {
    array = (Type[])new Object[capacity];
  }
  //#endregion

  public void printAll() {
    System.out.println(toString());
  }

  public String toString() {
    String string = "";
    for(Object i : array)
      string += i == null ? "[null]" : "[" + i + "]";
    return string;
  }

  //#region TO ARRAY
  public Object[] toArray() {
    if(isEmpty())
      return null;
    Object[] newArray = new Object[size()];
    for(int i = 0; i <= front; i++)
      newArray[i] = array[i];
    return newArray;
  }

  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] newArray) {
    if(isEmpty())
      return null;
    if(newArray.length < size())
      newArray = Arrays.copyOf(newArray, size());
    for(int i = 0; i <= front; i++)
      newArray[i] = (T)array[i];
    if(newArray.length > front)
      array[front + 1] = null;
    return newArray;
  }
  //#endregion

  //#region ADD
  public boolean add(Type value) {
    if(isFull())
      increaseCapacity();
    push(value);
    return true;
  }

  public boolean addAll(Type[] array) {
    for(Type i : array)
      add(i);
    return true;
  }

  public boolean addAll(Collection<? extends Type> collection) {
    for(Type i : collection)
      add(i);
    return true;
  }

  public boolean offer(Type value) {
    if(isFull())
      return false;
    push(value);
    return true;
  }

  public void push(Type value) {
    front++;
    array[front] = value;
  }
  //#endregion

  //#region REMOVE
  public Type remove() {
    return pop();
  }

  @SuppressWarnings("unchecked")
  public boolean remove(Object target) {
    Type t = (Type)target;
    if(isEmpty())
      return false;
    for(int i = 0; i <= front; i++) {
      if(array[i].equals(t)) {
        for(; i < front; i++) {
          array[i] = array[i + 1];
        }
        array[front] = null;
        front--;
        return true;
      }
    }
    return false;
  }

  public boolean removeAll(Type[] array) {
    for(Object i : array)
      remove(i);
    return false;
  }

  public boolean removeAll(Collection<?> collection) {
    for(Object i : collection)
      remove(i);
    return false;
  }

  public Type pop() {
    if(isEmpty())
      return null;
    Type value = (Type)array[front];
    array[front] = null;
    front--;
    return value;
  }
  //#endregion

  public boolean retainAll(Collection<?> collection) {
    Type[] newArray = toArray(array);
    for(int i = 0; i < newArray.length; i++)
      if(!collection.contains(newArray[i]))
        newArray[i] = null;
    for(Type i : (Type[])newArray)
      if(i != null)  
        add(i);
    array = newArray;
    return true;
  }

  public Type element() {
    if(isEmpty())
      throw new NoSuchElementException();
    return array[front];
  }

  public Type peek() {
    if(isEmpty())
      return null;
    return array[front];
  }
  
  //#region CONTAINS
  public boolean contains(Object target) {
    for(Type i : array) {
      if(i.equals(target))
      return true;
    }
    return false;
  }

  public boolean containsAll(Collection<?> collection) {
    for(Object i : collection) {
      if(!contains(i));
        return false;
    }
    return true;
  }
  //#endregion

  @SuppressWarnings("unchecked")
  public void clear() {
  	array = (Type[])new Object[array.length];
    front = -1;
  }

  boolean isFull() {
    return front + 1 == array.length;
  }

  public boolean isEmpty() {
    return front == -1;
  }

  //#region CAPACITY
  void increaseCapacity() {
    ensureCapacity(array.length * 2);
  }
  
  void increaseCapacity(int capacity) {
    ensureCapacity(array.length + capacity);
  }

  @SuppressWarnings("unchecked")
  void ensureCapacity(int newCapacity) {
    if(newCapacity <= array.length)
      return;
    Type[] newArray = (Type[])new Object[newCapacity];
    for(int i = 0; i <= front - 1; i++)
      newArray[i] = array[i];
    array = newArray;
  }
  //#endregion CAPACITY

  public int size() {
    return front + 1;
  }

  //#region ITERATOR
  @SuppressWarnings("unchecked")
  public Iterator<Type> iterator() {
    return new ArrayIterator<Type>((Type[])toArray());
  }

  @SuppressWarnings("unchecked")
  public ListIterator<Type> listIterator() {
    return new ArrayListIterator<Type>((Type[])toArray());
  }

  @SuppressWarnings("unchecked")
  public ListIterator<Type> listIterator(int index) {
    return new ArrayListIterator<Type>((Type[])toArray(), index);
  }
  //#endregion
}

class ArrayQueue<Type> implements java.util.Queue<Type> {

  Type[] array;
  int tail, head, size;

  //#region CONSTRUCTORS
  ArrayQueue() {
    this(1);
  }

  @SuppressWarnings("unchecked")
  ArrayQueue(int capacity) {
    array = (Type[])new Object[capacity];
    head = capacity - 1;
  }
  //#endregion

  void printAll() {
    System.out.println(toString());
  }

  //#region TO STRING
  public String toStringInOrder() {
    if(isEmpty())
      return "";
    String string = "";
    for(int i = 0; i != size; i++)
      string += "[" + array[wrapIndex(i + tail)] + "]";
    return string;
  }

  public String toString() {
    String string = "";
    for(Object i : array)
      string += i == null ? "[null]" : "[" + i + "]";
    return string;
  }
  //#endregion

  //#region TO ARRAY
  public Object[] toArray() {
    if(isEmpty())
      return null;
    Object[] newArray = new Object[size];
    for(int i = 0; i != size; i++)
      newArray[i] = array[wrapIndex(i + tail)];
    return newArray;
  }

  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] newArray) {
    if(isEmpty())
      return null;
    if(newArray.length < size)
      newArray = Arrays.copyOf(newArray, size());
    for(int i = 0; i != size; i++)
      newArray[i] = (T)array[wrapIndex(i + tail)];
    if(newArray.length > size)
      array[size] = null;
    return newArray;
  }
  //#endregion

  //#region ADD
  public boolean add(Type value) {
    if(isFull())
      increaseCapacity();
    enqueue(value);
    return true;
  }

  public boolean addAll(Type[] array) {
    for(Type i : array)
      add(i);
    return true;
  }

  public boolean addAll(Collection<? extends Type> collection) {
    for(Type i : collection)
      add(i);
    return true;
  }

  public boolean offer(Type value) {
    if(isFull())
      return false;
    enqueue(value);
    return true;
  }

  public void enqueue(Type value) {
    size++;
    tail = wrapIndex(tail - 1);
    array[tail] = value;
  }
  //#endregion

  //#region REMOVE
  public Type remove() {
    if(isEmpty())
      throw new NoSuchElementException();
    return dequeue();
  }

  public boolean remove(int index) {
    size--;
    for(int i = index; i != size; i++) {
      array[wrapIndex(i + tail)] = array[wrapIndex(i + tail + 1)];
    }
    array[head] = null;
    head = wrapIndex(head - 1);
    return true;
  }

  public boolean remove(Object target) {
    return remove(indexOf(target));
  }

  @SuppressWarnings("unchecked")
  public boolean removeAll(Collection<?> collection) {
    for(Type i : (Collection<Type>)collection)
      remove(i);
    return true;
  }

  public Type poll() {
    if(isEmpty())
      return null;
    return dequeue();
  }

  private Type dequeue() {
    size--;
    Type value = array[head];
    array[head] = null;
    head = wrapIndex(head - 1);
    return value;
  }
  //#endregion

  public boolean retainAll(Collection<?> collection) {
    Type[] newArray = toArray(array);
    for(int i = 0; i < newArray.length; i++)
      if(!collection.contains(newArray[i]))
        newArray[i] = null;
    for(Type i : (Type[])newArray)
      if(i != null)  
        add(i);
    array = newArray;
    return true;
  }

  //#region INDEX OF
  @SuppressWarnings("unchecked")
  protected int indexOf(Object target) {
    Type t = (Type)target;
    for(int i = 0; i != size; i++)
      if(array[wrapIndex(i + tail)].equals(t))
        return i;
    return -1;
  }

  @SuppressWarnings("unchecked")
  protected int lastIndexOf(Object target) {
    Type t = (Type)target;
    for(int i = size; i != -1; i--)
      if(array[wrapIndex(i + tail)].equals(t))
        return i;
    return -1;
  }
  //#endregion

  public Type element() {
    if(isEmpty())
      throw new NoSuchElementException();
    return array[head];
  }

  public Type peek() {
    if(isEmpty())
      return null;
    return array[head];
  }

  //#region CONTAINS
  public boolean contains(Object target) {
    for(Type i : array) {
      if(i.equals(target))
      return true;
    }
    return false;
  }

  public boolean containsAll(Collection<?> collection) {
    for(Object i : collection) {
      if(!contains(i));
        return false;
    }
    return true;
  }
  //#endregion

  @SuppressWarnings("unchecked")
  public void clear() {
  	array = (Type[])new Object[array.length];    
  }

  public boolean isFull() {
    return size == array.length;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  //#region CAPACITY
  public void increaseCapacity() {
    ensureCapacity(array.length * 2);
  }

  public void increaseCapacity(int capacity) {
    ensureCapacity(array.length + capacity);
  }
  
  @SuppressWarnings("unchecked")
  public void ensureCapacity(int newCapacity) {
    if(newCapacity <= array.length)
      return;
    Object[] newArray = new Object[newCapacity];
    for(int i = 0; i < size; i++) {
      newArray[i + newCapacity - array.length] = array[wrapIndex(i + tail)];
    }
    head = newArray.length - 1;
    tail = newArray.length - size;
    array = (Type[])newArray;
  }
  //#endregion

  public int size() {
    return size;
  }

  protected int wrapIndex(int index) {
    if(index < 0)
      return index + array.length;
    if(index >= array.length)
      return index - array.length;
    return index;
  }

  //#region ITERATOR
  @SuppressWarnings("unchecked")
  public Iterator<Type> iterator() {
    return new ArrayIterator<Type>((Type[])toArray());
  }

  @SuppressWarnings("unchecked")
  public ListIterator<Type> listIterator() {
    return new ArrayListIterator<Type>((Type[])toArray());
  }

  @SuppressWarnings("unchecked")
  public ListIterator<Type> listIterator(int index) {
    return new ArrayListIterator<Type>((Type[])toArray(), index);
  }
  //#endregion
}

class ArrayDeque<Type> extends ArrayQueue<Type> implements java.util.Deque<Type> {

  //#region CONSTRUCTORS
  ArrayDeque() {
    this(1);
  }

  ArrayDeque(int capacity) {
    super(capacity);
  }
  //#endregion

  //#region TO ARRAY
  public Object[] toDescendingArray() {
    if(isEmpty())
      return null;
    Object[] newArray = new Object[size];
    for(int i = 0; i != size; i++)
      newArray[(size - 1) - i] = array[wrapIndex(i + tail)];
    return newArray;
  }

  @SuppressWarnings("unchecked")
  public <T> T[] toDescendingArray(T[] newArray) {
    if(isEmpty())
      return null;
    if(newArray.length < size)
      newArray = Arrays.copyOf(newArray, size());
    for(int i = 0; i != size; i++)
      newArray[(size - 1) - i] = (T)array[wrapIndex(i + tail)];
    if(newArray.length > size)
      array[size] = null;
    return newArray;
  }
  //#endregion

  public void addFirst(Type value) {
    if(isFull())
      increaseCapacity();
    push(value);
  }

  public void addLast(Type value) {
    add(value);
  }

  public boolean offerFirst(Type value) {
    if(isFull())
      return false;
    push(value);
    return true;
  }

  public boolean offerLast(Type value) {
    return offer(value);
  }

  public Type removeFirst() {
    return remove();
  }

  public Type removeLast() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean removeFirstOccurrence(Object target) {
    return remove(target);
  }

  public boolean removeLastOccurrence(Object target) {
    return remove(lastIndexOf(target));
  }

  public Type pollFirst() {
    return poll();
  }

  public Type pollLast() {
    // TODO Auto-generated method stub
    return null;
  }

  public Type getFirst() {
    return element();
  }

  public Type getLast() {
    // TODO Auto-generated method stub
    return null;
  }

  public Type peekFirst() {
    return peek();
  }

  public Type peekLast() {
    // TODO Auto-generated method stub
    return null;
  }

  public void push(Type value) {
    size++;
    head = wrapIndex(head + 1);
    array[head] = value;
  }

  public Type pop() {
    return remove();
  }

  //#region DESCENDING ITERATOR
  @SuppressWarnings("unchecked")
  public Iterator<Type> descendingIterator() {
    return new ArrayIterator<Type>((Type[])toDescendingArray());
  }

  @SuppressWarnings("unchecked")
  public ListIterator<Type> descendingListIterator() {
    return new ArrayListIterator<Type>((Type[])toDescendingArray());
  }

  @SuppressWarnings("unchecked")
  public ListIterator<Type> descendingListIterator(int index) {
    return new ArrayListIterator<Type>((Type[])toDescendingArray(), index);
  }
  //#endregion
}

class ArrayIterator<Type> implements Iterator<Type> {

  Type[] array;
  int cursor;

  ArrayIterator(Type[] array) {
    this.array = array;
  }

  ArrayIterator(Type[] array, int index) {
    this(array);
    //cursor = index > array.length ? array.length - 1 : index;
    if(index > array.length - 1)
      throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + (array.length - 1));
    cursor = index;
  }

  public boolean hasNext() {
    return cursor != array.length;
  }

  public Type next() {
    Type next = array[cursor];
    cursor++;
    return next;
  }
}

class ArrayListIterator<Type> extends ArrayIterator<Type> implements ListIterator<Type> {

  int lastReturned = -1, size;

  //#region CONSTRUCTORS
  ArrayListIterator(Type[] array) {
    super(array);
    size = calculateSize();
  }

  ArrayListIterator(Type[] array, int index) {
    this(array);
    //cursor = index > array.length ? array.length - 1 : index;
    if(index > array.length)
      throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + array.length);
    cursor = index;
  }
  //#endregion

  //#region HAS NEXT/PREVIOUS
  public boolean hasNext() {
    return cursor < array.length;
  }

  public boolean hasPrevious() {
    return cursor > 0;
  }
  //#endregion

  //#region NEXT/PREVIOUS INDEX
  public int nextIndex() {
    return cursor;
  }

  public int previousIndex() {
    return cursor - 1;
  }
  //#endregion

  //#region GET NEXT/PREVIOUS
  public Type next() {
    lastReturned = cursor;
    Type next = array[cursor];
    cursor++;
    return next;
  }

  public Type previous() {
    cursor--;
    lastReturned = cursor;
    Type previous = array[cursor];
    return previous;
  }
  //#endregion

  public void add(Type value) {
    if(isFull())
      increaseCapacity();
    size++;
    for(int i = size - 1; i > cursor; i--)
      array[i] = array[i - 1];
    array[cursor] = value;
  }

  public void remove() {
    if(lastReturned == -1)
      return;
    if(isEmpty())
      increaseCapacity();
    size--;
    for(int i = lastReturned; i + 1 <= size; i++)
      array[i] = array[i + 1];
    array[size] = null;
    lastReturned = -1;
  }

  public void set(Type value) {
    if(lastReturned == -1)
      return;
    array[lastReturned] = value;
  }

  @SuppressWarnings("unchecked")
  public void clear() {
    array = (Type[])new Object[array.length];
  }

  public boolean isFull() {
    return size == array.length;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  //#region CAPACITY
  void increaseCapacity() {
    ensureCapacity(array.length * 2);
  }

  void increaseCapacity(int capacity) {
    ensureCapacity(array.length + capacity);
  }
  
  @SuppressWarnings("unchecked")
  public void ensureCapacity(int newCapacity) {
    if(newCapacity <= array.length)
      return;
    Object[] newArray = new Object[newCapacity];
    for(int i = 0; i < size; i++) {
      newArray[i] = array[i];
    }
    array = (Type[])newArray;
  }
  //#endregion

  public int size() {
    return size;
  }

  public int calculateSize() {
    for(int i = array.length - 1; i >= 0; i--)
      if(array[i] != null)
        return i + 1;
    return 0;
  }
}