package DictionaryCmL;
/**
 * class DictionaryManagement ke thua class Dictionary, quan ly tu dien
 * @author Do Thi Hong Ngat
 * @version 3.0
 * @since 2018-09-28
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class DictionaryManagement extends Dictionary
{

    /**
     * insertFromCommandline de nhap vao tat ca cac tu tu ban phim
     */
    public static void insertFromCommandline()
    {
        Scanner sc = new Scanner(System.in);
        int countWord = sc.nextInt();
        String s = new String();
        String s1 = new String();
        s = sc.nextLine();
        while(countWord>0)
        {
            countWord--;
            s = sc.nextLine();
            s1 = sc.nextLine();
            Word tmp = new Word(s,s1);
            listWord.add(tmp);
        }
    }
    /**
     * insertFromFile de nhap vao tat ca cac tu tu file dictionary.txt
     */
    public static void  insertFromFile()
    {
        File text = new File("dictionary.txt");
        Scanner sc = null;
        try
        {
            sc = new Scanner(text);
            while (sc.hasNextLine())
            {
                String[] s = sc.nextLine().split("\t");
                Word tmp = new Word(s[0], s[1].trim());
                listWord.add(tmp);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Doc file duoi .dict vao tu dien
     */
    public static void insertFromFileDICT() {
        try {
            String content = readFile("defaultDictionary.dict", Charset.defaultCharset());
            String[] words = content.split("@");
            for (String word: words) {
                String result[] = word.split("\r?\n", 2);
                if (result.length >1) {
                    String wordExplain1 = new String();
                    String wordTarget1 = new String();
                    String wordSound1 = new String();
                    if(result[0].contains("/")) {
                        String firstmeaning = result[0].substring(0, result[0].indexOf("/"));
                        String lastSoundMeaning = result[0].substring(result[0].indexOf("/"), result[0].length());
                        wordTarget1 = firstmeaning;
                        wordSound1 = lastSoundMeaning;
                    }
                    else
                    {
                        wordTarget1 = result[0];
                        wordSound1 = "";
                    }
                    wordExplain1 = result[1];
                    listWord.add( new Word(wordTarget1.trim(),  wordSound1.trim() , wordExplain1.trim()));
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Doc file duoi .dict
     * @param path duong dan toi file
     * @param encoding encoding
     * @return file  encoded
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Lookup la ham tim tu
     * @param w la tu can tra nghia
     * @return Word tra ve tu  co word_target la w
    */
     public static Word dictionaryLookup(String w)
    {
        Word w1 = new Word(w);
        Word w2 = new Word(w + "z");
        TreeSet<Word> LW = (TreeSet<Word>) listWord.subSet(w1,w2);
        Iterator<Word> iter = LW.iterator();
        if (iter.hasNext())
        {
            Word s = iter.next();
            if(s.getWord_target().equals(w)) return s ;
        }
        return new Word(w, "","This word is not already existed");
    }

    /**
     * addWord la ham them 1 tu vao tu dien hien tai
     * @param target la tu tieng anh
     * @param explain la nghia tieng viet
     * @param sound la phat am
     * @return tra ve status
     */
    public static String addWord(String target,String sound, String explain)
    {
        target.trim(); sound.trim(); explain.trim();
        target.replaceAll("/t","");
        explain.replaceAll("/t","");
        if (target.equals("")) return "Write a word";
        if (listWord.contains(new Word(target))) return "This word is already existed";
        if (sound.equals("")) return "Write phonetic";
        if (!sound.startsWith("/") || !sound.endsWith("/")) return "Phonetic: Wrong format";
        if (explain.equals("")) return "Write definition";
        Word word = new Word(target,sound,explain);
        listWord.add(word);
        editedWord.addFirst(word);
        if (removedWord.contains(word)) removedWord.remove(word);
        return "Add sucessfully !";
    }

    /**
     * removeWord la ham xoa 1 tu khoi tu dien
     * @param target la tu tieng anh
     * @return tra ve status
     */
    public static String removeWord(String target)
    {
        target.trim();
        target.replaceAll("/t","");
        Word word = new Word(target);
        if (target.equals("")) return "Write a word";
        if (!listWord.contains(word)) return "This word is not already existed";
        listWord.remove(word);
        removedWord.add(word.getWord_target());
        if (favoriteWord.contains(target)) favoriteWord.remove(target);
        if (recentWord.contains(target)) recentWord.remove(target);
        if (editedWord.contains(word)) editedWord.remove(word);
        return "Remove sucessfully !";
    }

    /**
     * editWord la ham sua 1 tu trong tu dien
     * @param target la tu can sua
     * @param explain la nghia moi cua tu do
     * @return tra ve status
     */
    public static String editWord(String target, String explain)
    {
        target.trim(); explain.trim();
        target.replaceAll("/t","");
        explain.replaceAll("/t","");
        Word word = dictionaryLookup(target);
        word.setWord_explain(explain);
        if (target.equals("")) return "Write a word";
        if (!listWord.contains(word)) return  "This word is not already existed";
        if (explain.equals("")) return "Write definition";
        listWord.remove(word);
        listWord.add(word);
        editedWord.addFirst(word);
        if (removedWord.contains(target)) removedWord.remove(target);
        return "Edit successfully !";
    }

    /**
     * dictionaryExportToFile la ham xuat tu dien hien tai ra file
     * @throws IOException
     */
    public static void dictionaryExportToFile() throws IOException
    {
        FileWriter fw = new FileWriter("dictionary.txt");
        for(Word w : listWord) fw.write(w.toString());
        fw.close();
    }

    /**
     * Ham xuat tu dien cua nguoi dung ra file
     * @throws IOException
     */
    public static void exportCustomDictionary() throws IOException
    {
        FileWriter fw = new FileWriter("recentWord.txt");
        for(String w : recentWord ) fw.write(w+"\n");
        fw.close();
        fw = new FileWriter("favoriteWord.txt");
        for(String w : favoriteWord ) fw.write(w+"\n");
        fw.close();
        fw = new FileWriter("editedWord.txt");
        for(Word w : editedWord ) fw.write(w.getWord_target()+"\n"+w.getWord_sound()+"\n"+w.getWord_explain()+"\n");
        fw.close();
        fw = new FileWriter("removedWord.txt");
        for(String w : removedWord ) fw.write(w+"\n");
        fw.close();
    }

    /**
     * nhap du lieu cu cua nguoi dung
     * @throws IOException
     */
    public static void importCustomDictionary() throws IOException
    {
        File text = new File("editedWord.txt");
        Scanner sc = null;
        sc = new Scanner(text);
        while (sc.hasNextLine()){
            String target = sc.nextLine();
            if(target.equals("")) break;
            String sound ="";
            if(sc.hasNextLine())  sound = sc.nextLine();
            String explain="";
            if(sc.hasNextLine()) explain = sc.nextLine();
            editedWord.add(new Word(target,sound,explain));
            listWord.add(new Word(target,sound,explain));
        }
        insertFromFileDICT();
        text = new File("favoriteWord.txt");
        sc = new Scanner(text);
        while (sc.hasNextLine()){
            String target = sc.nextLine();
            if(target.equals("")) break;
            favoriteWord.add(target);
            Word word = dictionaryLookup(target);
            word.setFavorite(true);
            listWord.remove(word);
            listWord.add(word);
        }
        text = new File("recentWord.txt");
        sc = new Scanner(text);
        while (sc.hasNextLine()){
            String target = sc.nextLine();
            if(target.equals("")) break;
            recentWord.add(target);
        }
        text = new File("removedWord.txt");
        sc = new Scanner(text);
        while (sc.hasNextLine()){
            String target = sc.nextLine();
            if(target.equals("")) break;
            listWord.remove(new Word(target));
        }
    }

    /**
     * Khoi tao lai du lieu tu dien ve mac dinh
     */
    public static void resetToDefaultDictionary()
    {
        recentWord.clear();
        favoriteWord.clear();
        editedWord.clear();
        removedWord.clear();
        listWord.clear();
        insertFromFileDICT();
    }


}
