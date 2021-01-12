package nebrija.traductor;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import nebrija.traductor.ComponenteLexico;
import nebrija.traductor.Lexico;
import nebrija.traductor.Identificador;
import nebrija.traductor.NumeroEntero;
import nebrija.traductor.TipoArray;
import nebrija.traductor.TipoDato;
import nebrija.traductor.TipoPrimitivo;

public class AnalizadorSintactico {
	private ComponenteLexico componenteLexico;
	private Lexico lexico;
	private Hashtable<String, TipoDato> simbolos;
	
	public AnalizadorSintactico(Lexico lexico) {
		this.simbolos = new Hashtable<String, TipoDato>();
		this.lexico = lexico;
		this.componenteLexico = this.lexico.getComponenteLexico();
	}
	
	private void compara(String etiqueta) {
		if(this.componenteLexico.getEtiqueta().equals(etiqueta))
			this.componenteLexico = this.lexico.getComponenteLexico();
		else
			System.out.println("Error, se esperaba " + etiqueta);
	}
	
	public String tablaSimbolos() {
		String simbolos = "";
		Set<Map.Entry<String, TipoDato>> s = this.simbolos.entrySet();
		
		for(Map.Entry<String, TipoDato> m : s)
			simbolos = simbolos + "<'" + m.getKey() + "', " + m.getValue().toString() + "> \n";
		
		return simbolos;
	}
	
	public void programa() {
		compara("void");
		compara("main");
		compara("open_parenthesis");
		
		declaraciones();
		instrucciones();
		
		compara("close_parenthesis");
	}
	
	private void declaraciones() {
		String etiqueta = this.componenteLexico.getEtiqueta();
		
		if(etiqueta.equals("int") || etiqueta.equals("float")) {
			declaracion();
			declaraciones();
		}
	}
	
	private void declaracion() {
		String tipo = tipo();
		int tamaño = 1;
		
		if(this.componenteLexico.getEtiqueta().equals("open_square_bracket")) {
			compara("open_square_bracket");
			if(this.componenteLexico.getEtiqueta().equals("int")) {
				NumeroEntero numero = (NumeroEntero) this.componenteLexico;
				tamaño = numero.getValor();
			}
			compara("int"); //Tamaño del vector
			compara("closed_square_bracket");
			
			if(this.componenteLexico.getEtiqueta().equals("id")) {
				Identificador id = (Identificador) this.componenteLexico;
				simbolos.put(id.getLexema(),  new TipoArray(tipo, tamaño));
			}
			compara("id");
			compara("semicolon");
		}
		else {
			identificadores(tipo);
			compara("semicolon");
		}
		
		identificadores(tipo);
		compara("semicolon");
	}
	
	public void instrucciones() {
		String etiqueta = this.componenteLexico.getEtiqueta();
		
		if(etiqueta.equals("id") || etiqueta.equals("if") || etiqueta.equals("while") || etiqueta.equals("do")) {
			instruccion();
			instrucciones();
		}
	}
	
	public void instruccion() {
		String etiqueta = this.componenteLexico.getEtiqueta();
		
		if(etiqueta.equals("id")) {
			asignacion();
			compara("semicolon");
		}
	}
	
	public void asignacion() {
		if(this.componenteLexico.getEtiqueta().equals("id")) {
			Identificador id1 = (Identificador) this.componenteLexico;
			
			TipoDato tipo1 = this.simbolos.get(id1.getLexema());
					
			compara("id");
			compara("assignment");
			
			Identificador id2 = (Identificador) this.componenteLexico;
			
			TipoDato tipo2 = this.simbolos.get(id2.getLexema());
			compara("id");
			
			if(tipo1.getTipo().equals(tipo2.getTipo()))
				System.out.println(id1.getLexema() + " (" + tipo1 + ") vs " + id2.getLexema() + " (" + tipo2 + ") is ok");
			else
				System.out.println(id1.getLexema() + " (" + tipo1 + ") vs " + id2.getLexema() + " (" + tipo2 + ") type mismatch");
		}
	}
	
	private String tipo() {
		String tipo = this.componenteLexico.getEtiqueta();
		
		if(tipo.equals("int")) {
			compara("int");
		}
		else if(tipo.equals("float")) {
			compara("float");
		}
		else if(tipo.equals("boolean")){
			compara("boolean");
		}
		else {
			System.out.println("Error, se esperaba int o float o boolean");
		}
		
		return tipo;
	}
	
	private void identificadores(String tipo) {
		if(this.componenteLexico.getEtiqueta().equals("id")) {
			Identificador id = (Identificador) this.componenteLexico;
			
			simbolos.put(id.getLexema(), new TipoPrimitivo(tipo));
			
			compara("id");
			masIdentificadores(tipo);
		}
	}
	
	private void masIdentificadores(String tipo) {
		if(this.componenteLexico.getEtiqueta().equals("comma")) {
			compara("comma");
			
			Identificador id = (Identificador) this.componenteLexico;
				
			simbolos.put(id.getLexema(), new TipoPrimitivo(tipo));
				
			compara("id");
			masIdentificadores(tipo);
		}
	}
	
	private void asignacionDeclaracion() {
		if(this.componenteLexico.getEtiqueta().equals("equals")) {
			compara("equals");
			expresionLogica();
		}
	}
	
	private void expresionLogica() {
		terminoLogico();
		expresionLogicaPrima();
	}
	
	private void expresionLogicaPrima() {
		//terminoLogico || ecpresionLogicaPrima | epsilon
	}
	
	private void terminoLogico() {
		factorLogico();
		terminoLogicoPrima();
	}
	
	private void terminoLogicoPrima() {
		//factorLogico() && terminoLogicoPrima | epsilon
	}
	
	private void factorLogico() {
		if(this.componenteLexico.getEtiqueta().equals("not")) {
			compara("not");
			factorLogico();
		}
		if(this.componenteLexico.getEtiqueta().equals("true")) {
			
		}
	}
}
