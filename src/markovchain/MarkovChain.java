
package markovchain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MarkovChain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
       BufferedReader b = new BufferedReader(new FileReader("D:\\inputfile.txt"));
       HashMap<String,HashMap<String,Integer>> map = new HashMap();
       String s;
       System.out.println("Analyzing input text");
       while((s=b.readLine())!= null) {
           //s = s.replaceAll("[!@#$%+^&*?:;'_-]", "");
          // s = s.replaceAll("[)(]", "");
           
             s = s.toLowerCase();
           s = s.trim().replaceAll(" +", " ");
           String[] sp = s.split("[ ]");
           for(int i=0;i<sp.length-2;i++) {
               if(!map.containsKey(sp[i] +" "+ sp[i+1])) {
                   HashMap<String,Integer> h = new HashMap();
                   h.put(sp[i+2], 1);
                   map.put(sp[i] +" "+ sp[i+1],h);
               } else {
                  HashMap<String,Integer> h = map.get(sp[i] +" "+ sp[i+1]);
                  if(h.containsKey(sp[i+2])) {
                      h.put(sp[i+2], h.get(sp[i+2]).intValue()+1);
                  } else {
                      h.put(sp[i+2], 1);
                  }
               }
           }
       }
       
       System.out.println("Analysis complete. Specify the first word to randomly generate a sentence");
       BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
       String startWord = in.readLine();
       System.out.print(startWord + " ");
       for(int i=0; i<100; i++) {
           boolean wordSelected;
           int numitems=0;
           int sumoffreq=0;
           int maxfreq=0;
           ArrayList<WordFrequency> list = new ArrayList();
           list.clear();
           wordSelected=false;
           HashMap<String,Integer> con = map.get(startWord);
           if(con==null) 
               continue;
       for(Map.Entry<String, Integer> entry : con.entrySet())
       { 
          WordFrequency wordF = new WordFrequency();
          wordF.setWord(entry.getKey());
          wordF.setFrequency(entry.getValue());
         // numitems++;
          if(wordF!=null){
          sumoffreq+=wordF.getFrequency();
          list.add(wordF);
          }
       }
       Random r = new Random();
       while(!wordSelected) {
           for(int j=0;j<list.size();j++) {
               if(r.nextInt(sumoffreq)+1 <= list.get(j).getFrequency()){
                   wordSelected=true;
                   System.out.print(list.get(j).getWord() + " ");
                   if(list.get(j).getWord().contains(".")) {
                       System.out.println();
                   }
                   startWord=startWord.split("[ ]")[1] + " "+ list.get(j).getWord();
                   break;
               }
           }
       }
         
       }
       System.out.println();
       
    }
}
