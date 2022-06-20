

import java.util.ArrayList;
import java.util.List;

public class MyCategory<E extends MyData> implements Category<E>{

    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare una categoria appartenente ad una
    // bacheca di dati generici. La categoria possiede un nome che la identifica e tre insiemi che contengono
    // oggetti di tipo E (generici), Like e String e che rappresentano rispettivamente insiemi di dati generici,
    // di Like e di amici. Ogni oggetto di tipo Like è associato ad uno ed uno solo oggetto di tipo generico.
    // L'insieme di String rappresenta gli amici che sono abilitati alla lettura dei
    // dati generici presenti all'interno dell'insieme di dati generici e all'inserzione di like per un determinato
    // oggetto generico.

    // TYPICAL ELEMENT: <category : name, <data_0, ..., data_k>, <like_0, ..., like_k>, <friend_0, ..., friend_j>>

    private String name;
    private ArrayList<E> elements;
    private ArrayList<MyLike> likes;
    private ArrayList<String> friends;

    // FA: a(C) = <category : this.name, <this.elements.get(0), ..., this.elements.get(k)>, <this.likes.get(0), ...,
    // this.likes.get(k)>, <this.friends.get(0), ..., this.friend.get(j)>>
    // with k = this.elements.size() - 1 = this.likes.size() - 1 && j = this.friends.size() - 1

    // IR: this.name != null && this.elements != null && this.likes != null && this.friends != null &&
    //     for all i : 0 <= i < this.elements.size() => this.elements.get(i) != null && this.likes.get(i) != null &&
    //     for all i : 0 <= i < this.friends.size() => this.friends.get(i) != null &&
    //     for all i, j : 0 <= i < j < this.elements.size() => this.elements.get(i) != this.elements.get(j) &&
    //     for all i, j : 0 <= i < j < this.friends.size() => this.friends.get(i) != this.friends.get(j)


    // REQUIRES: name != null;
    // THROWS: NullPointerException se name == null;
    // MODIFIES: this;
    // EFFECTS: Istanzia un nuovo oggetto di tipo MyCategory.
    public MyCategory(String name) throws NullPointerException {
        // COSTRUTTORE:
        if(name == null) throw new NullPointerException("One or more parameter are se to null!\n");
        this.name = name;
        this.elements = new ArrayList<E>();
        this.friends = new ArrayList<String>();
        this.likes = new ArrayList<MyLike>();
    }

    // REQUIRES: friend != null;
    // THROWS: NullPointerException se friend == null;
    // EFFECTS: Restituisce true se friend appartiene all'insieme di amici abilitati alla lettura dei data presenti
    // all'interno della categoria, false altrimenti
    public boolean checkFriend(String friend) throws NullPointerException {
        if(friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if(this.friends.contains(friend)) return true;
        else return false;
    }

    // REQUIRES: friend != null, friend non appartenente all'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    // THROWS: NullPointerException se friend == null, DuplicateException se friend è già presente nell' insieme
    // degli amici abilitati alla lettura dei data presenti all'interno della categoria;
    // MODIFIES: this;
    // EFFECTS: Aggiunge un amico all'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    public void addFriend(String friend) throws DuplicateException, NullPointerException {
        if(friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if(this.checkFriend(friend)) throw new DuplicateException("Friend already presente in this category!\n");
        this.friends.add(friend);
    }

    // REQUIRES: friend != null, friend presente nell'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    // THROWS: NullPointerException se friend == null, InexistentElementException se friend non è presente
    // nell'insieme degli amici abilitati alla lettura dei data presenti all'interno della categoria;
    // MODIFIES: this;
    // EFFECTS: rimuove friend dall'insieme degli amici abilitati alla lettura dei data presenti
    // all'interno della categoria;
    public void removeFriend(String friend) throws NullPointerException, InexistentElementException {
        if(friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if(!(this.checkFriend(friend))) throw new InexistentElementException("Friend is not present in this " +
                "category!\n");
        this.friends.remove(friend);

    }

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // EFFECTS: Restituisce true se data è presente all'interno della categoria, false altrimenti;
    public boolean checkData(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if(this.elements.contains(data)) return true;
        else return false;
    }

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: Aggiunge data alla categoria;
    public void addData(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        else this.elements.add(data);
    }

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: Associa a data un elemento di tipo Like.
    public void addLike(E data) {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        int i = this.elements.indexOf(data);
        MyLike tmp = new MyLike();
        if(i != -1) this.likes.add(tmp);
    }
    // REQUIRES: name != null;
    // THROWS: NullPointerException se name == null;
    // EFFECTS: Restituisce true se il nome della categoria è uguale a name, false altrimenti
    public boolean checkName(String name) {
        if(name == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if(this.name.equals(name)) return true;
        else return false;
    }

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // EFFECTS: Restituisce l'elemento data, che corrisponde al parametro, se esso è presente all'interno della
    // categoria; null altrimenti.
    public E getData(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        int i = this.elements.indexOf(data);
        if(i != -1) return this.elements.get(i);
        else return null;
    }

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: Rimuove data se esso è presente all'interno della categoria.
    public void removeData(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        int i = this.elements.indexOf(data);
        if(i != -1) this.elements.remove(data);
    }

    // REQUIRES: friend != null && data != null, friend non ha già messo like a data;
    // THROWS: NullPointerException se friend == null || data == null, DuplicateException se friend ha già messo like
    // a data (eccezione lanciata all'interno del metodo incLike());
    // MODIFIES: this;
    // EFFECTS: Aggiunge un like di friend a data.
    public void incLike(String friend, E data) throws NullPointerException, DuplicateException {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        int pos = this.elements.indexOf(data);
        if(pos != -1) this.likes.get(pos).incLike(friend);
    }


    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // MODIFIES: this;
    // EFFECTS: rimuove l'oggetto di tipo Like associato a data.
    public void removeLike(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("One or more parameter are se to null!\n");
        int i = this.elements.indexOf(data);
        if(i != -1) this.likes.remove(i);
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce la lista di tutti i dati presenti all'interno della categoria.
    public List<E> getDataCategory() {
        // COSTRUTTORE:
        ArrayList<E> dataCopy = new ArrayList<E>();
        return dataCopy = this.elements;
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce la lista del numero dei like di ogni data presente nella categoria.
    public List<Integer> getLikesCategory() {
        ArrayList<Integer> likesCopy = new ArrayList<Integer>();
        for (MyLike like : this.likes) {
            likesCopy.add(like.getNLikes());
        }
        return likesCopy;
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce la stringa contenente il nome della categoria.
    public String toString() {
        return this.name;
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce la lista degli amici presenti nella categoria.
    public List<String> getFriendsCategory() {
        ArrayList<String> friendsCopy = new ArrayList<String>();
        for (String friends : this.friends) {
            friendsCopy.add(friends);
        }
        return friendsCopy;
    }
}


