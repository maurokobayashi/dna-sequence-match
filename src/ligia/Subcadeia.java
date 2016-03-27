package ligia;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Subcadeia {
	
	static int DEFAULT_MAX_DISTANCE = 2;
	static String INPUT_FILENAME = "jakarta.txt";
	static String OUTPUT_FILENAME = "resultado.txt";
	static String[] padroes = {
		"actgcttctg",
		"aggaggctgg",
		"tacatgccat",
		"cctcagcatc", 
		"gcaacgttca", 
		"gacattgact"
	};

	public static void main(String[] args) {
		createOutputFile();

		System.out.println("Lendo o arquivo '"+INPUT_FILENAME+"'...");
		appendToFile("Results for partial matching on DNA Sequences");
		
		int maxDistance = (args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_MAX_DISTANCE);
		appendToFile("Maximum distance considered = "+maxDistance);
		appendToFile("Test results for DNA Sequence of Virus Type 2");
		
		try {
			ArrayList<String> dnaSequences = readFile(INPUT_FILENAME);
			appendToFile("Length of DNA Sequence of Virus Type 2 = "+dnaSequences.size());
			System.out.println(dnaSequences);
			
			for (int i=0; i<padroes.length; i++) {
				appendToFile("\n\nTest - Pattern P = <"+padroes[i]+"> - DNA Sequence Virus Type 2");
				appendToFile("Distance bettween Pattern P and DNA Sequence Virus Type 2");
				
				for (String sequence : dnaSequences) {
					int distance = distanceBetween(padroes[i], sequence);
					if(distance <= maxDistance) {
						//s=3324 <actgcttct> Distance = 0
						appendToFile("s="+dnaSequences.indexOf(sequence)+" <"+sequence+ "> Distance = "+distance);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao ler o arquivo.");
		}
	}

	static int distanceBetween(String a, String b) {
		int matchesCount = 0;
		for (int i=0; i<a.length(); i++) {
			if(a.charAt(i) == b.charAt(i)) {
				matchesCount++;
			}
		}
		return a.length() - matchesCount;
	}
	
	
	@SuppressWarnings("resource")
	static ArrayList<String> readFile(String fileName) throws IOException {
		String line;
		ArrayList<String> dnaSequences = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		while((line = br.readLine()) != null) {
			String[] sequencesAux = line.split(" ");
			for (int i=0; i<sequencesAux.length; i++) {
				// ignorando o índice
				if(i!=0 && i%7!=0) {
					dnaSequences.add(sequencesAux[i]);
				}
			}
		}
		return dnaSequences;
	}
	
	static void appendToFile(String str) {
		try {
			str = str + "\n"; 
		    Files.write(Paths.get(OUTPUT_FILENAME), str.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
	

	static void createOutputFile() {
		try {
			FileOutputStream out = new FileOutputStream(OUTPUT_FILENAME);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
