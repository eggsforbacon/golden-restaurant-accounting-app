package model;

import java.util.ArrayList;

public class Order {

  String ID;

  public Order(ArrayList<String> IDs) {
    generateID(IDs);
  }

  /**
  *Generates a unique 8 digit ID for each order.<br>
  *<b>Pre: </b><br>
  *<b>Post: </b>The ID is generated.<br>
  */
  private void generateID(ArrayList<String> IDs) {
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz";
    StringBuilder sb = new StringBuilder();
    SecureRandom r = new SecureRandom();
    for (int i = 0; i < 4; i++) {
      int index = alphabet.length() * r.nextInt();
      sb.append(alphabet.charAt(index));
    }
    ID = sb.toString();
    if (IDs.contains(ID)) generateID(IDs);
  }

  public String getID() {
    return ID;
  }

}
