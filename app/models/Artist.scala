package models

/**
  * Created by leon on 2016/9/29.
  */
case class Artist(name: String, country: String)

object Artist {
  val availableArtist = Seq(Artist("Wolfgang Amadeus Mozart", "Austria"),
    Artist("Ludwig van Beethoven", "Germany"),
    Artist("Johann Sebastian Bach", "Germany"),
    Artist("Frédéric François Chopin", "Poland"),
    Artist("Joseph Haydn", "Austria"),
    Artist("Antonio Lucio Vivaldi", "Italy"),
    Artist("Franz Peter Schubert", "Austria"),
    Artist("Franz Liszt", "Austria"),
    Artist("Giuseppe Fortunino Francesco Verdi", "Austria"))

  def fetch: Seq[Artist] = {
    availableArtist
  }

  def fetchByName(name: String): Seq[Artist] = {
    availableArtist.filter(a => a.name.contains(name))
  }

  def fetchByCountry(country: String): Seq[Artist] = {
    availableArtist.filter(a => a.country.contains(country))
  }

  def fetchByNameOrCountry(name: String, country: String): Seq[Artist] = {
    availableArtist.filter(a => a.name.contains(name) || a.country.contains(country))
  }

  def fetchByNameAndCountry(name: String, country: String): Seq[Artist] = {
    availableArtist.filter(a => a.name.contains(name) && a.country.contains(country))
  }
}
