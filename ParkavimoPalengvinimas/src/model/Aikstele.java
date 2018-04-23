package model;

public class Aikstele {

	private int[] aukstai = {-2, -1, 1, 2, 3};
	private int[] laisvosVietos = {20, 20, 20, 20, 20};
	private int vietuSkAukste = 20;
	private int laisvuVietu;
	
	public Aikstele(int[] aukstai, int[] laisvosVietos, int vietuSkAukste, int laisvuVietu) {
		this.aukstai = aukstai;
		this.laisvosVietos = laisvosVietos;
		this.vietuSkAukste = vietuSkAukste;
		this.laisvuVietu = laisvuVietu;
	}
	
	public Aikstele() {}
	
	public int[] getAukstai() {
		return aukstai;
	}
	public void setAukstai(int[] array) {
		this.aukstai=array;
	}
	public int[] getLaisvosVietos() {
		return laisvosVietos;
	}
	public void setLaisvosVietos(int vieta, int nr) {
		int[] array = getLaisvosVietos();
		
		for(int i = 0; i<aukstai.length;i++) {
			array [vieta] = nr;
		}
		this.laisvosVietos = array;;
	}
	public void setLaisvosVietosPridejimui(int[] array) {
		this.laisvosVietos = array;
	}
	public int getVietuSkAukste() {
		return vietuSkAukste;
	}
	public void setVietuSkAukste(int vietuSkAukste) {
		this.vietuSkAukste = vietuSkAukste;
	}
	public int getLaisvuVietu() {
		return laisvuVietu;
	}
	public int getLaisvuVietuPirmiausia() {
		return laisvuVietu = laisvosVietos.length * vietuSkAukste;
	}
	public void setLaisvuVietu(int laisvuVietu) {
		this.laisvuVietu = laisvuVietu;
	}
	
	
	
}
