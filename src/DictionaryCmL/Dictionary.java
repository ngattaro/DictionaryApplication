package DictionaryCmL;
/**
 * class Dictionary luu tru cac tu
 * @author Do Thi Hong Ngat
 * @version 3.0
 * @since 2018-09-28
 */

import java.util.LinkedList;
import java.util.TreeSet;


public class Dictionary extends TreeSet<Word>
{
    public static TreeSet<Word> listWord = new TreeSet<Word>();
    public static LinkedList<String> favoriteWord = new LinkedList<String>();
    public static LinkedList<String> recentWord =new LinkedList<String>();
    public static LinkedList<String> removedWord = new LinkedList<String>();
    public static LinkedList<Word> editedWord = new LinkedList<Word>();
}
