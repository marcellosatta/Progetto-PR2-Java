

import java.util.ArrayList;

public class MyLike implements Like {

    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare il numero di likes associati ad un
    // dato generico e l'insieme degli amici che hanno messo like al medesimo dato generico.

    // TYPICAL ELEMENT: <nLikes, [friend_0, ..., friend_k]> with k = nLikes - 1

    private int nLikes;
    private ArrayList<String> friend;

    // FA: a(c) = <this.nLikes, [this.friend.get(0), ..., this.friend.get(this.nLikes - 1)]>
    // with this.friend.size() = this.nLikes

    // IR: this.nLikes >= 0 && this.friend != null &&
    // for all i : 0 <= i < nLikes => this.friend.get(i) != null &&
    // for all i, j : 0 <= i < j < nLikes => this.friend.get(i) != this.friend.get(j)

    // REQUIRES: true;
    // EFFECTS: istanzia un nuovo oggetto con nLikes = 0 e l'insieme vuoto degli amici.
    public MyLike() {
        this.nLikes = 0;
        this.friend = new ArrayList<String>();
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce il numero di like nLikes di this.
    public int getNLikes() {
        return this.nLikes;
    }

    // REQUIRES: friend != null, friend non ha ancora messo like al data associato a this;
    // THROWS: NullPointerException se friend == null, DuplicateException se friend ha gia messo like aò data
    // associato a this;
    // MODIFIES: this;
    // EFFECTS: Incrementa di uno il numero di like assegnati al data associato a this e aggiunge friend alla lista
    // di amici che hanno messo like al dato associato a this.
    public void incLike(String friend) throws NullPointerException, DuplicateException {
        if (friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (!(this.checkLike(friend))) {
            this.nLikes += 1;
            this.friend.add(friend);
        } else throw new DuplicateException("Friend has already liked this data!\n");
    }

    // REQUIRES: friend != null;
    // THROWS: NullPointerException se friend == null;
    // EFFECTS: Restituisce true se friend ha messo like al data associato a this, false altrimenti.
    public boolean checkLike(String friend) throws NullPointerException {
        if (friend == null) throw new NullPointerException("One or more parameter are se to null!\n");
        if (this.friend.contains(friend)) return true;
        else return false;
    }


}