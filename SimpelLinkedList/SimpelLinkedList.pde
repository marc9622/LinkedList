//Opg 1.)  Lav en “node” klasse der kan indeholde tal!!
//Opg 2.)  Hardcode en linked list, der kan indeholde tal!
//Opg 3.)  Opbyg en “linked-list” klasse der indeholder et objekt “first” der er den første “node” til en linked-list.
//Opg 4.)  Lav metode i “linked-list klassen” til at indsætte tal i din “linked-list”
//Opg 5.)  Lav metode i “linked-list klassen” der udskriver alle linked-list noder og afprøv dinmetode...
//Opg 6.)  Lav metode i “linked-list klassen” til at indsætte et tal i slutningen
//Opg 7.)  Lav metode i “linked-list klassen” til at indsætte et tal i starten
//Opg 8.)  Lav metode i “linked-list klassen” til at indsætte et tal på plads K i listen
//Opg 9.)  Lav metode i “linked-list klassen” til at finde det største tal i listen
//Opg 10.) Lav metode i “linked-list klassen” til at finde det mindste tal i listen

LinkedList list;

void setup() {
  
  list = new LinkedList();  
  list.first = new Node(10, null);
  list.first.next = new Node(20, new Node(30, null));
  
  list.add(40);
  list.add(50);
  
  list.printAll();
  
}
