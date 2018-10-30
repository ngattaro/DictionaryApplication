package DictionaryCmL;

/**
 * class Word luu tru tu va nghia
 * @author Do Thi Hong Ngat
 * @version 3.0
 * @since 2018-09-28
 */

public class Word implements Comparable<Word>
{
    private String word_target, word_explain, word_sound;
    private boolean isFavorite;
    public String getWord_sound() {
        return word_sound;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getWord_explain()
    {
        return word_explain;
    }

    public String getWord_target()
    {
        return word_target;
    }

    public void setWord_sound(String word_sound) {
        this.word_sound = word_sound;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setWord_explain(String word_explain)
    {
        this.word_explain = word_explain;
    }

    public void setWord_target(String word_target)
    {
        this.word_target = word_target;
    }

    /**
     * Khoi tao khi chi co tu tieng anh
     * @param target
     */
    public Word(String target)
    {
        setWord_target(target);
    }

    /**
     * phuong thuc khoi tao
     * @param target la tu tieng anh
     * @param explain la nghia tieng viet
     */
    public Word(String target, String explain)
    {
        setWord_explain(explain);
        setWord_target(target);
    }

    /**
     * constructor
     * @param target tu tieng anh
     * @param sound phat am
     * @param explain tu tieng viet
     */
    public Word(String target,String sound, String explain)
    {
        setWord_explain(explain);
        setWord_target(target);
        setWord_sound(sound);
    }

    /**
     * in ra thong tin cua tu
     * @return String tra ve tu vung , phat am va nghia cua tu
     */
    public String toString ()
    {
        return getWord_target() + "    " + getWord_sound()+"\n" + getWord_explain() + "\n";
    }
    /**
     * in ra thong tin cua tu
     * @return String tra ve tu vung, phat am va nghia cua tu
     */
    public String toString2()
    {
        String w = getWord_explain();
        int m = w.indexOf("\n",0);
        if (m!=-1) m = w.indexOf("\n", m+1);
        if (m==-1) m = w.length();

        return getWord_target() + "    " + getWord_sound() + "\n" +w.substring(0,m) ;
    }
    /**
     * compareTo la ham so sanh 2 tu theo thu tu tu dien, implement cua Treeset
     * @param w la tu can so sanh
     * @return int la ket qua so sanh
     */
    @Override
    public int compareTo(Word w)
    {
        return this.getWord_target().compareTo(w.getWord_target());
    }

}
