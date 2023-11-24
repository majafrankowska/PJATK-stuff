import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class XList <T> extends ArrayList<T> {

    public XList(T... t) {

        for (T wrt : t) {
            if (wrt instanceof Set<?>) {
                this.addAll((Set<T>) wrt);
            } else
                this.add(wrt);
        }
    }

    public static <T> XList<T> of(Object... ts) {

        XList<T> xList = new XList<>();

        for (Object wrt : ts) {

            if (wrt instanceof Set<?>) {
                xList.addAll((Set<T>) wrt);
            } else
                xList.add((T) wrt);
        }

        return xList;
    }

    public static XList<String> charsOf(String string) {

        char[] tab = string.toCharArray();
        XList<String> xList = new XList<>();

        for (int i = 0; i < tab.length; i++) {
            xList.add(String.valueOf(tab[i]));
        }

        return xList;
    }

    public static XList<String> tokensOf(String s, String sep) {

        String[] tab = s.split(sep);
        XList<String> l;

        return l = new XList<>(tab);
    }

    public static XList<String> tokensOf(String s) {

        return tokensOf(s, " ");
    }


    public XList<T> union(Collection<T> lista) {

        XList<T> xlist = new XList<>();
        Iterator<T> it = this.iterator();

        while (it.hasNext()) {
            xlist.add(it.next());
        }

        it = lista.iterator();

        while (it.hasNext()) {
            xlist.add(it.next());
        }

        return xlist;
    }

    public XList<T> union(T[] lista) {

        XList<T> xlist = new XList<>();
        Iterator<T> it = this.iterator();

        while (it.hasNext()) {
            xlist.add(it.next());
        }

        for (int i = 0; i < lista.length; i++) {
            xlist.add(lista[i]);
        }

        return xlist;
    }

//    public XList<T> diff(Collection obj) {
//
//        Set<T> s = new HashSet<>(obj);
//
//
//
//        Iterator<T> it = s.iterator();
//
//        while (it.hasNext()){
//            this.remove(it.next());
//        }
//
//
//        return this;
//    }

    public XList<T> diff(Collection obj) {

        Set<T> s = new HashSet<>(obj);
        int size;
        Iterator<T> iterator = this.iterator();
        XList<T> xList = new XList<>();

        while (iterator.hasNext()) {
            xList.add(iterator.next());
        }
        Iterator<T> it;

        do {
            it = s.iterator();
            size = xList.size();

            while (it.hasNext()) {
                xList.remove(it.next());
            }
        } while (size != xList.size());

        return xList;
    }


    public XList<T> unique() {

        Set<T> s = new HashSet<>(this);
        this.clear();
        Iterator<T> it = s.iterator();

        while (it.hasNext()) {
            this.add(it.next());
        }

        return this;
    }


    void sout() {

        Iterator<T> it = this.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
//    public XList<XList<String>> combine() {
//
//        Iterator<T> it = this.iterator();
//        XList<XList<String>> xList = new XList<>();
//
//        while (it.hasNext()){
//            it.next();
//        }
//
//        return xList;
//    }


    public XList<XList<T>> combine() {
        XList<XList<T>> result = new XList<>();
        XList<XList<T>> listaList = new XList<>();
        XList<T> aktualna = new XList<>();
        kombinacjeRekurencyjne(listaList, result, aktualna, 0);
        return result;
    }

    private void kombinacjeRekurencyjne(XList<XList<T>> listaList, XList<XList<T>> wynik, XList<T> aktualna, int indeks) {
        if (indeks == listaList.size()) {
            wynik.add(aktualna);
            return;
        }

        for (T element : listaList.get(indeks)) {
            aktualna.add(element);
            kombinacjeRekurencyjne(listaList, wynik, aktualna, indeks + 1);
            aktualna.remove(aktualna.size() - 1);  // Usuwamy ostatni element, aby wrócić do poprzedniego stanu
        }
        kombinacjeRekurencyjne(listaList, wynik, aktualna, indeks + 1);
    }

//    public XList<XList<T>> combine() {
//        XList<XList<T>> result = new XList<>();
//        kombinacjeRekurencyjne(new XList<>(this), result, new XList<T>(), 0);
//        return result;
//    }
//
//    private static <T> void kombinacjeRekurencyjne(XList<XList<T>> listaList, XList<XList<T>> wynik, XList<T> aktualna, int indeks) {
//        if (indeks == listaList.size()) {
//            wynik.add(aktualna);
//            return;
//        }
//
//        for (T element : listaList.get(indeks)) {
//            aktualna.add(element);
//            kombinacjeRekurencyjne(listaList, wynik, aktualna, indeks + 1);
//            aktualna.remove(aktualna.size() - 1);  // Usuwamy ostatni element, aby wrócić do poprzedniego stanu
//        }
//        kombinacjeRekurencyjne(listaList, wynik, aktualna, indeks + 1);
//    }


    public void forEachWithIndex(BiConsumer<T, Integer> consumer) {

        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i), i);
        }
    }

    public <R> XList<R> collect(Function<T, R> mapper) {

        XList<R> result = new XList<>();

        for (T element : this) {
            result.add(mapper.apply(element));
        }

        return result;
    }

    public String join() {
        return join("");
    }

    public String join(String separator) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < size(); i++) {
            result.append(get(i)).append(separator);
        }

        // Sprawdzamy, czy StringBuilder ma co najmniej separator znaków, zanim spróbujemy skrócić
        if (result.length() > separator.length()) {
            result.setLength(result.length() - separator.length());
        }

        return result.toString();
    }
}

/*
XList<XList<String>> cres = toCombine.combine();
        System.out.println(cres);

        // collect i join
        XList<String> j1 = cres.collect( list -> list.join());
        System.out.println(j1.join(" "));
        XList<String> j2 =cres.collect( list -> list.join("-"));
        System.out.println(j2.join(" "));
 */

// Niesiety ten wynik który ci podałam to jest wynik do którego dąże, natomiast teraz mam taki wynik  po implementacji mojej klasy XList.java w obecnym stanie gdzie combine, collect  i join nie dziala :
