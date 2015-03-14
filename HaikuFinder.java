import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class HaikuFinder {

	public static void main(String[] args){
		final String fileLocation = "FILE HERE";//Put file's location here

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
					if (unit == 5){	
						unit += 7;	//Now look for lines with 7 syllables (12 sylCount)
					}else if (unit == 12){
						unit += 5;	//Now look for lines with 5 syllables (17 sylCount)
					}else if (unit == 17){	
						unit = 5;	//Back to 5
						allHaikus += haiku + "-------------\n";	//Separator for each Haiku
						
						//Reset
						haiku = "";
						sylCount = 0;
					}
					
				}else if (sylCount > unit){	//If the syllable count don't matches up with the units 575
					//Reset
					haiku = "";
					sylCount = 0;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("<!>ERROR<!> File not found!");
		}
		return allHaikus;
	}
	
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
		
		//Silent 'e'
		//Problem with count when the last char is not a silent 'e'
		//For Instance, goodbye will return 1 which is wrong and name will return 1 which is correct.
		if (vowelsCount > 0 && lastChar != 'e') sylCount++;
		if (sylCount == 0) sylCount++;	//If syllable count is 0 increase by 1.
		
		return sylCount;
	}
}
