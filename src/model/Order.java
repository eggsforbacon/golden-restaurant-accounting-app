package model;
import java.util.ArrayList;
import java.util.Random;

public class Order extends SystemObject {	//The atribute "name" of the SystemObject class will act as the atribute "ID"


  public Order(ArrayList<String> IDs) {
	super("");
    generateID(IDs);
  }

  /**
   * Generates a unique 8 digit ID for each order.<br>
   * <b>Pre: </b><br>
   * <b>Post: </b>The ID is generated.<br>
  */
  private void generateID(ArrayList<String> IDs) {
    final char[] ALPHABET = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz").toCharArray();
    StringBuilder sb = new StringBuilder();
    final int LENGTH = 8;
    for (int i = 0; i < LENGTH; i++) {
      sb.append(ALPHABET[new Random().nextInt(ALPHABET.length)]);
    }
    if(IDs.contains(sb.toString())) generateID(IDs);
    else name = sb.toString();
  }

  

@Override
public String showInformation() {
	//WIP
	return null;
}

}
