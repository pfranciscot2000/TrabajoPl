package nebrija.traductor;

public class BooleanFalse extends ComponenteLexico{
	private boolean valor;
	
	public BooleanFalse(boolean valor) {
		super("false");
		this.valor = valor;
	}
	
	public boolean getValor() {
		return this.valor;
	}
	
	public String toString() {
		return super.toString() + ", " + this.getValor();
	}
}
