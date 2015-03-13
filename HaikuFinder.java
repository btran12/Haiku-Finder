import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class HaikuFinder {

	public static void main(String[] args){
		final String fileLocation = "Samuel.txt";

		System.out.println(findHaiku(fileLocation));
	}

	//Read in each word from the text file's location
	//Then send it to the countSyllable
	@SuppressWarnings("resource")
	public static String findHaiku(String fileLocation){

		String word;
		int sylCount = 0;
		int unit = 5;   //first verse
		String haiku = "";
		String allHaikus = "";
		Scanner scanFile;

		try {
			scanFile = new Scanner(new FileReader(new File(fileLocation)));

			while(scanFile.hasNext()){
				word = scanFile.next();

				sylCount += countSyllable(word);
				haiku += word + " ";
				
				if(sylCount == unit){
					haiku += "\n";	//New line to separate units
					
					//Change the unit to look for
					if (unit == 5){	//7
						unit += 7;
						
					}else if (unit == 12){	//5
						unit += 5;
					}else if (unit == 17){	//Back to 5
						unit = 5;
						allHaikus += haiku + "-------------\n";
						
						//Reset
						haiku = "";
						sylCount = 0;
					}
				}else if (sylCount > unit){	//If the syllable count don't match up with the units 575
					//Reset
					haiku = "";
					sylCount = 0;
				}
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return allHaikus;
	}
	
	//REFINE LOOKING FOR VOWELS WITH 'e'
	public static int countSyllable(String word){
		//Look for patterns within words
		int vowelsCount = 0;
		int sylCount = 0;
		word = word.toLowerCase();	//Convert to lower case
		char[] strArray = word.toCharArray();
		char lastChar = 0;
		
		for(char c : strArray){
			
			if (c == 'u' || c == 'e' || c == 'o' || c == 'a'|| c == 'i' || c == 'y'){   //Count the number of vowels + y
				vowelsCount++;

			}else{  //When the char go from vowel to non-vowel. Will count only one of the vowels. 
				if (vowelsCount > 0){   
					sylCount++;
				}
				vowelsCount = 0;
			}
			lastChar = c;
		}

		//After the check and a vowel has not been accounted for.
		if (vowelsCount > 0 && lastChar != 'e'){
			sylCount++;
		}
		return sylCount;
	}
}