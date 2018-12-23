package jjcipher.zoc;

import java.util.Date;

import jjcipher.zoc.annotation.CheckedString;

public class MyZetaObj implements ZetaObj {

  private static final long serialVersionUID = -2725316920927010632L;

  @CheckedString(criteria = CheckedString.NOT_NULL)
  final String name;

  @CheckedString(criteria = CheckedString.UNIQUE)
  final String login;

  @CheckedString(criteria = CheckedString.NOT_EMPTY)
  final String password;

  @CheckedString(minLength = 8, maxLength = 100,
      pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
  final String email;

  @CheckedString(maxLength = 10)
  final String description;

  final Date created;

  public MyZetaObj(String name, String login, String password, String email, String description) {
    this.name = name;
    this.login = login;
    this.password = password;
    this.email = email;
    this.description = description;
    this.created = new Date();
  }

  private MyZetaObj(MyZetaObj other) {
    this.name = other.name;
    this.login = other.login;
    this.password = other.password;
    this.email = other.email;
    this.description = other.description;
    this.created = other.created;
  }
}
