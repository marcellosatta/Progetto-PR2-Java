

public interface Like {

    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare il numero di likes associati ad un
    // dato generico e l'insieme degli amici che hanno messo like al medesimo dato generico.

    // TYPICAL ELEMENT: <nLikes, [friend_0, ..., friend_k]> with k = nLikes - 1

    // REQUIRES: true;
    // EFFECTS: Restituisce il numero di like nLikes di this.
    int getNLikes();

    // REQUIRES: friend != null, friend non ha ancora messo like al data associato a this;
    // THROWS: NullPointerException se friend == null, DuplicateException se friend ha gia messo like aò data
    // associato a this;
    // MODIFIES: this;
    // EFFECTS: Incrementa di uno il numero di like assegnati al data associato a this e aggiunge friend alla lista
    // di amici che hanno messo like al dato associato a this.
    void incLike(String friend) throws NullPointerException, DuplicateException;

    // REQUIRES: friend != null;
    // THROWS: NullPointerException se friend == null;
    // EFFECTS: Restituisce true se friend ha messo like al data associato a this, false altrimenti.
    boolean checkLike(String friend) throws NullPointerException;

}
