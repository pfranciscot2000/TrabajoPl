package nebrija.traductor;

public class BooleanTrue extends ComponenteLexico{
	private boolean valor;
	
	public BooleanTrue(boolean valor) {
		super("true");
		this.valor = valor;
	}
	
	public boolean getValor() {
		return this.valor;
	}
	
	public String toString() {
		return super.toString() + ", " + this.getValor();
	}
}
