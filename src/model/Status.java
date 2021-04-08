package model;
public enum Status{
	CANCELADO(0),SOLICITADO(1),EN_PROCESO(2),ENVIADO(3),ENTREGADO(4);

	int index;

	Status(int index) {
		this.index = index;
	}

	public Status get(int index) throws IndexOutOfBoundsException {
		switch (index) {
			case 0:
				return CANCELADO;
			case 1:
				return SOLICITADO;
			case 2:
				return EN_PROCESO;
			case 3:
				return ENVIADO;
			case 4:
				return ENTREGADO;
			default:
				throw new IndexOutOfBoundsException("Index not part of the enum.");
		}
	}
}