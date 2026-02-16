import automate.automateBuilder
import automate.model.Automate
import java.io.File
import java.util.Locale.getDefault

fun main() {

    val fichiers = File("src/automate/data/txt").listFiles() ?: arrayOf()


    do {
        println("\n--------------- Menu des Automates -------------------------")
        println("Veuillez choisir l'automate à créer parmi : \n")

        fichiers.forEachIndexed { index, fichier ->
            println("${index}. ${fichier.nameWithoutExtension}")
        }

        println("Stop. Arrêt de l’application")
        print("-> ")



        when (val choice = readln().lowercase(getDefault())) {

            "stop" -> {
                println("Arrêt du programme")
                break
            }

            else -> {
                // Vérif
                val choiceInt = choice.toIntOrNull()
                if (choiceInt == null || choiceInt < 0 || choiceInt > fichiers.size - 1) {
                    println("Automate inexistant, veuillez choisir un automate présent dans la liste !")
                    continue
                }

                val nameFile = fichiers[choice.toInt()].nameWithoutExtension
                println(nameFile + "\n")

                val automate = automateBuilder(nameFile)
                if (!Automate.isConnexe(automate)) {
                    println("Votre automate n'est pas connexe, modifier le !")
                    continue
                }


                println("------------------------------------------------------------")
                println("Vous êtes sur l'automate $nameFile ! \n")
                do {

                    println("Veuillez choisir une option parmi : \n")

                    println("1. Rentrer $nameFile")
                    println("2. Imprimer le graphe")
                    println("Stop. Arrêt de l'automate")
                    print("-> ")


                    when (val choice = readln().lowercase(getDefault())) {

                        "stop" -> {
                            println("Arrêt du programme")
                            break
                        }

                        "1" -> {
                            println("\nAlphabet autorisé :\n [ ${automate.alphabet.joinToString(", ")} ]\n")
                            print("Veuillez écrire votre $nameFile : ")
                            val mot = readln().lowercase(getDefault())
                            println(automate.validation(mot) + "\n")
                        }

                        "2" -> automate.toString()

                        else -> {
                            println("Option invalide: Veuillez choisir une option qui existe !\n")
                            continue
                        }

                    }


                } while (true)

            }
        }
    } while (true)
}
