

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;

public class MyDataBoard1<E extends MyData> implements DataBoard<E> {

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

    private ArrayList<MyCategory> board;
    private String admin;
    private String passw;


    // FA: a() = [this.board.get(0), ..., this.board.get(n)] with int n = this.board.size() - 1 &&
    //     proprietario della bacheca = this.admin && password del proprietario della bacheca = this.passw

    // IR: this.board != null && this.admin != null && this.passw != null &&
    //     for all i : 0 <= i < this.board.size() => this.board.get(i) != null &&
    //     for all i, j : 0 <= i < j < this.board.size() => this.board.get(i) != this.board.get(j)
    //                                                     (ogni categoria è diversa per nome e per ogni data contenuto)


    // REQUIRES: admin != null && passw != null;
    // THROWS: NullPointerException se admin == null || passw == null;
    // MODIFIES: this;
    // EFFECTS: Istanzia un nuovo oggetto di tipo MyDataboard1.
    public MyDataBoard1(String admin, String passw) throws NullPointerException {
        // COSTRUTTORE:
        if (admin == null || passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        this.admin = admin;
        this.passw = passw;
        this.board = new ArrayList<MyCategory>();
    }

    // REQUIRES: category != null && passw != null, passw corrisponde alla password del proprietario della bacheca,
    // category non identifica una categoria già presente nella bacheca;
    // THROWS: NullPointerException se category == null || passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // DuplicateException se category identifica una categoria già presente nella bacheca;
    // MODIFIES: this;
    // EFFECTS: Crea una categoria di dati.
    public void createCategory(String category, String passw) throws NullPointerException, WrongPasswordException,
            DuplicateException {
        // controllo NullPointerException
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo le categorie
            for (MyCategory myCategory : this.board) {
                // se la categoria identificata da category è già presente all'interno della bacheca lancio
                // DuplicateException
                if (myCategory.checkName(category)) throw new DuplicateException("Category already " +
                        "presente in this board!\n");
            }
            // altrimenti la creo
            MyCategory tmp = new MyCategory(category);
            this.board.add(tmp);
        }
    }

