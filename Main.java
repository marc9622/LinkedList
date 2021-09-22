import java.util.Iterator;

class Main {

  static public void main(String[] args) {

    LinkedList<Integer> list = new LinkedList<Integer>(1);

    System.out.println(list.toString());

    list.add(10);
    list.add(100);

    for(int i : list) {
      System.out.println(i);
    }

    LinkedList<String> list1 = new LinkedList<String>("Hej");

    System.out.println(list1.toString());
  }
}

class LinkedListIterator<Type> implements Iterator<Type> {

  public Node<Type> currentNode;

  public LinkedListIterator(LinkedList<Type> list) {
    this.currentNode = list.first;
  }

  @Override
  public boolean hasNext() {
    return currentNode.next != null;
  }

  @Override
  public Type next() {
    currentNode = currentNode.next;
    return currentNode.value;
  }
}

class LinkedList<Type> implements Iterable<Type>{

  Node<Type> first = null;

  //#region CONSTRUCTORS
  LinkedList() {

  }

  LinkedList(Type value) {
    add(value);
  }

  LinkedList(Type[] array) {
    add(array);
  }

  LinkedList(LinkedList<Type> list) {
    add(list);
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

  public String toString() {
    String string = "";
    Node<Type> currentNode = first;
    while(currentNode != null) {
      string += "[" + currentNode.value.toString() + "] ";
      currentNode = currentNode.next;
    }
    return string;
  }

  public LinkedList<Type> clone() {
    return new LinkedList<Type>(this);
  }

  //#region ADD
  void add(Type value) {
    if(first == null)
      first = new Node<Type>(value);
    else
      getLastNode().next = new Node<Type>(value);
  }

  void add(int index, Type value) {
    if(first == null) {
      first = new Node<Type>(value);
      return;
    }
    Node<Type> previousNode = getNode(index);
    if(previousNode == null) {
      previousNode = new Node<Type>(value);
      return;
    }
    Node<Type> nextNode = previousNode.next;
    previousNode.next = new Node<Type>(value, nextNode);
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
  void remove(int index) {
    Node<Type> previousNode = getNode(index - 1);
    Node<Type> nextNode = previousNode.next.next;
    if(nextNode == null)
      previousNode.next = null;
    else
      previousNode.next = nextNode;
  }

  void remove(Node<Type> target) {
    Node<Type> previousNode = getNode(indexOf(target) - 1);
    Node<Type> nextNode = previousNode.next.next;
    if(nextNode == null)
      previousNode.next = null;
    else
      previousNode.next = nextNode;
  }
  //#endregion

  void set(int index, Type value) {
    getNode(index).value = value;
  }

  Type get(int index) {
    return getNode(index).value;
  }

  /*//#region GET MAX/MIN
  int getMax() {
    int maxValue = first.value;
    Node currentNode = first;
    while(currentNode != null) {
      if(currentNode.value > maxValue) {
        maxValue = currentNode.value;
      }
      currentNode = currentNode.next;
    }
    return maxValue;
  }


  int getMin() {
    int minValue = first.value;
    Node currentNode = first;
    while(currentNode != null) {
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
    int maxValue = first.value;
    int maxIndex = 0;
    Node currentNode = first;
    int currentIndex = 0;
    while(currentNode != null) {
      if(currentNode.value > maxValue) {
        maxValue = currentNode.value;
        maxIndex = currentIndex;
      }
      currentNode = currentNode.next;
      currentIndex++;
    }
    return maxIndex;
  }

  int indexOfMin() {
    int minValue = first.value;
    int minIndex = 0;
    Node currentNode = first;
    int currentIndex = 0;
    while(currentNode != null) {
      if(currentNode.value < minValue) {
        minValue = currentNode.value;
        minIndex = currentIndex;
      }
      currentNode = currentNode.next;
      currentIndex++;
    }
    return minIndex;
  }
  *///#endregion

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

  void clear() {
    first = null;
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

  boolean isEmpty() {
    return first == null;
  }

  boolean hasNext(int index) {
    return getNode(index).next != null;
  }

  Node<Type> getLastNode() {
    Node<Type> currentNode = first;
    if(currentNode == null)
      return null;
    while(currentNode.next != null)
      currentNode = currentNode.next;
    return currentNode;
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
}
