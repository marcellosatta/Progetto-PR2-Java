

import java.util.*;

public class MyDataBoard2<E extends MyData> implements DataBoard<E> {
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

    private String admin;
    private String passw;
    private HashMap<String, HashSet<E>> elements;
    private HashMap<String, HashSet<String>> friends;
    private HashMap<E, MyLike> likes;

    // FA: La bacheca è rappresentata da una HashMap le cui chiavi sono i nomi delle categorie e i valori insiemi di
    // generici E (HashSet). Gli amici abilitati alla lettura dei dati all'interno delle categorie sono
    // rappresentati da un'HashMap le cui chiavi sono i nomi delle categorie e i valori i nomi degli amici.
    // Un'altra HashMap è utilizzata per mappare su ogni dato un oggetto di tipo Like che rappresenta i likes che
    // il dato ha ricevuto. Admin e passw rappresentano rispettivamente nome e passw del proprietario.
    //
    // Formalmente:
    //
    // a(C) = [(key = category_i, values = this.elements.get(category_i),
    //         (key = category_i, values = this.friends.get(category_i),
    //         (key = data_l, values = this.likes.get(data_l)]
    //         with i = 1, ..., (this.elements.keySet().size() = this.friends.keySet().size()) &&
    //         l = 1, ..., this.likes.keySet().size() &&
    //         proprietario della bacheca = this.admin && password del proprietario della bacheca = this.passw

    // IR: this.admin != null && this.passw != null %% this.elements != null && this.friends != null &&
    //     this.like != null &&
    //     this.elements.get(category_i) != null && this.friends.get(category_i) != null &&
    //     this.likes.get(data_l) != null
    //     with i = 1, ..., (this.elements.keySet().size() = this.friends.keySet().size()) &&
    //     l = 1, ..., this.likes.keySet().size()
    //     La proprietà che ogni dato e ogni amico presente in una categoria non può avere duplicati è espresso
    //     intrinsicamente nella proprieta degli HashSet (definiti come insiemi).

