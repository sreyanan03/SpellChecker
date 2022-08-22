import java.util.*;
import java.io.*;

public class SpellChecker implements SpellCheckerInterface {
    
    private HashSet<String> dictionary;

    public SpellChecker(String filename) throws Exception { //gets dictionary
        Scanner scan = null;
        try {
            File f = new File(filename);
            scan = new Scanner(f);
        } catch(Exception e){
            throw new Exception("file not found");
        }
        
        dictionary = new HashSet<>();
        while(scan.hasNext()) {
            String line = scan.nextLine();
            line = line.toLowerCase();
            line = removePunctuation(line);
            line = line.replaceAll("[^a-zA-Z0-9]", "");
            dictionary.add(line);
        } 
    }

    public List<String> getIncorrectWords(String filename) { //gets test file
        Scanner scan = null;
        try {
            File f = new File(filename);
            scan = new Scanner(f); 
        } catch (Exception e){
            return null;
        }

        ArrayList<String> list = new ArrayList<>();
        while(scan.hasNextLine()) { 
            String line = scan.nextLine(); //reading by line
            String [] arr = line.split(" "); //parsing by words
            for(String word: arr) { //reading through loop to lowercase remove punc
                word = word.toLowerCase(); // makes lowercase
                word = removePunctuation(word); //removes punctuation
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                if (!word.isEmpty() && !isCorrectlySpelled(word)) {
                    list.add(word); //adds to list if not spelled correctly
                }
            }
        }
        return list; //returns the list
    }

    private String removePunctuation(String rawWord) {
        String cleanWord = "";
        for(char c: rawWord.toCharArray()) {
            if (Character.isLetter(c) || Character.isDigit(c)) {
                cleanWord+= c;
            }
        }
        return cleanWord;
    }

    private boolean isCorrectlySpelled(String word) {
      if(dictionary.contains(word)) {
          return true;
      } else {
          return false;
      }
    }

    public Set<String> getSuggestions (String word) {
        //System.out.println("Word: " + word);
        HashSet<String> listOfSuggestions = new HashSet<>();
        String newWord;

    //add character
        for (int ascii = 97; ascii <= 122; ascii++) {
            char alphabet = (char) ascii;
            for (int i = 0; i < word.length() +1; i++) {
                newWord = word.substring(0, i) + alphabet + word.substring(i);
                if(dictionary.contains(newWord)) {
                    listOfSuggestions.add(newWord);
                }
            }
        }
      

    //remove character
        for (int i = 0; i < word.length(); i++) {
            newWord = word.substring(0, i) + word.substring(i + 1);
            if(dictionary.contains(newWord)) {
                listOfSuggestions.add(newWord); //do i add punctuation back in?
            }
        }
        

    //swap character
        for (int i = 0; i < word.length() - 1; i++) {
            char[] arr = word.toCharArray();
            char temp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = temp;
            newWord = new String(arr);
            if(dictionary.contains(newWord)) {
                listOfSuggestions.add(newWord);
            }
        }
        return listOfSuggestions;
    }
}