    // REQUIRES: category != null && passw != null, passw corrisponde alla password del proprietario della bacheca,
    // category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se category == null || passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca;
    // MODIFIES: this;
    // EFFECTS: Rimuove una categoria di dati.
    public void removeCategory(String category, String passw) throws NullPointerException, WrongPasswordException,
            InexistentElementException {
        // controllo NullPointerException
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo le categorie
            for (int i = 0; i < this.board.size(); i++) {
                // se è presente la rimuovo
                if (this.board.get(i).checkName(category)) {
                    this.board.remove(i);
                    return;
                }
            }
            // altrimenti non esiste
            throw new InexistentElementException("No such category present in this board!\n");
        }
    }

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
    public void addFriend(String category, String passw, String friend) throws NullPointerException,
            WrongPasswordException, InexistentElementException, DuplicateException {
        // controllo NullPointerException
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo le categorie
            for (MyCategory myCategory : this.board) {
                // se è presente aggiungo friend
                if (myCategory.checkName(category)) {
                    myCategory.addFriend(friend);
                    return;
                }
            }
            // altrimenti lancio InexsistentElementException
            throw new InexistentElementException("No such category present in this board!\n");
        }
    }

    // REQUIRES: category != null && passw != null, friend != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca, friend identifica un amico già abilitato
    // alla lettura dei dati nella categoria identificata da category;
    // THROWS: NullPointerException se category == null || passw == null || friend == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca || friend
    // non identifica un amico già abilitato alla lettura dei dati presenti nella categoria identificata da category;
    // MODIFIES: this;
    // EFFECTS: Rimuove un amico da una categoria di dati.
    public void removeFriend(String category, String passw, String friend) throws NullPointerException,
            WrongPasswordException, InexistentElementException {
        // controllo NullPointerException
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo le categorie
            for (MyCategory myCategory : this.board) {
                // se essa è presente
                if (myCategory.checkName(category)) {
                    // rimuovo friend (controllo effettuato all'interno della removeFriend() in MyCategory)
                    myCategory.removeFriend(friend);
                    return;
                }
            }
            // altrimenti lancio InexistentElementException per la categoria
            throw new InexistentElementException("No such category present in this board!\n");
        }
    }

    // REQUIRES: category != null && passw != null, dato != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca, dato non corrisponde a nessun dato
    // presente in bacheca;
    // THROWS: NullPointerException se category == null || passw == null || dato == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca;
    // DuplicateException se il dato è già presente nella bacheca.
    // MODIFIES: this;
    // EFFECTS: Inserisce un dato in bacheca.
    public boolean put(String passw, E dato, String category) throws NullPointerException, WrongPasswordException,
            InexistentElementException, DuplicateException {
        // controllo NullPointerException
        if (passw == null) throw new
                NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            int cat = -1;
            // mi prendo la posizione di category all'intrerno di board se essa esiste
            for (int i = 0; i < this.board.size(); i++) {
                if (this.board.get(i).checkName(category))
                    cat = i;
                }

                if (cat == -1) // se la categoria non è presente all'interno della bacheca
                    throw new InexistentElementException("No such category present in this board!\n");

                for (MyCategory myCategory : this.board) {
                    // controllo se il dato è già presente all'interno della categoria
                    if (myCategory.checkData(dato))
                        throw new DuplicateException("Data already present in this board!\n");
                }

                this.board.get(cat).addData(dato);
                this.board.get(cat).addLike(dato);
                return true;
            }
        }

    // REQUIRES: passw != null, dato != null, passw corrisponde alla password del proprietario
    // della bacheca, dato corrisponde ad un dato presente in bacheca;
    // THROWS: NullPointerException se passw == null || dato == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se il dato corrispondente al parametro dato non è presente in bacheca;
    // EFFECTS: Restituisce una copia del dato in bacheca.
    public E get(String passw, E dato) throws NullPointerException, WrongPasswordException, InexistentElementException {
        // controllo NullPointerException
        if (passw == null) throw new
                NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo categorie
            for (MyCategory myCategory : this.board)
                // controllo se il dato è presente
                if (myCategory.checkData(dato))
                    return (E) myCategory.getData(dato);
                // il dato non è presente
            throw new InexistentElementException("No such Data present in this board!\n");
        }
    }

    // REQUIRES: passw != null, dato != null, passw corrisponde alla password del proprietario
    // della bacheca, dato corrisponde ad un dato presente in bacheca;
    // THROWS: NullPointerException se passw == null || dato == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se il dato corrispondente al parametro dato non è presente in bacheca;
    // MODIFIES: this;
    // EFFECTS: Rimuove il dato dalla bacheca e lo restituisce.
    public E remove(String passw, E dato) throws NullPointerException, WrongPasswordException,
            InexistentElementException {
        // controllo NullPointerException
        if (passw == null) throw new
                NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo categorie
            for (MyCategory myCategory : this.board)
                // se il dato è presente lo rimuovo
                if (myCategory.checkData(dato)) {
                    myCategory.removeData(dato);
                    myCategory.removeLike(dato);
                    return dato;
                }
            // il dato non è presente
            throw new InexistentElementException("No such Data present in this board!\n");
        }
    }

    // REQUIRES: friend != null, dato != null, dato corrisponde ad un dato presente in bacheca, friend identifica un
    // amico abilitato alla lettura dei dati nella categoria in cui è presente il dato identificato dall'omonimo
    // parametro;
    // THROWS: NullPointerException se friend == null || dato == null,
    // InexistentElementException se il dato corrispondente al parametro dato non è presente in bacheca,
    // PermissionDeniedException se l'amico non è abilitato alla lettura dei dati presenti nella categoria in cui il
    // parametro dato è presente,
    // DuplicateException se friend ha gia messo like al dato;
    // MODIFIES: this;
    // EFFECTS: Aggiunge un like a un dato.
    public void insertLike(String friend, E dato) throws NullPointerException, PermissionDeniedException,
            InexistentElementException, DuplicateException {
        // ciclo categorie
        for (MyCategory myCategory : this.board) {
            // se il dato è presente
            if (myCategory.checkData(dato)) {
                // se l'amico ha i permessi
                if (myCategory.checkFriend(friend)) {
                    myCategory.incLike(friend, dato);
                    return;
                }
                // friend non ha i permessi
                throw new PermissionDeniedException("This friend is not allowed to like this data!\n");
            }
        }
        // il dato non è presente
        throw new InexistentElementException("No such data present in this board!\n");
    }

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista dei dati in bacheca di una determinata categoria.
    public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException,
            InexistentElementException {
        // controllo NullPointerException
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // ciclo le categorie
            for (MyCategory myCategory : this.board) {
                // se la categoria è presente
                if (myCategory.checkName(category)) {
                    return myCategory.getDataCategory();
                }
            }
            // se la categoria non è presente
            throw new InexistentElementException("No such category present in this board!\n");
        }
    }

    // REQUIRES: passw != null, passw corrisponde alla password del proprietario della bacheca;
    // THROWS: NullPointerException se passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca;
    // EFFECTS: Restituisce un iteratore (senza remove) che genera tutti i dati in bacheca ordinati rispetto al numero
    // di like.
    public Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException {
        // controllo NullPointerException
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid Password!\n");
        else {
            // creo due nuove liste, una per i dati e una per i likes
            ArrayList<Integer> nLikes = new ArrayList<Integer>();
            ArrayList<E> elements = new ArrayList<E>();
            // le liste vengono popolate
            for (MyCategory myCategory : this.board) {
                elements.addAll(myCategory.getDataCategory());
                nLikes.addAll(myCategory.getLikesCategory());
            }
            // creo un nuovo TreeSet di DataLike
           TreeSet<MyDataLike> treeSet = new TreeSet<MyDataLike>();

            // grazie alla compareTo modificata appositamente, i dati vengono inseriti nel treeSet ordinati in modo
            // crescente
            for(int i = 0; i < elements.size(); i++) {
                treeSet.add(new MyDataLike(elements.get(i), nLikes.get(i)));
            }

            elements.clear();
            // poichè mi serve solo l'iteratore di generici (e non di DataLike) ripopolo la lista di generici ordinata
            Iterator itr = treeSet.iterator();
            while(itr.hasNext()) {
                elements.add(((MyDataLike<E>) itr.next()).getData());
            }
            // e restituisco l'iteratore
            return elements.iterator();

        }
    }

    // REQUIRES: friend != null;
    // THROWS: NullPointerException se passw == null;
    // EFFECTS: restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi.
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException {
        // controllo NullPointerException
        if (friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // creo una lista di generici
        ArrayList<E> elements = new ArrayList<E>();
        // la popolo con gli elementi condivisi con friend
        for (MyCategory myCategory : this.board) {
            if (myCategory.checkFriend(friend))
                elements.addAll(myCategory.getDataCategory());
        }
        // restituisco l'iteratore
        return elements.iterator();
    }

    // METODI DI SUPPORTO:

    // REQUIRES: category != null && passw != null, passw corrisponde alla password del proprietario della bacheca,
    // category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se category == null || passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca;
    // EFFECTS: Restituisce il nome della categoria category.
    public String getCategoryName(String category, String passw) throws NullPointerException, WrongPasswordException, InexistentElementException {
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            for (int i = 0; i < this.board.size(); i++)
                if (this.board.get(i).checkName(category)) return this.board.get(i).toString();
        }
        throw new InexistentElementException("No such category present!\n");
    }

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista degli amici di una determinata categoria.
    public List<String> getFriendsCategory(String category, String passw) throws NullPointerException, WrongPasswordException, InexistentElementException {
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            for (MyCategory myCategory : this.board) {
                if (myCategory.checkName(category)) {
                    return myCategory.getFriendsCategory();
                }
            }
            throw new InexistentElementException("No such category present!\n");
        }
    }


    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista dei likes associati ai dati di una determinata categoria.
    public List<Integer> getLikesCategory(String category, String passw) throws NullPointerException, WrongPasswordException, InexistentElementException {
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            for (MyCategory myCategory : this.board) {
                if (myCategory.checkName(category)) {
                    return myCategory.getLikesCategory();
                }
            }
            throw new InexistentElementException("No such category present!\n");
        }
    }



}


