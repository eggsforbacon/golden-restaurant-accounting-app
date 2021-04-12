package model;
public enum Status{
	CANCELADO(0,"Cancelado"),SOLICITADO(1,"Solicitado"),EN_PROCESO(2,"En Proceso"),ENVIADO(3,"Enviado"),ENTREGADO(4,"Entregado");

	int index;
	String name;

	Status(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public String getName() {
		return name;
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