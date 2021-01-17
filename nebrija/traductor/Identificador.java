package nebrija.traductor;

public class Identificador extends ComponenteLexico{
	private String lexema;
	
	public Identificador(String lexema) {
		//super(etiqueta);
		super("id");
		this.lexema = lexema;
	}
	
	public String getLexema() {
		return this.lexema;
	}
	
	public String toString() {
		//return this.lexema;
		return super.toString() + ", " + this.getLexema();
	}
}
