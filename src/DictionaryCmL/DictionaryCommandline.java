package DictionaryCmL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * class DictionaryCommandline ke thua Class DictionaryManagement, xu ly cac lenh nhap tu ban phim
 * @author Do Thi Hong Ngat
 * @version 2.0
 * @since 2018-09-18
 */

public class DictionaryCommandline extends DictionaryManagement
{
    /**
     * constructor rong
     */
    public DictionaryCommandline() {}

    /**
     * ShowAllWord in ra cac tu co trong tu dien
     */
    public static void showAllWords()
    {
        System.out.printf("No        | English             | Vietnamese\n\n");
        int id = 0;
        for (Word w : listWord)
        {
            id++;
            System.out.printf("%-10d| %-20s| %s\n", id, w.getWord_target(), w.getWord_explain());
        }
    }

    /**
     * dictionarySearcher la ham in ra tat ca cac tu co cung tien to
     * @param word_target0 la tien to can tim
     * @return danh sach tu
     */
    public static ArrayList<String> dictionarySearcher(String word_target0)
    {
        Word w1 = new Word(word_target0);
        Word w2 = new Word(word_target0 + "z");
        TreeSet<Word> LW = (TreeSet<Word>) listWord.subSet(w1,w2);
        ArrayList<String> list = new ArrayList<String>();
        if (!LW.isEmpty())
        {
            for(Word word: LW)
            {
                list.add(word.getWord_target());
            }
        }
        return list;
    }

    /**
     * dictionaryBasic nhap vao cac tu moi tu ban phim va in tat ca ra man hinh
     */
    public static void dictionaryBasic()
    {
        System.out.println("Nhap vao so tu va danh sach tu dien:");
        insertFromCommandline();
        showAllWords();
    }

    /**
     * introduction gioi thieu cach dung tu dien Advanced version
     */
    public static void introduction()
    {
        System.out.printf("WELLCOME !!!\n");
        System.out.printf("[L] Lookup\n[S] Search all the relevant word\n[Show] Show All Words with explain\n");
        System.out.printf("[A] Add a word\n[R] Remove a word\n[E] Edit meaning of word\n[F] Export this dictionary to file\n[X] Exit\n");
    }

    /**
     * dictionaryAdvanced nhap cac tu moi tu file; them, sua, xoa tu bang dong lenh
     * in tat ca tu ra man hinh, tra cuu tu dien, xuat tu dien ra file
     */
    public static void dictionaryAdvanced( ) throws IOException
    {
        introduction();
        DictionaryCommandline Ob = new DictionaryCommandline();
        Ob.insertFromFile();
        String ch,w,w1,w2;
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit)
        {
            ch = sc.nextLine();
            switch (ch)
            {
                case "Show" :
                    Ob.showAllWords();
                    break;
                case "L" :
                    System.out.println("Write a word: ");
                    w = sc.nextLine();
                    w = Ob.dictionaryLookup(w).getWord_explain();
                    if (!w.contentEquals("This word is not already existed"))
                    System.out.println("Explain: ");
                    System.out.println(w);
                    break;
                case "S" :
                    System.out.println("Write partial word: ");
                    w = sc.nextLine();
                    ArrayList<String> arrayList = dictionarySearcher(w);
                    for (String s : arrayList) System.out.println(s);
                    break;
                case "A" :
                    System.out.println("Write a word in english: ");
                    w = sc.nextLine();
                    System.out.println("Write the phonetic of word: ");
                    w1 = sc.nextLine();
                    System.out.println("Write the meaning of word: ");
                    w2 = sc.nextLine();
                    System.out.println(addWord(w,w1,w2));
                    break;
                case "R" :
                    System.out.println("Write a word: ");
                    w = sc.nextLine();
                    System.out.println(removeWord(w));
                    break;
                case "E" :
                    System.out.println("Write a word in english: ");
                    w = sc.nextLine();
                    System.out.println("Write the new meaning of word: ");
                    w1 = sc.nextLine();
                    System.out.println(editWord(w,w1));
                    break;
                case "F" :
                    dictionaryExportToFile();
                    System.out.println("Export Successfully!");
                    break;
                case "X" :
                    exit = true;
                    break;
            }

        }
    }
    public static void main(String[] args) {
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        try {
            dictionaryCommandline.dictionaryAdvanced();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
