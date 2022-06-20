
public interface DataLike<E> {
    // OVERVIEW: Tipo di dato astratto che intende rappresentare un dato di tipo generico con associato il
    // numero di likes che esso ha ricevuto.

    // TYPICAL ELEMENT: <data, nLikes>

    // REQUIRES: dl != null;
    // THROWS: NullPointerException se dl == null;
    // EFFECTS: Restituisce un intero per ordinare dl e this in ordine crescente per numero di like. Se il numero di
    // likes è uguale, l'ordinamento è effettuato lessicograficamente per author e per text.
    int compareTo(MyDataLike dl) throws NullPointerException;

    // REQUIRES: true;
    // EFFECTS: restituisce il dato presente in this.
    E getData();
}
