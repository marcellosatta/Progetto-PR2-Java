

import java.util.Iterator;
import java.util.List;

public interface DataBoard<E extends MyData> {
    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare una bacheca di dati generici raggruppati
    // per categoria. Non ci possono essere due categorie con lo stesso nome.
    // Per ogni dato, esso è associato ad una ed una sola categoria.
    // La bacheca garantisce la privacy dei	dati fornendo un proprio meccanismo	di gestione	della
    // condivisione dei dati. Il proprietario della	bacheca può	definire le	proprie	categorie e	stilare una lista di
    // contatti	(amici)	a cui saranno visibili i dati per ogni tipologia di	categoria. I dati condivisi	sono visibili
    // solamente in	lettura: in particolare	i dati possono essere visualizzati dagli amici ma modificati solamente
    // dal proprietario	della bacheca. Gli amici possono associare un “like” al	contenuto condiviso.

    // TYPICAL ELEMENT: (admin, password, <category_0: <name_0, <data_0, ..., data_k>, <likes_0, ..., likes_k>,
    // <friendAllowed_0, ..., friendAllowed_j>> ..., category_n: <name_n, <data_0, ..., data_k>, <likes_0, ...,
    // likes_k>, <friendAllowed_0, ..., friendAllowed_j>>>)

    // REQUIRES: category != null && passw != null, passw corrisponde alla password del proprietario della bacheca,
    // category non identifica una categoria già presente nella bacheca;
    // THROWS: NullPointerException se category == null || passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // DuplicateException se category identifica una categoria già presente nella bacheca;
    // MODIFIES: this;
    // EFFECTS: Crea una categoria di dati.
    void createCategory(String category, String passw) throws NullPointerException, WrongPasswordException,
            DuplicateException;

    // REQUIRES: category != null && passw != null, passw corrisponde alla password del proprietario della bacheca,
    // category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se category == null || passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca;
    // MODIFIES: this;
    // EFFECTS: Rimuove una categoria di dati.
    void removeCategory(String category, String passw) throws NullPointerException, WrongPasswordException,
            InexistentElementException;

    // REQUIRES: category != null && passw != null, friend != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca, friend non identifica un amico già
    // abilitato alla lettura dei dati nella categoria identificata da category;
    // THROWS: NullPointerException se category == null || passw == null || friend == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // DuplicateException se friend identifica un amico già abilitato alla lettura dei dati nella categoria identificata
    // da category;
    // MODIFIES: this;
    // EFFECTS: Aggiunge un amico ad una categoria di dati.
    void addFriend(String category, String passw, String friend) throws NullPointerException,
            WrongPasswordException, InexistentElementException, DuplicateException;

    // REQUIRES: category != null && passw != null, friend != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca, friend identifica un amico già abilitato
    // alla lettura dei dati nella categoria identificata da category;
    // THROWS: NullPointerException se category == null || passw == null || friend == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca || friend
    // non identifica un amico già abilitato alla lettura dei dati presenti nella categoria identificata da category;
    // MODIFIES: this;
    // EFFECTS: Rimuove un amico da una categoria di dati.
    void removeFriend(String category, String passw, String friend) throws NullPointerException,
            WrongPasswordException, InexistentElementException;

    // REQUIRES: category != null && passw != null, dato != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca, dato non corrisponde a nessun dato
    // presente in bacheca;
    // THROWS: NullPointerException se category == null || passw == null || dato == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca;
    // DuplicateException se il dato è già presente nella bacheca.
    // MODIFIES: this;
    // EFFECTS: Inserisce un dato in bacheca.
    boolean put(String passw, E dato, String category) throws NullPointerException, WrongPasswordException,
            InexistentElementException, DuplicateException;

    // REQUIRES: passw != null, dato != null, passw corrisponde alla password del proprietario
    // della bacheca, dato corrisponde ad un dato presente in bacheca;
    // THROWS: NullPointerException se passw == null || dato == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se il dato corrispondente al parametro dato non è presente in bacheca;
    // EFFECTS: Restituisce una copia del dato in bacheca se vengono rispettati i controlli di identità.
    E get(String passw, E dato) throws NullPointerException, WrongPasswordException, InexistentElementException;

    // REQUIRES: passw != null, dato != null, passw corrisponde alla password del proprietario
    // della bacheca, dato corrisponde ad un dato presente in bacheca;
    // THROWS: NullPointerException se passw == null || dato == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se il dato corrispondente al parametro dato non è presente in bacheca;
    // MODIFIES: this;
    // EFFECTS: Rimuove il dato dalla bacheca e lo restituisce.
    E remove(String passw, E dato) throws NullPointerException, WrongPasswordException,
            InexistentElementException;

    // REQUIRES: friend != null, dato != null, dato corrisponde ad un dato presente in bacheca, friend identifica un
    // amico abilitato alla lettura dei dati nella categoria in cui è presente il dato identificato dall'omonimo
    // parametro;
    // THROWS: NullPointerException se friend == null || dato == null,
    // InexistentElementException se il dato corrispondente al parametro dato non è presente in bacheca,
    // PermissionDeniedException se l'amico non è abilitato alla lettura dei dati presenti nella categoria in cui il
    // parametro dato è presente,
    // DuplicateException se friend ha gia messo like al dato;
    // MODIFIES: this;
    // EFFECTS: Aggiunge un like a un dato, da parte di friend.
    void insertLike(String friend, E dato) throws NullPointerException, PermissionDeniedException,
            InexistentElementException, DuplicateException;

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista dei dati in bacheca di una determinata categoria.
    List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException,
            InexistentElementException;

    // REQUIRES: passw != null, passwc orrisponde alla password del proprietario della bacheca;
    // THROWS: NullPointerException se passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca;
    // EFFECTS: Restituisce un iteratore (senza remove) che genera tutti i dati in bacheca ordinati rispetto al numero
    // di like.
    Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException;

    // REQUIRES: passw != null, passw corrisponde alla password del proprietario della bacheca;
    // THROWS: NullPointerException se passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca;
    // EFFECTS: restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi.
    Iterator<E> getFriendIterator(String friend) throws NullPointerException;

    // METODI DI SUPPORTO:

    // REQUIRES: category != null && passw != null, passw corrisponde alla password del proprietario della bacheca,
    // category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se category == null || passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca;
    // EFFECTS: Restituisce il nome della categoria category.
    public String getCategoryName(String categor, String passw) throws NullPointerException, WrongPasswordException,
            InexistentElementException;

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista degli amici di una determinata categoria.
    public List<String> getFriendsCategory(String category, String passw) throws NullPointerException,
            WrongPasswordException, InexistentElementException;

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista dei likes associati ai dati di una determinata categoria.
    public List<Integer> getLikesCategory(String category, String passw) throws NullPointerException,
            WrongPasswordException, InexistentElementException;

    }