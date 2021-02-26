# Libreria
<img src="https://www.finestwallpaper.com/uploads/5/7/7/9/5779447/2995229_orig.jpg" align="right" Hspace="8" Vspace="0" width="300" height="300"
Border="0">
Libreria virtuale scritta in linguaggio *Java* con l'ausilio della libreria *Swing*. Lo scopo è creare un software in grado di gestire tutte le operazioni che si possono svolgere in una libreria come la gestione delle posizione di un libro, la gestione di ogni scaffale e la restituzione/ prestito di un libro. Il programma crea 3 stanze nominate **A**, **B**, **C**, ognuna delle quali ha un certo numero di scaffali; ogni scaffale a sua volta ha 20 posizioni, inizializzate vuote in cui è possibile aggiungere un libro o cancellarlo. Una volta entrato in una stanza è possibile effettuare operazioni su ogni posizione presente su uno scaffale semplicemente premendo con il tasto destro sulla posizioni e selezionando l'operazione che si desidera effettuare:
* **Cancellazione**: il libro presente in quella posizione sullo scaffale, se presente, viene cancellato.
* **Restituzione**: il libro, se in prestito, cambio lo stato diventando *disponibile*.
* **Prestito**: il libro, se non in prestito, cambio lo stato diventando *non disponibile*.
* **Aggiunta**: se la posizione non è occupata, lo diventa aggiungendo un nuovo libro.
* **Dettagli**: mostra i dettagli del libro in quella posizione, se presente.

## Prerequisiti
Occorre installare i seguenti strumenti al fine di poter scaricare la cartella e provare il software:
* *Java 8 o versioni successive*
* *Libreria Java Swing*
* *Eclipse o IntelliJ*

## Implementazione e Test
Il progetto ha previsto la creazione di 3 pacchetti:
* [*Beans*](https://github.com/Peppen/Library/tree/master/ProgettoBiblioteca/src/beans) che contiene i beans necessari per l'implementazione della logica del software:
  * [*Libro*](https://github.com/Peppen/Library/blob/master/ProgettoBiblioteca/src/beans/Libro.java) contenente i seguenti campi: 
    * *titolo*, in riferimento al titolo del libro
    * *lista di autori*, in riferimento agli autori del libro
    * *casa editrice*, in riferimento alla casa editrice produttrice del libro
    * *stato*, specifica s eun libro è disponibile al prestito o meno
    * *posizione*, in riferimento alla posizione all'interno dello scaffale
  * [*Scaffale*](https://github.com/Peppen/Library/blob/master/ProgettoBiblioteca/src/beans/Scaffale.java)
  * [*Stanza*](https://github.com/Peppen/Library/blob/master/ProgettoBiblioteca/src/beans/Stanza.java)
* [*BusinessLogic*](https://github.com/Peppen/Library/tree/master/ProgettoBiblioteca/src/businesslogic)
* [*Graphics*](https://github.com/Peppen/Library/tree/master/ProgettoBiblioteca/src/graphics)




