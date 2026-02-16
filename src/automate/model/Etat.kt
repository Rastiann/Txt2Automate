package automate.model

import automate.model.Puit

open class Etat(
    val nom: String,
    var hashmap: HashMap<Char, Etat> = hashMapOf()
) {

    // Cette fonction permet de la création d'un Etat sans nécessairement lui donné toutes ses relations
    // ça évite de devoir commencer par la création des états finaux
    fun ajouterTransition(c: Char, etat: Etat) {
        this.hashmap[c] = etat
    }

    open fun etatSuivant(value: Char): Etat {
        return this.hashmap[value] ?: Puit("Puit")
    }

}