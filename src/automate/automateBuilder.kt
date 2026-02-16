package automate

import automate.model.Automate
import automate.model.Etat
import java.io.File
import java.io.InputStream
import kotlin.collections.iterator

fun automateBuilder(file: String): Automate {

    // Ils vont etre initialisé au fur et à mesure de la lecture du txt
    val alphabet: MutableSet<Char> = mutableSetOf()
    val etatsMAP: MutableMap<String, Etat> = mutableMapOf()
    lateinit var etatInitial: Etat
    val etatsFinal: MutableSet<Etat> = mutableSetOf()
    val delta: MutableMap<Pair<Char, Etat>, Etat> = mutableMapOf()


    File("src/automate/data/txt/$file.txt").forEachLine { line ->

        val mots = line.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }

        // @ parce qu'on est dans un lambda et pas dans une boucle
        if (mots.isEmpty()) return@forEachLine


        when (mots[0]) {

            "init" -> {
                etatInitial = etatsMAP.getOrPut(mots[1]) { Etat(mots[1]) }
            }

            "finals" -> {
                for (i in 1 until mots.size) {
                    val nomEtat = mots[i]
                    val etat = etatsMAP.getOrPut(nomEtat) { Etat(nomEtat) }
                    etatsFinal.add(etat)
                }
            }

            "alphabet" -> {
                for (i in 1 until mots.size) {

                    if (mots[i] == "letters") {
                        alphabet.addAll('a'..'z')
                        alphabet.addAll('A'..'Z')

                    } else if (mots[i] == "digits") {
                        alphabet.addAll('0'..'9')
                    } else {
                        val char = mots[i][0]
                        alphabet.add(char)
                    }

                }
            }

            else -> {

                val fromName = mots[0]
                val toName = mots[mots.size - 1]

                val stateA = etatsMAP.getOrPut(fromName) { Etat(fromName) }
                val stateB = etatsMAP.getOrPut(toName) { Etat(toName) }


                for (i in 1 until mots.size-1) {
                    when (mots[i]) {
                        "letters" -> {
                            ('a'..'z').forEach { stateA.ajouterTransition(it, stateB) }
                            ('A'..'Z').forEach { stateA.ajouterTransition(it, stateB) }
                        }

                        "digits" -> ('0'..'9').forEach { stateA.ajouterTransition(it, stateB) }
                        else -> stateA.ajouterTransition(mots[i][0], stateB)
                    }
                }


            }
        }

    }

    // Build Delta
    for (etat in etatsMAP.values) {
        for ((char, destination) in etat.hashmap) {
            delta[Pair(char, etat)] = destination
        }
    }

    val etatsSET: Set<Etat> = etatsMAP.values.toSet()


    return Automate(file ,alphabet, etatsSET, etatInitial, etatsFinal, delta)
}

