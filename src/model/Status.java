package model;
public enum Status{
	CANCELADO(0),SOLICITADO(1),EN_PROCESO(2),ENVIADO(3),ENTREGADO(4);

	int index;

	Status(int index) {
		this.index = index;
	}

	public static String get(int index){
		switch (index) {
			case 0:
				return CANCELADO.name();
			case 1:
				return SOLICITADO.name();
			case 2:
				return EN_PROCESO.name();
			case 3:
				return ENVIADO.name();
			case 4:
				return ENTREGADO.name();
			default:
				return "ERROR";
		}
	}
}