package nebrija.traductor;

import nebrija.traductor.ComponenteLexico;
import nebrija.traductor.Lexico;
import nebrija.traductor.NumeroEntero;

public class TraductorExpresion {
	private Lexico lexico;
	private ComponenteLexico componenteLexico;
	private String postfijo;
	
	public TraductorExpresion(Lexico lexico) {
		this.lexico = lexico;
		this.componenteLexico = this.lexico.getComponenteLexico();
		//this.pila = new Stack<Integer>();
		this.postfijo = "";
	}
	
	public void postfijo() {
		expresion();
	}
	
	public void expresion() {
		termino();
		masTerminos();
	}
	
	private void masTerminos() {
		if (this.componenteLexico.getEtiqueta().equals("add")) {
			compara("add");
			termino();
			System.out.print(" + ");
			masTerminos();
		}
		else if (this.componenteLexico.getEtiqueta().equals("subtract")) {
			compara("subtract");
			termino();
			System.out.print(" - ");
			masTerminos();
		}
	}
	
	private void termino() {
		factor();
		masFactores();
	}
	
	private void masFactores() {
		if (this.componenteLexico.getEtiqueta().equals("multiply")) {
			compara("multiply");
			factor();
			System.out.print(" * ");
			masFactores();
		}
		else if (this.componenteLexico.getEtiqueta().equals("divide")) {
			compara("divide");
			factor();
			System.out.print(" / ");
			masFactores();
		}
	}
	
	private void factor() {
		if (this.componenteLexico.getEtiqueta().equals("open_parenthesis")) {
			compara("open_parenthesis");
			expresion();
			compara("closed_parenthesis");
		}
		else if (this.componenteLexico.getEtiqueta().equals("int")) {
			NumeroEntero numero = (NumeroEntero) this.componenteLexico;
			System.out.print(" " + numero.getValor() + " ");
			compara("int");
		}
		else
			System.out.println("Error, se esperaba paréntesis abierto o número");
	}
	
	private void compara(String etiqueta) {
		if (this.componenteLexico.getEtiqueta().equals(etiqueta))
			this.componenteLexico = this.lexico.getComponenteLexico();
		else
			System.out.println("Error, se esperaba " + etiqueta);
	}
}
