
import java.util.List;

public interface Category<E> {

    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare una categoria appartenente ad una
    // bacheca di dati generici. La categoria possiede un nome che la identifica e tre insiemi che contengono
    // oggetti di tipo E (generici), Like e String e che rappresentano rispettivamente insiemi di dati generici,
    // di Like e di amici. Ogni oggetto di tipo Like è associato ad uno ed uno solo oggetto di tipo generico.
    // L'insieme di String rappresenta gli amici che sono abilitati alla lettura dei
    // dati generici presenti all'interno dell'insieme di dati generici e all'inserzione di like per un determinato
    // oggetto generico.

    // TYPICAL ELEMENT: <category : name, <data_0, ..., data_k>, <like_0, ..., like_k>, <friend_0, ..., friend_j>>

    // REQUIRES: friend != null;
    // THROWS: NullPointerException se friend == null;
    // EFFECTS: Restituisce true se friend appartiene all'insieme di amici abilitati alla lettura dei data presenti
    // all'interno della categoria, false altrimenti
    boolean checkFriend(String friend) throws NullPointerException;

    // REQUIRES: friend != null, friend non appartenente all'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    // THROWS: NullPointerException se friend == null, DuplicateException se friend è già presente nell' insieme
    // degli amici abilitati alla lettura dei data presenti all'interno della categoria;
    // MODIFIES: this;
    // EFFECTS: Aggiunge un amico all'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    void addFriend(String friend) throws DuplicateException, NullPointerException;

    // REQUIRES: friend != null, friend presente nell'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    // THROWS: NullPointerException se friend == null, InexistentElementException se friend non è presente
    // nell'insieme degli amici abilitati alla lettura dei data presenti all'interno della categoria;
    // MODIFIES: this;
    // EFFECTS: rimuove friend dall'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    void removeFriend(String friend) throws NullPointerException, InexistentElementException;

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // EFFECTS: Restituisce true se data è presente all'interno della categoria, false altrimenti;
    boolean checkData(E data) throws NullPointerException;

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: Aggiunge data alla categoria;
    void addData(E data) throws NullPointerException;

    // REQUIRES: name != null;
    // THROWS: NullPointerException se name == null;
    // EFFECTS: Restituisce true se il nome della categoria è uguale a name, false altrimenti
    boolean checkName(String name);

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // EFFECTS: Restituisce l'elemento data, che corrisponde al parametro, se esso è presente all'interno della
    // categoria; null altrimenti.
    E getData(E data) throws NullPointerException;

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: Rimuove data se esso è presente all'interno della categoria.
    void removeData(E data) throws NullPointerException;

    // REQUIRES: friend != null && data != null, friend non ha già messo like a data;
    // THROWS: NullPointerException se friend == null || data == null, DuplicateException se friend ha già messo like
    // a data (eccezione lanciata all'interno del metodo incLike());
    // MODIFIES: this;
    // EFFECTS: Aggiunge un like di friend a data.
    void incLike(String friend, E data) throws NullPointerException, DuplicateException;

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: rimuove l'oggetto di tipo Like associato a data.
    void removeLike(E data) throws NullPointerException;

    // REQUIRES: true;
    // EFFECTS: Restituisce la lista di tutti i dati presenti all'interno della categoria.
    List<E> getDataCategory();

    // REQUIRES: true;
    // EFFECTS: Restituisce la lista del numero dei like di ogni data presente nella categoria.
    List<Integer> getLikesCategory();

    // REQUIRES: true;
    // EFFECTS: Restituisce la lista degli amici presenti nella categoria.
    List<String> getFriendsCategory();

}
