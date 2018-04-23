package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.Aikstele;

public class Parkavimas {
	
	static Aikstele aik = new Aikstele();
	
	
	public static void main(String[] args) {

		System.out.println("Sveiki atvykę į automobilių stovėjimo aikštęlę \n"
				+ "Aikštelėje yra "+ aik.getAukstai().length +" aukštai "
				+ aukstaiToString(aik.getAukstai()) +"\n"
						+ "-Jeigu norėtumėte pakeisti aukštų skaičių: 'T'");
		
		String pasirinkimas;
		boolean pakeista = false;
		int up, down;
				
		Scanner scanner = new Scanner(System.in);
		
		pasirinkimas = scanner.next();  //leidžiama pridėti aukštų skaičių
		if(pasirinkimas.equals("T")||pasirinkimas.equals("t")) {		
			System.out.println("Kiek aukštų į viršų norėtumėte pridėti");
			up = scanner.nextInt();
			aik.getAukstai();
			System.out.println("Kiek aukštų žemyn norėtumėte pridėti");
			down = scanner.nextInt();
			aik.setAukstai(naujuAukstuPridejimas(up,down));  //pakeičiamas aukštų masyvas
			pakeista = true;
		}	
		
		int[] sortedAukstai = aukstaiSort(aik.getAukstai());  //sudaromas eiliškumas nuo pirmo aukšto
		
		if(pakeista) System.out.println("Aikštelė buvo pakeista: " + aukstaiToString(sortedAukstai));
		
		System.out.println("Laisvų vietų "+ aik.getLaisvuVietuPirmiausia());
		System.out.println("** Įveskitę automobilių skaičių **");	
		
		int autoSk;
		while(scanner.hasNextInt() || aik.getLaisvuVietu()==0) { //vykdomas skaiciavimas kol yra laisvu vietu arba 
			autoSk = scanner.nextInt();							//kol irasomas jau nebe skaicius
			skaiciavimas(autoSk, aik.getLaisvuVietu(), sortedAukstai, aik.getVietuSkAukste(), aik.getLaisvosVietos());
		}
		
		scanner.close();
	}
	
	public static int[] aukstaiSort(int[] array) {  //parodoma, kaip judama nuo pagrindinio aukšto
		int [] fixedArray = Arrays.copyOf(array, array.length);
	    int teig = 1; //pagr aukštas
	    int neig = -1;
    	boolean pos = false;
	    boolean neg = false;
	        for (int i=0; i<array.length; i++) {  //užpildomas naujas masyvas tvarkingai
	        	for (int k = 0; k < array.length; k++) {	
				    if(array[k] == teig && !pos) {  
				        fixedArray[i] = teig;   
					    teig++;
					    array[k]=0;
					    for (int f=i; f<array.length; f++) {
					    	if(array[f]<0 ) {  // ar dar yra neigiamų...
					    		neg = true;
					    		pos = true;  
					       		break;
					    	}else neg = false;
					    }
					    break;
					    
			        }else if(array[k] == neig && neg) {  // 1=-1,
			            fixedArray[i] = neig;  //1=-1, 3, 5, 
			            neig--;
			            array[k]=0;
			            for (int f=i; f<array.length; f++) {
			            	if(array[f]>0) {
			            		pos = false;
			            		neg = false;
			            		break;
			            	}else pos = true;
			            }
			            break;
		        	}	
	        	}
	        }
	    return fixedArray;
	}

	public static String aukstaiToString(int[] array) {  
		String aukstaiStringe="{";
		for(int i=0; i< Array.getLength(array); i++) {
			if(i == Array.getLength(array)-1){
				aukstaiStringe += array[i];  //paskutiniam saraše
			}else {
				aukstaiStringe += array[i] +", " ;
			}
		}
		aukstaiStringe += "}";
		return aukstaiStringe;
	}
	
