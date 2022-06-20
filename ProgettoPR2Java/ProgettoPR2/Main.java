

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws DuplicateException, InexistentElementException,
            WrongPasswordException, PermissionDeniedException {

        // TEST ECCEZIONI PRIMA IMPLEMENTAZIONE
        System.out.println("TEST ECCEZIONI PRIMA IMPLEMENTAZIONE\n");
        MyDataBoard1 b1 = new MyDataBoard1("Marcello", "123");
        testEccezioni(b1);
        // TEST ECCEZIONI SECONDA IMPLEMENTAZIONE
        System.out.println("TEST ECCEZIONI SECONDA IMPLEMENTAZIONE\n");
        MyDataBoard2 b2 = new MyDataBoard2("Marcello","123");
        testEccezioni(b2);

        // TEST APP PRIMA IMPLEMENTAZIONE
        MyDataBoard1 b1App = new MyDataBoard1("Marcello", "123");
        System.out.println("TEST APP PRIMA IMPLEMENTAZIONE\n");
        testApp(b1App);
        // TEST APP SECONDA IMPLEMENTAZIONE
        MyDataBoard2 b2App = new MyDataBoard2("Marcello", "123");
        System.out.println("TEST APP SECONDA IMPLEMENTAZIONE\n");
        testApp(b2App);

    }


    public static void testEccezioni(DataBoard board) throws DuplicateException, InexistentElementException, WrongPasswordException, PermissionDeniedException {



        // TEST createCategory():
        System.out.println("TEST createCategory():\n");
        board.createCategory("Prima Categoria","123");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.createCategory(null, null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            board.createCategory("Seconda Categoria","321");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test DuplicateException:
        try {
            System.out.println("Mi aspetto che venga lanciata la DuplicateException.");
            board.createCategory("Prima Categoria","123");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST removeCategory():
        System.out.println("TEST removeCategory():\n");
        board.removeCategory("Prima Categoria","123");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.removeCategory(null,null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            board.createCategory("Seconda Categoria","123");
            board.removeCategory("Seconda Categoria","321");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexsistentElementException:
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            board.removeCategory("Prima Categoria","123");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST addFriend();
        System.out.println("TEST addFriend():\n");

        board.addFriend("Seconda Categoria", "123", "Giorgio");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.addFriend(null, null, null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            board.addFriend("Seconda categoria", "321", "Simone");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistentElementException:
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            board.addFriend("Terza Categoria","123","Simone");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test DuplicateException:
        try {
            System.out.println("Mi aspetto che venga lanciata la DuplicateException.");
            board.addFriend("Seconda Categoria","123","Giorgio");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST removeFriend();
        System.out.println("TEST removeFriend():\n");
        board.removeFriend("Seconda Categoria","123","Giorgio");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.removeFriend(null,null,null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            board.addFriend("Seconda Categoria", "123", "Simone");
            board.removeFriend("Seconda Categoria","321","Simone");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistentElementException
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            board.removeFriend("Seconda Categoria","123","Francesco");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST put();

        System.out.println("TEST put():\n");
        MyData primoDato = new MyData("Marcello", "I topi non avevano nipoti");
        board.put("123", primoDato,"Seconda Categoria");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.put(null, null,null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            board.put("321", primoDato, "Seconda Categoria");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test DuplicateException:
        try {
            System.out.println("Mi aspetto che venga lanciata la DuplicateException.");
            board.put("123", primoDato, "Seconda Categoria");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistetElementException:
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            board.put("123", primoDato, "Terza Categoria");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST get();
        System.out.println("TEST get():\n");

        MyData newDato = board.get("123", primoDato);
        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            newDato = board.get(null, null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            newDato = board.get("321", primoDato);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistentElementException
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            MyData datoInesistente = new MyData("Nessuno", "Niente");
            newDato = board.get("123", datoInesistente);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST remove();
        System.out.println("TEST remove():\n");
        newDato = board.remove("123", primoDato);

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            newDato = board.remove(null, null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            newDato = board.remove("321", primoDato);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistentElementException:
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            MyData datoInesistente = new MyData("Nessuno", "Niente");
            newDato = board.remove("123", datoInesistente);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST insertLike();
        System.out.println("TEST insertLike():\n");

        board.addFriend("Seconda Categoria", "123", "Matteo");
        MyData secondoDato = new MyData("d", "d");
        board.put("123", secondoDato, "Seconda Categoria");

        board.insertLike("Matteo", secondoDato);

        // testNullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.insertLike(null, null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test PermissionDeniedException:
        try {
            System.out.println("Mi aspetto che venga lanciata la PermissionDeniedException.");
            board.insertLike("Francesco", secondoDato);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistentElementException:
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            MyData datoInesistente = new MyData("Nessuno", "Niente");
            board.insertLike("Matteo", datoInesistente);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test DuplicateException:
        try {
            System.out.println("Mi aspetto che venga lanciata la DuplicateException.");
            board.insertLike("Matteo", secondoDato);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST getDataCategory();

        System.out.println("TEST getDataCategory():\n");
        board.createCategory("Lista", "123");
        MyData a = new MyData("a","a");
        MyData b = new MyData("b","b");
        MyData c = new MyData("c","c");

        board.put("123", a, "Lista");
        board.put("123", b, "Lista");
        board.put("123", c, "Lista");

        List<Data> lista = new ArrayList();

        lista = board.getDataCategory("123", "Lista");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            lista = board.getDataCategory(null, null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test InexistentElementException:
        try {
            System.out.println("Mi aspetto che venga lanciata la InexistentElementException.");
            lista = board.getDataCategory("123", "Categoria che non esiste");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            lista = board.getDataCategory("321", "Lista");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }


        // TEST getIterator();

        System.out.println("TEST getIterator():\n");
        Iterator primoIteratore = null;

        primoIteratore = board.getIterator("123");
        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            Iterator iter = board.getIterator(null);
            //primoIteratore = board.getIterator(null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
        // test WrongPasswordException:
        try {
            System.out.println("Mi aspetto che venga lanciata la WrongPasswordException.");
            primoIteratore = board.getIterator("321");
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

        // TEST getFriendIterator();
        System.out.println("TEST getFriendIterator():\n");
        Iterator secondoIteratore = lista.iterator();

        board.addFriend("Lista", "123", "Matteo");
        board.getFriendIterator("Matteo");

        // test NullPointerException:
        try {
            System.out.println("Mi aspetto che venga lanciata la NullPointerException.");
            board.getFriendIterator(null);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }

    }


    public static void testApp(DataBoard board) throws DuplicateException, WrongPasswordException, InexistentElementException, PermissionDeniedException {

        // Creazione nuove categorie
        board.createCategory("Categoria1","123");
        board.createCategory("Categoria2","123");
        board.createCategory("Categoria3","123");

        System.out.println("\n");

        // Stampa elenco categorie
        System.out.println("STAMPA LISTA DELLE CATEGORIE PRESENTI IN BACHECA:");
        for(int i = 1; i < 4; i++) {
            System.out.println(board.getCategoryName("Categoria" + i, "123").toString());
        }
        System.out.println("\n");

        // Creazione nuovi dati
        MyData data1 = new MyData("author1", "text1");
        MyData data2 = new MyData("author2", "text2");
        MyData data3 = new MyData("author3", "text3");
        MyData data4 = new MyData("author4", "text4");
        MyData data5 = new MyData("author5", "text5");
        MyData data6 = new MyData("author6", "text6");
        MyData data7 = new MyData("author7", "text7");
        MyData data8 = new MyData("author8", "text8");
        MyData data9 = new MyData("author9", "text9");

        // Inserimento dati in bacheca
        board.put("123", data1, "Categoria1");
        board.put("123", data2, "Categoria1");
        board.put("123", data3, "Categoria1");
        board.put("123", data4, "Categoria2");
        board.put("123", data5, "Categoria2");
        board.put("123", data6, "Categoria2");
        board.put("123", data7, "Categoria3");
        board.put("123", data8, "Categoria3");
        board.put("123", data9, "Categoria3");


        // Inserimento amici nelle categorie
        board.addFriend("Categoria1", "123", "friend1");
        board.addFriend("Categoria1", "123", "friend2");
        board.addFriend("Categoria1", "123", "friend3");
        board.addFriend("Categoria2", "123", "friend4");
        board.addFriend("Categoria2", "123", "friend5");
        board.addFriend("Categoria2", "123", "friend6");
        board.addFriend("Categoria3", "123", "friend7");
        board.addFriend("Categoria3", "123", "friend8");
        board.addFriend("Categoria3", "123", "friend9");

        // Stampa lista amici per categoria
        System.out.println("STAMPA TUTTI GLI AMICI PRESENTI IN OGNI CATEGORIA:");
        for(int j = 1; j < 4; j++) {
            System.out.println("STAMPA LISTA DI AMICI APPARTENENTI ALLA CATEGORIA " + j + ":");
            List testList = board.getFriendsCategory("Categoria" + j, "123");
            // Stampa lista restituita da getDataCategory()
            for (int i = 0; i < testList.size(); i++)
                System.out.println((String) testList.get(i));
        }
        System.out.println("\n");

        // Inserimento likes
        board.insertLike("friend1", data1);
        board.insertLike("friend2", data1);
        board.insertLike("friend3", data1);
        board.insertLike("friend4", data4);
        board.insertLike("friend5", data4);
        board.insertLike("friend7", data7);

        // Stampa dati con relativi likes per categoria
        System.out.println("STAMPA TUTTI I DATI PRESENTI NELLA BACHECA CON IL LORO NUMERO DI LIKE RICEVUTI:");
        for(int j = 1; j < 4; j++) {
            System.out.println("STAMPA LISTA DI DATI APPARTENENTI ALLA CATEGORIA " + j + ":");
            List testList = board.getDataCategory("123", "Categoria" + j);
            List testList2 = board.getLikesCategory("Categoria" + j, "123");
            for (int i = 0; i < testList.size(); i++) {
                ((MyData) testList.get(i)).display();
                System.out.println("Numero di likes: " + testList2.get(i) + "\n");
            }
        }

        // Stampa iteratore dati
        System.out.println("\n");
        System.out.println("STAMPA ITERATORE DATI (ORDINATI PER NUMERO DI LIKES (CRESCENTE)):");
        Iterator itr = board.getIterator("123");
        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("\n");

        // Stampa iteratore friend
        System.out.println("STAMPA ITERATORE DATI CONDIVISI CON FRIEND (friend1):");
        itr = board.getFriendIterator("friend1");
        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("\n");

        // Test removeFriend()
        board.removeFriend("Categoria1","123", "friend1");
        // Stampa amici categoria per testare removeFriend()
        System.out.println("STAMPA TUTTI GLI AMICI PRESENTI IN OGNI CATEGORIA (DOPO removeFriend(Categoria1, friend1)):");
        for(int j = 1; j < 4; j++) {
            System.out.println("STAMPA LISTA DI AMICI APPARTENENTI ALLA CATEGORIA " + j + ":");
            List testList = board.getFriendsCategory("Categoria" + j, "123");
            for (int i = 0; i < testList.size(); i++)
                System.out.println((String) testList.get(i));
        }
        System.out.println("\n");

        // Test removeCategory()
        board.removeCategory("Categoria1", "123");
        // Stampa elenco categorie dopo removeCategory()
        System.out.println("STAMPA LISTA DELLE CATEGORIE PRESENTI IN BACHECA (DOPO removeCategory(Categoria1)):");
        for(int i = 2; i < 4; i++) {
            System.out.println(board.getCategoryName("Categoria" + i, "123").toString());
        }
        System.out.println("\n");

        // Test get()
        System.out.println("STAMPA data7 (TEST get(data7)):");
        MyData testEl = board.get("123", data7);
        // Stampa get()
        testEl.display();

        System.out.println("\n");

        // Test remove()
        board.remove("123", data5);
        // Stampa dati categoria in cui è presente data5 (dopo remove())
        System.out.println("STAMPA DATI PRESENTI NELLA CATEGORIA2 (TEST remove(data5)):");
        List testList = board.getDataCategory("123", "Categoria2");
        List testList2 = board.getLikesCategory("Categoria2", "123");
        for (int i = 0; i < testList.size(); i++) {
            ((MyData) testList.get(i)).display();
            System.out.println("Numero di likes: " + testList2.get(i) + "\n");
        }
        System.out.println("\n");







    }
}