    // REQUIRES: admin != null && passw != null;
    // THROWS: NullPointerException se admin == null || passw == null;
    // MODIFIES: this;
    // EFFECTS: Istanzia un nuovo oggetto di tipo MyDataboard1.
    public MyDataBoard2(String admin, String passw) throws NullPointerException {
        // COSTRUTTORE:
        if (admin == null || passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        this.admin = admin;
        this.passw = passw;
        this.elements = new HashMap<String, HashSet<E>>();
        this.friends = new HashMap<String, HashSet<String>>();
        this.likes = new HashMap<E, MyLike>();
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
        if (category == null || passw == null)
            throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // controllo che la categoria non sia già presente all'interno della bacheca
            if (this.elements.keySet().contains(category)) throw new DuplicateException("Category already present in " +
                    "this board!\n");
            // creo una nuova categoria
            this.elements.put(category, new HashSet<E>());
            this.friends.put(category, new HashSet<String>());
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
        if (category == null || passw == null)
            throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // controllo se la categoria è presente nella bacheca
            if (!(this.elements.containsKey(category))) throw new InexistentElementException("Category is not " +
                    "present in this board!\n");
            // elimino i like degli elementi presenti in category
            List<E> tmp = getDataCategory(this.passw, category);
            for (int i = 0; i < tmp.size(); i++) {
                likes.remove(tmp.get(i));
            }
            // rimuovo la categoria
            this.elements.remove(category);
            this.friends.remove(category);
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
        if (category == null || passw == null || friend == null)
            throw new NullPointerException("One or more parameter" +
                    " are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // controllo se la categoria è presente
            if (!(this.elements.containsKey(category))) {
                throw new InexistentElementException("Category is not present in this board!\n");
            }
            // controllo se friend è già presente in category
            if (this.friends.get(category).contains(friend)) throw new DuplicateException("Friend is already present " +
                    "in this category!\n");
            // aggiungo friend
            this.friends.get(category).add(friend);
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
        if (category == null || passw == null || friend == null)
            throw new NullPointerException("One or more parameter" +
                    " are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // controllo se category è presente in bacheca
            if (!(this.elements.containsKey(category))) throw new InexistentElementException("Category is not " +
                    "present in this board!\n");
            // controllo se friend è presente in category
            if (!(this.friends.get(category).contains(friend))) throw new InexistentElementException("Friend is not " +
                    "present in this category!\n");
            // rimuovo friend dalla category
            this.friends.get(category).remove(friend);
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
        if (category == null || passw == null || dato == null) throw new NullPointerException("One or more parameter" +
                " are set to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // controllo se category è presente in bacheca
            if (!(this.elements.containsKey(category))) throw new InexistentElementException("Category is not " +
                    "present in this board!\n");
            for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
                for (E i : entry.getValue())
                    // controllo se il dato è già presente in bacheca
                    if (i.equals(dato)) throw new DuplicateException("Data is already present in " +
                            "this board!\n");
             // aggiungo il dato
            this.elements.get(category).add(dato);
            // creo la mappa dato - like
            this.likes.put(dato, new MyLike());
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
        if (passw == null || dato == null) throw new NullPointerException("One or more parameter" +
                " are set to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
                for (E i : entry.getValue())
                    // se il dato è presente lo restituisco
                    if (i.equals(dato)) return i;
             // il dato non è presente
            throw new InexistentElementException("Data is not present in this board!\n");
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
        if (passw == null || dato == null) throw new NullPointerException("One or more parameter" +
                " are set to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
                for (E i : entry.getValue())
                    // cerco il dato
                    if (i.equals(dato)) {
                        E tmp = i;
                        // lo rimuovo
                        entry.getValue().remove(i);
                        return tmp;
                    }
            // se il dato non è presente
            throw new InexistentElementException("Data is not present in this board!\n");
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
        // controllo NullPointerException
        if (friend == null || dato == null) throw new NullPointerException("One or more parameter are set to null!\n");
        String category;
        category = null;
        // cerco la categoria del dato
        for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
            if (entry.getValue().contains(dato))
                category = entry.getKey();
         // il dato non è presente
        if (category == null) throw new InexistentElementException("No such data present in " +
                "this board!\n");
        // controllo se l'amico è abilitato
        if (!(this.friends.get(category).contains(friend)))
            throw new PermissionDeniedException("This friend is not allowed to like this data!\n");

        // incremento i likes di dato
        this.likes.get(dato).incLike(friend);

    }

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista dei dati in bacheca di una determinata categoria.
    public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException,
            InexistentElementException {
        // controlo NullPointerException
        if (category == null || passw == null)
            throw new NullPointerException("One or more parameter are se to null!\n");
        // controllo d'identità
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            // controllo se la categoria è presente
            if (!(this.elements.keySet().contains(category))) throw new InexistentElementException("No such category" +
                    " present in this board!\n");
            // restituisco la lista contenente i dati della categoria
            return new ArrayList<E>(this.elements.get(category));
        }

    }

    // REQUIRES: passw != null, passwcorrisponde alla password del proprietario della bacheca;
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
            // creo due liste, una di generici, una di Integer
            ArrayList<Integer> nLikes = new ArrayList<Integer>();
            ArrayList<E> elements = new ArrayList<E>();
            // le popolo rispettivamente con i data della bacheca e con il numero di likes associato ad ogni data
            for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
                for (E i : entry.getValue())
                    elements.add(i);
            for (int i = 0; i < elements.size(); i++) {
                nLikes.add(this.likes.get(elements.get(i)).getNLikes());

            }
            // creo un TreeSet di DataLike
            TreeSet<MyDataLike> treeSet = new TreeSet<MyDataLike>();

            // aggiungo ogni oggetto DataLike ordinato in modo crescente grazie alla compareTo modificata
            for (int i = 0; i < elements.size(); i++) {
                treeSet.add(new MyDataLike(elements.get(i), nLikes.get(i)));
            }

            elements.clear();
            // ripopolo la lista di generici poichè devo restituire solo i dati
            Iterator itr = treeSet.iterator();
            while (itr.hasNext()) {
                elements.add(((MyDataLike<E>) itr.next()).getData());
            }
            // restituisco l'iteratore
            return elements.iterator();
        }
    }

    // REQUIRES: passw != null, passwcorrisponde alla password del proprietario della bacheca;
    // THROWS: NullPointerException se passw == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca;
    // EFFECTS: restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi.
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException {
        // controllo NullPointerException
        if (friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        // creo una lista di generici
        ArrayList<E> elements = new ArrayList<E>();
        // la popolo con i data accessibili a friend
        for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
            if (this.friends.get(entry.getKey()).contains(friend)) {
                for (E i : entry.getValue())
                    elements.add(i);
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
    public String getCategoryName(String category, String passw) throws InexistentElementException {
        if (this.friends.get(category) != null) return category;
        throw new InexistentElementException("No such category present!\n");

    }

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista degli amici di una determinata categoria.
    public List<String> getFriendsCategory(String category, String passw) throws NullPointerException,
            WrongPasswordException, InexistentElementException {
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid password!\n");
        else {
            if(this.friends.keySet().contains(category)) return new ArrayList<>(this.friends.get(category));
            }
        throw new InexistentElementException("No such category present!\n");

    }

    // REQUIRES: passw != null, category != null, passw corrisponde alla password del proprietario
    // della bacheca, category identifica una categoria presente nella bacheca;
    // THROWS: NullPointerException se passw == null || category == null,
    // WrongPasswordException se passw non corrisponde alla password del proprietario della bacheca,
    // InexistentElementException se category non identifica una categoria presente all'interno della bacheca
    // EFFECTS: Crea la lista dei likes associati ai dati di una determinata categoria.
    public List<Integer> getLikesCategory(String category, String passw) throws NullPointerException, WrongPasswordException, InexistentElementException {
        if (passw == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (!(this.passw.equals(passw))) throw new WrongPasswordException("Invalid Password!\n");
        else {
            ArrayList<Integer> nLikes = new ArrayList<Integer>();
            ArrayList<E> elements = new ArrayList<E>();
            for (Map.Entry<String, HashSet<E>> entry : this.elements.entrySet())
                for (E i : entry.getValue())
                    elements.add(i);
            for (int i = 0; i < elements.size(); i++) {
                if(this.elements.get(category).contains(elements.get(i)))
                    nLikes.add(this.likes.get(elements.get(i)).getNLikes());
            }
            return nLikes;
        }
    }
}