

public class MyDataLike<E extends Data> implements DataLike, Comparable<MyDataLike<E>> {

    // OVERVIEW: Tipo di dato astratto che intende rappresentare un dato di tipo generico con associato il
    // numero di likes che esso ha ricevuto.

    // TYPICAL ELEMENT: <data, nLikes>

    private E data;
    private Integer nLikes;

    // AF: a(C) = <this.data, this.nLikes>

    // IR: this.data != null && this.nLikes != null && this.nLikes >= 0

    // REQUIRES: data != null && nLikes != null && nLikes >= 0;
    // THROWS: NullPointerException se data == null || nLikes == null, IllegalArgumentException se nLikes < 0;
    // MODIFIES: this;
    // EFFECTS: Istanzia un nuovo oggetto di tipo MyDataLike.
    public MyDataLike(E data, Integer nLikes) throws NullPointerException, IllegalArgumentException {
        if(data == null || nLikes == null) throw new NullPointerException("One or more parameter are set to null!\n");
        if(nLikes < 0) throw new IllegalArgumentException("nLikes must be >= 0!\n");
        this.data = data;
        this.nLikes = nLikes;
    }

    // REQUIRES: dl != null;
    // THROWS: NullPointerException se dl == null;
    // EFFECTS: Restituisce un intero per ordinare dl e this in ordine crescente per numero di like. Se il numero di
    // likes è uguale l'ordinamento è effettuato lessicograficamente per author e per text.
    public int compareTo(MyDataLike dl) throws NullPointerException {
        if(dl == null) throw new NullPointerException("One or more parameter are set to null!\n");
        if(this.nLikes == dl.nLikes) {
            if(((MyData)this.data).getAuthor().compareTo(((MyData)dl.data).getAuthor()) == 0)
                return ((MyData)this.data).getText().compareTo(((MyData)dl.data).getText());
            else
                return ((MyData)this.data).getAuthor().compareTo(((MyData)dl.data).getAuthor());
        }
        else return this.nLikes - dl.nLikes;
    }

    // REQUIRES: true;
    // EFFECTS: restituisce il dato presente in this.
    public E getData() {
        return this.data;
    }

}
