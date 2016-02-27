package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.Column;
import ua.skillsup.javacourse.practice1.db.Table;

/**
 * @author leopold
 * @since 27/02/16
 */
@Table("authors")
public class Author {

  @Column
  private Integer id;

  @Column
  private String name;

  @Column
  private LocalDate birthday;

  @Column
  private LocalDate death;

  @Column
  private String country;

  @Column
  private String notes;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public LocalDate getDeath() {
    return death;
  }

  public void setDeath(LocalDate death) {
    this.death = death;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Author author = (Author) o;

    if (id != null ? !id.equals(author.id) : author.id != null) {
      return false;
    }
    if (!name.equals(author.name)) {
      return false;
    }
    if (!birthday.equals(author.birthday)) {
      return false;
    }
    if (death != null ? !death.equals(author.death) : author.death != null) {
      return false;
    }
    if (!country.equals(author.country)) {
      return false;
    }
    return notes != null ? notes.equals(author.notes) : author.notes == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + name.hashCode();
    result = 31 * result + birthday.hashCode();
    result = 31 * result + (death != null ? death.hashCode() : 0);
    result = 31 * result + country.hashCode();
    result = 31 * result + (notes != null ? notes.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Author{" +
           "name='" + name + '\'' +
           ", birthday=" + birthday +
           ", death=" + death +
           ", country='" + country + '\'' +
           ", notes='" + notes + '\'' +
           '}';
  }
}
