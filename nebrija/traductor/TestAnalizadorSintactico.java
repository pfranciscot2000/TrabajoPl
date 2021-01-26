package nebrija.traductor;

import java.nio.charset.StandardCharsets;

public class TestAnalizadorSintactico {

	public static void main(String [] args) {
			boolean mostrarComponentesLexicos = true;
			String ficheroEntrada = "programa.txt";
				
			ComponenteLexico etiquetaLexica;
				
			Lexico lexico = new Lexico(ficheroEntrada, StandardCharsets.UTF_8);
			if(mostrarComponentesLexicos)	
				do {
					etiquetaLexica = lexico.getComponenteLexico();
					System.out.println("<" + etiquetaLexica.toString() + ">");
				}while (!etiquetaLexica.getEtiqueta().equals("end_program"));
			
			AnalizadorSintactico compilador = new AnalizadorSintactico(new Lexico(ficheroEntrada, StandardCharsets.UTF_8));
			
			compilador.programa();
			System.out.println("\nTabla de simbolos \n\n" + compilador.tablaSimbolos());			
	}
}