	public static void skaiciavimas(int autoSk, int laisvuVietu, int[] sortedAukstai,
			int vietuSkAukste, int[] laisvosVietos) {
		
		int nerVietu=0;
		if(autoSk>laisvuVietu)
			nerVietu = autoSk-laisvuVietu;
		boolean uzpildytas = false;
		
		for(int p=0; p<sortedAukstai.length; p++) {
			if(laisvosVietos[p] > 0) {
				if(autoSk > vietuSkAukste) {  // 20 pranešimų užpildantys aukštą
					int temp = 0;   
					for(int i=autoSk; i>(autoSk-vietuSkAukste); i--) { 
						System.out.println(" Laisva vieta " + sortedAukstai[p] + " aukšte");
						laisvosVietos[p]--;
						laisvuVietu--;
						temp++;
						if(laisvosVietos[p] == 0) {
							aik.setLaisvosVietos(p,0);
							aik.setLaisvuVietu(laisvuVietu);
							break;
						}
					}autoSk -= temp;	//užpildėm aikštelę bet nebūtinai su 20 automobilių				
				}else {   //maziau nei 20 likusiu
					for(int i=autoSk; i>0; i--) {  
						if(aik.getLaisvuVietu() < 1) {  //kad neperpildytų
							break;
						}
						else{
							System.out.println(" Laisva vieta " + sortedAukstai[p] + " aukšte");
							autoSk--;
							laisvosVietos[p] = aik.getLaisvosVietos()[p];
							laisvosVietos[p]--;
							laisvuVietu--;
							if(laisvosVietos[p] == 0) {
								aik.setLaisvosVietos(p,0);
								aik.setLaisvuVietu(laisvuVietu);
								uzpildytas = true;
								break;
							}	
							aik.setLaisvuVietu(laisvuVietu);	
						}aik.setLaisvosVietos(p,(laisvosVietos[p]));
					}
					if(!uzpildytas){  //tam, kad toliau galėtų pildyti neužpildytą aukštą
						continue;
					}
				}
			}else continue;
		}aik.setLaisvuVietu(laisvuVietu);
		
		for(int j=1; j<=nerVietu; j++) {
			System.out.println("Aikštelėje nebeliko vietų");
			if(j>1) {
				System.out.println("Aikštelėje nebeliko vietų [+ dar "+ (nerVietu-=(j+1))+ " tokių pranešimų]");
				System.exit(0);
				break;
			}			
		}		
				
		if(aik.getLaisvuVietu() > 0) {
			System.out.println("Aikštelėje liko "+ aik.getLaisvuVietu() +" laisvų vietų");
			System.out.println("Kiek automobilių dar atvažiavo?");
		}
		
		
	}
	
	public static int[] naujuAukstuPridejimas(int up, int down) {
		List<Integer> tempArray = new ArrayList<Integer>();
		int[] laisvosVietos;
		int max = 0;
		int min = 0;
		
		for(int y=0; y<aik.getAukstai().length; y++) {
			tempArray.add(aik.getAukstai()[y]);
		}
		
		for(int i=0; i<tempArray.size(); i++) {
			if(tempArray.get(i)>max){
				max = tempArray.get(i);
			}else if(tempArray.get(i)<min){
				min = tempArray.get(i);
			}
		}	
		
		while(up!=0) {
			max++;
			tempArray.add(max);
			up--;
		}
		while(down!=0) {
			min--;
			tempArray.add(min);
			down--;
		}
		
		int[] tmpArray = new int [tempArray.size()];
		
		for(int k=0; k<tempArray.size(); k++) {
			tmpArray[k] = tempArray.get(k);
		}
		
		laisvosVietos = new int[tempArray.size()];
		for( int i=0; i< tmpArray.length;i++) {
			laisvosVietos[i] = aik.getVietuSkAukste();
		}

		aik.setLaisvosVietosPridejimui(laisvosVietos);
		System.out.println(aukstaiToString(tmpArray));
		
		return tmpArray;
	}
	
}
