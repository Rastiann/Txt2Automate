package automate.model

class Puit(nom: String) : Etat(nom) {

    override fun etatSuivant(value: Char): Puit {
        return this
    }

}