package jjcipher.zoc;

import jjcipher.zoc.annotation.CheckedString;

public class MyZetaObjWithPriority implements ZetaObj, WithPriority {

  private static final long serialVersionUID = -2725316920927010632L;

  @CheckedString(criteria = CheckedString.UNIQUE)
  final String name;

  public MyZetaObjWithPriority(String name) {
    this.name = name;
  }
}
