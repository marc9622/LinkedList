class LinkedList {
  
  Node first;
  
  void add(int value) {
    getLastNode().next = new Node(value, null);
  }
  
  Node getLastNode() {
    Node current = first;
    while(current.next != null) {
      current = current.next;
    }
    return current;
  }
  
  void printAll() {
    Node current = first;
    print("[" + current.value + "]");
    while(current.next != null) {
      current = current.next;
      print("[" + current.value + "]");
    }
  }
  
}
